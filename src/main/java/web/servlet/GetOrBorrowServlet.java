package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import service.get_or_borrow_service;
import service.impl.get_or_borrow_service_impl;
import service.impl.item_service_impl;
import service.impl.order_service_impl;
import service.item_service;
import service.order_service;
import util.UuidUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet("/getorborrow/*")
public class GetOrBorrowServlet extends BaseServlet {
    private static final int SUBMITTED = 1;
    private static String order_id = "";
    private static Map<String, List<Item>> temp_items = new HashMap<>();
    private order_service o_service = new order_service_impl();
    private item_service i_service = new item_service_impl();
    private final get_or_borrow_service service = new get_or_borrow_service_impl();
    private final order_service orderService = new order_service_impl();

    /**
     * 领用物品
     */
    public void createOrUpdateItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GetOrBorrowServlet：进入 领用物品 的后端");
        // 注册String转变Date
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class aClass, Object o) {
                // 如果字符串为null，则返回null
                if(o == null) return null;
                // 如果字符串为空格，则返回null
                String str = (String) o;
                if(str.trim().equals("")) return null;
                // 将字符串转为Date类型
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    return formatter.parse(o.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return new Date();
            }
        }, Date.class);

        //1.获取数据（领用申请表基本信息 + 领用申请物品清单）
        Map<String, String[]> map = request.getParameterMap(); //获取前端数据，考虑分两次传输 申请表信息和物品清单

        //2.封装对象
        get_or_borrow_Requisition get_or_borrow_requisition = new get_or_borrow_Requisition();
        // 前端传回申请表和物品列表的集合
        try {
            BeanUtils.populate(get_or_borrow_requisition, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        List<Object_Entry> object_entries = new ArrayList<>();
        Object_Entry objectEntry = new Object_Entry();


        if (get_or_borrow_requisition.getGet_or_borrow_order_id() == null) {
            get_or_borrow_requisition.setGet_or_borrow_order_id(order_id);
            System.out.println("OrderId : " + get_or_borrow_requisition.getGet_or_borrow_order_id() + "====");
        }
        temp_items.computeIfAbsent(order_id, k -> new ArrayList<Item>());
        System.out.println(temp_items.get(get_or_borrow_requisition.getGet_or_borrow_order_id()));

        //-----------？？？-----------------
        for (Item item : temp_items.get(order_id)) {
            objectEntry.setOrder_id(order_id);
            objectEntry.setObject_id(item.getObject_id());  //TODO:NullPointerException
            objectEntry.setNum((int)item.getQuantity());
            object_entries.add(objectEntry);
        }
        i_service.add(temp_items.get(get_or_borrow_requisition.getGet_or_borrow_order_id()));
        //--------------？？？----------------

        template_order templateOrder = new template_order(get_or_borrow_requisition, object_entries);
        service.createOrUpdate(templateOrder);
        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 获取订单ID
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getOrderId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        order_id = request.getParameter("order_id");
    }

    /**
     * 查询一个申请单的物品列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchOneOrderList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        // TODO : 前端获取某一行订单ID：order_id
        String orderId = request.getParameter("order_id");
        List<Object_Entry> objectEntries = orderService.search(orderId);
        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setData(objectEntries);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 用户查询自己的所有申请单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchAllByUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        // TODO : 前端获取用户id ： user_id
        String userId = request.getParameter("user_id");
        List<get_or_borrow_Requisition> getOrBorrowRequisitionList = service.searchTableByUser(userId);
        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setData(getOrBorrowRequisitionList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 审批人查询所有需要审批的申请单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchAllByApprovalPerson(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        List<get_or_borrow_Requisition> getOrBorrowRequisitionList = service.searchTableByState(SUBMITTED);
        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setData(getOrBorrowRequisitionList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    /**
     * 删除功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // TODO : 前端获取申请表ID：table_id
        String tableId = request.getParameter("get_or_borrow_requisition_id");
        String orderID = request.getParameter("order_id");
        // 调用service删除
        o_service.deleteEntryByOrder(orderID);
        service.deleteTable(tableId);
    }

    // 初始化 物品清单列表
    public void initListTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("PurchaseServlet：初始化物品清单弹窗");
        String order_id = request.getParameter("order_id");
//        System.out.println("PurchaseServlet order_id="+order_id);
        String purchaseDataList = "";
        if (order_id != null)
            purchaseDataList = updatePurchaseListData(order_id);

        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(purchaseDataList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public String updatePurchaseListData(String order_id) throws IOException {
        System.out.println("===================");
        System.out.println("长度："+temp_items.keySet());
        System.out.println("===================");
        System.out.println("更新 物品清单列表"+order_id);

        // 获取物品清单的 全部物品行
        List<Object_Entry> oe_list = o_service.search(order_id);

        // 根据 order_id 查找 物品信息
        List<Item> items = new ArrayList<>();
        for (Object_Entry oe : oe_list) {
            System.out.println("物品id="+oe.getObject_id());
            Item item = i_service.search(oe.getObject_id());  // 获取物品信息
            item.setQuantity(oe.getQuantity());  // 修改数量（采购物品的数量在object_entry类中）
            item.print();  // 显示对象信息
            items.add(item);  //添加进 物品信息表
        }
        //拷贝副本
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            result.add(item);
        }

        if (temp_items.get(order_id) != null) {
            for (Item item1 : temp_items.get(order_id)) {
                for (Item i2 : items) {
                    if (item1.getObject_id().equals(i2.getObject_id())) {
                        result.remove(i2);
                        result.add(item1);
                        break;
                    } else {
                        result.add(item1);
                    }
                }
            }
        }
        System.out.println("-------*************************----------"+items.size());
        for(Item i : result) {
            i.print();
        }
        System.out.println("--------*************************---------");


        // 拼串
        String json = "";
        for (Item i : result) {
            try {
                json += writeValueAsString(i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        json = json.replace("}", "},");
        json = json.replaceFirst(".$","");  //去掉最后的逗号
        json += "]";
        StringBuffer buf = new StringBuffer();
        buf.append(json).insert(0, "[");
        json = buf.toString();  //拼串完成
        System.out.println("物品信息json="+json);
//        String filename = "PurchaseListData.txt";
//        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
    }

    // 更新清单
    public void UpdateItemsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 注册 String 转 Date的BeanUtils转换器
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class aClass, Object o) {
                // 如果字符串为null，则返回null
                if(o == null) return null;
                // 如果字符串为空格，则返回null
                String str = (String) o;
                if(str.trim().equals("")) return null;
                // 将字符串转为Date类型
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                try {
                    return formatter.parse(o.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return new Date();
            }
        }, Date.class);

        Item item_changed = new Item();
        Map<String, String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(item_changed, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // 如果当前order_id没有关联的物品行，新建一个对应的缓存
        if (order_id == "") {
            order_id = UuidUtil.getUuid();
        }
        temp_items.computeIfAbsent(order_id, k -> new ArrayList<Item>());

        if (item_changed.getObject_id()!= null) {  // 不是新建的item
            for (Item i : temp_items.get(order_id)) {  // 遍历已经缓存的关联该订单的Item
                if (i.getObject_id().equals(item_changed.getObject_id())) {  // 如果修改过的Item已经缓存过了
                    temp_items.get(order_id).remove(i);  // 从缓存中删除
                    break;  // 结束遍历
                }
            }
        } else {  // 新建的Item 补充id属性
            item_changed.setObject_id(UuidUtil.getUuid());
        }
        temp_items.get(order_id).add(item_changed);  // 缓存 前端修改的item


        // 响应结果
        ResultInfo info = new ResultInfo();
        info.setFlag(true);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public void initTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("PurchaseServlet：进入 初始化表格 后端");
        String user_id = request.getParameter("user_id");
        System.out.println("PurchaseServlet user_id="+user_id);
        String purchaseDataList = "";
        if (user_id != null)
            purchaseDataList = updatePurchaseData(user_id);

        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(purchaseDataList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public String updatePurchaseData(String user_id) throws IOException {
        System.out.println("进入updatePurchaseDataList");
        List<get_or_borrow_Requisition> pr_list = service.searchTableByUser(user_id);
        for (get_or_borrow_Requisition pr : pr_list) {
            System.out.println(pr.getGet_or_borrow_order_id());
        }
        // 拼串
        String json = "";
        for (get_or_borrow_Requisition pr : pr_list) {
            try {
                json += writeValueAsString(pr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        json = json.replace("}", "},");
        json = json.replaceFirst(".$","");  //去掉最后的逗号
        json += "]";
        StringBuffer buf = new StringBuffer();
        buf.append(json).insert(0, "[");
        json = buf.toString();  //拼串完成
        System.out.println(json);

//        String filename = "PurchaseData.txt";
//        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
    }

}
