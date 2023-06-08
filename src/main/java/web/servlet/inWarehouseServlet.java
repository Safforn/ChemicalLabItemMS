package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.*;
import service.impl.*;
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

class Temp{
    private List<get_or_borrow_Requisition> borrowList;
    private List<Purchase_Requisition> purchaseList;

    public Temp(List<get_or_borrow_Requisition> borrowList, List<Purchase_Requisition> purchaseList) {
        this.borrowList = borrowList;
        this.purchaseList = purchaseList;
    }

    public List<get_or_borrow_Requisition> getBorrowList() {
        return borrowList;
    }

    public void setBorrowList(List<get_or_borrow_Requisition> borrowList) {
        this.borrowList = borrowList;
    }

    public List<Purchase_Requisition> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase_Requisition> purchaseList) {
        this.purchaseList = purchaseList;
    }
}

@WebServlet("/inWarehouse/*")
public class inWarehouseServlet extends BaseServlet {
    private purchase_in_service purchaseInService = new purchase_in_service_impl();
    private borrow_in_service borrowInService = new borrow_in_service_impl();
    private get_or_borrow_service getOrBorrowService = new get_or_borrow_service_impl();
    private purchase_requisition_service purchaseRequisitionService = new purchase_requisition_service_impl();
    private order_service orderService = new order_service_impl();
    private item_service itemService = new item_service_impl();
    private static Map<String, List<Item>> temp_items = new HashMap<>();
    private static String order_id = "";
    /**
     * 查询所有可以入库的表单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 借用入库查询
        List<get_or_borrow_Requisition> borrowList = getOrBorrowService.searchBorrowUnreturn();
        // 采购入库查询
        List<Purchase_Requisition> purchaseList = purchaseRequisitionService.searchUnreturn();

        Temp temp = new Temp(borrowList, purchaseList);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(temp);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
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
    public void searchOneByOrderId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        String orderId = "";
        //2.封装对象
        try {
            BeanUtils.populate(orderId, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        List<Object_Entry> object_entries = orderService.search(orderId);

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(object_entries);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 采购入库
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void purchaseIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        Purchase_Requisition purchase_requisition = new Purchase_Requisition();
        try {
            BeanUtils.populate(purchase_requisition, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        List<Object_Entry> objectEntries = new ArrayList<>();
        Object_Entry object_entry = new Object_Entry();
        for (Item item : temp_items.get(purchase_requisition.getPurchase_order_id())) {
//            object_entry.setOrder_id(purchase_requisition.getPurchase_order_id());
            object_entry.setObject_id(item.getObject_id());
            object_entry.setOrder_id(item.getObject_id());
            object_entry.setQuantity((int)item.getQuantity());
            objectEntries.add(object_entry);
        }

        template_order templateOrder = new template_order(purchase_requisition, objectEntries);
        boolean flag = purchaseInService.add(templateOrder);
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
            item.setObject_id(UuidUtil.getUuid());
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
     * 借用入库
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void borrowIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        template_order templateOrder = new template_order();
        //2.封装对象
        try {
            BeanUtils.populate(templateOrder, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean flag = borrowInService.add((Borrow_in_Warehouse) templateOrder.getTable());
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


    public void deleteTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // 从前端获取 待删除申请表行的 tableId
        String tableId = request.getParameter("tableId");
        // 调用service删除
        getOrBorrowService.deleteTable(tableId);
        // TODO: 更新前端表格页面，这个好像不需要做，前端会自动删除（加个"确认删除"的弹窗）
        //  只需要把tableId传给后端即可

    }

    public void initTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("GorBServlet：进入 初始化表格 后端");
        String user_id = request.getParameter("user_id");
        System.out.println("GorBServlet user_id="+user_id);
        String getorborrowDataList = "";
        if (user_id != null)
            getorborrowDataList = updateGBData(user_id);

        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(getorborrowDataList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


    public String updateGBData(String user_id) throws IOException {
        System.out.println("进入updatePurchaseDataList");
        List<get_or_borrow_Requisition> pr_list = getOrBorrowService.searchTableByUser(user_id);
        for (get_or_borrow_Requisition pr : pr_list) {
            System.out.println(pr.getGet_or_borrow_requisition_id());
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

        String filename = "IWData.txt";
        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
    }


    // 初始化 物品清单列表
    public void initListTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("IWServlet：进入 初始化物品清单弹窗表格 后端");
        String order_id = request.getParameter("order_id");
        System.out.println("IWServlet order_id="+order_id);
        String GBDataList = "";
        if (order_id != null)
            GBDataList = updateGBListData(order_id);


        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(GBDataList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public String updateGBListData(String order_id) throws IOException {
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
        String filename = "IWListData.txt";
        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
    }

}
