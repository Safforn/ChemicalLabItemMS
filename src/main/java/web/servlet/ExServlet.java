package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.ex_service;
import service.get_or_borrow_service;
import service.impl.ex_service_impl;
import service.impl.get_or_borrow_service_impl;
import service.impl.item_service_impl;
import service.impl.order_service_impl;
import service.item_service;
import service.order_service;
import util.JsonUtil;
import util.UuidUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ex/*")
public class ExServlet extends BaseServlet {
    private static final int APPROVED = 2;
    private final ex_service exService = new ex_service_impl();
    private final get_or_borrow_service getOrBorrowService = new get_or_borrow_service_impl();
    private final order_service orderService = new order_service_impl();
    private final item_service itemService = new item_service_impl();
    private static final Map<String, List<Item>> temp_items = new HashMap<>();
    private static String order_id = "";
    /**
     * 出库
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exWarehouse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO : 前端填写出库单
        Map<String, String[]> map = request.getParameterMap();

        Ex_Warehouse ex_warehouse = new Ex_Warehouse();
        try {
            BeanUtils.populate(ex_warehouse, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        List<Object_Entry> objectEntries = new ArrayList<>();
        Object_Entry object_entry = new Object_Entry();
        for (Item item : temp_items.get(ex_warehouse.getEx_order_id())) {
            object_entry.setQuantity((int)item.getQuantity());
            object_entry.setObject_id(item.getObject_id());
            object_entry.setOrder_id(item.getObject_id());
        }


        boolean flag = exService.add(ex_warehouse, objectEntries);
        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(flag);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 更新物品清单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void UpdateItemsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Item item = new Item();
        Map<String, String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(item, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // 如果当前order_id没有关联的物品行，新建一个对应的缓存
        temp_items.computeIfAbsent(order_id, k -> new ArrayList<Item>());

        if (item.getObject_id()!= null) {  // 不是新建的item
            for (Item i : temp_items.get(order_id)) {  // 遍历已经缓存的关联该订单的Item
                if (i.getObject_id().equals(item.getObject_id())) {  // 如果修改过的Item已经缓存过了
                    temp_items.get(order_id).remove(i);  // 从缓存中删除
                    break;  // 结束遍历
                }
            }
        } else {  // 新建的Item 补充id属性
            item.setObject_id(UuidUtil.getII());
        }
        temp_items.get(order_id).add(item);  // 缓存 前端修改的item


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
     * 查询所有领用、借用单 状态：审批已通过
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<get_or_borrow_Requisition> getOrBorrowRequisitionList = getOrBorrowService.searchTableByState(APPROVED);
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
     * 查询一个申请单的物品列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchOneOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO : 前端获取order_id ： order_id
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
     * 查询所有出库单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchAllExTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ex_Warehouse> exWarehouses = exService.search();
        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setData(exWarehouses);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


    public void initTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("EWServlet：进入 初始化表格 后端");
        String user_id = request.getParameter("user_id");
        System.out.println("EWServlet user_id="+user_id);
        String exDataList = "";
        if (user_id != null)
            exDataList = updateExData(user_id);

        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(exDataList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


    public String updateExData(String user_id) throws IOException {
        System.out.println("进入updateExDataList");
        List<Ex_Warehouse> pr_list = exService.search();
        for (Ex_Warehouse pr : pr_list) {
            System.out.println(pr.getEx_warehouse_id());
        }
        // 拼串
        String json = "";
        for (Ex_Warehouse pr : pr_list) {
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

        String filename = "ExData.txt";
        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
    }


    // 初始化 物品清单列表
    public void initListTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("ExServlet：进入 初始化物品清单弹窗表格 后端");
        String order_id = request.getParameter("order_id");
        System.out.println("ExServlet order_id="+order_id);
        String exDataList = "";
        if (order_id != null)
            exDataList = updateExListData(order_id);


        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(exDataList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public String updateExListData(String order_id) throws IOException {
        System.out.println("===================");
        System.out.println("长度："+temp_items.keySet());
        System.out.println("===================");
        System.out.println("更新 物品清单列表"+order_id);

        // 获取物品清单的 全部物品行
        List<Object_Entry> oe_list = orderService.search(order_id);

        // 根据 order_id 查找 物品信息
        List<Item> items = new ArrayList<>();
        for (Object_Entry oe : oe_list) {
            System.out.println("物品id="+oe.getObject_id());
            Item item = itemService.search(oe.getObject_id());  // 获取物品信息
            item.setQuantity(oe.getQuantity());  // 修改数量（采购物品的数量在object_entry中）
            item.print();  // 显示对象信息
            items.add(item);  //添加进 物品信息表
        }
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
        String filename = "PurchaseListData.txt";
        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
    }

}
