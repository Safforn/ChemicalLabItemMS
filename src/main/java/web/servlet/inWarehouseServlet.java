package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.*;
import service.impl.*;
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


@WebServlet("/inWarehouse/*")
public class inWarehouseServlet<T> extends BaseServlet {
    private purchase_in_service purchaseInService = new purchase_in_service_impl();
    private borrow_in_service borrowInService = new borrow_in_service_impl();
    private other_in_service otherInService = new other_in_service_impl();
    private get_or_borrow_service getOrBorrowService = new get_or_borrow_service_impl();
    private purchase_requisition_service purchaseRequisitionService = new purchase_requisition_service_impl();
    private order_service orderService = new order_service_impl();
    private item_service itemService = new item_service_impl();
    private static String order_id = "";
    private static Map<String, List<Item>> temp_items = new HashMap<>();


    /**
     * 查询所有可以采购入库单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchAllPurchaseRequisition(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 查询采购入库
        System.out.println("开始 查询采购入库");

        // 采购入库查询
        List<Purchase_Requisition> purchaseList = purchaseRequisitionService.searchUnreturn();

        System.out.println("purchaseRequisitionService.searchUnreturn()返回的数据");
        for (Purchase_Requisition pr : purchaseList) {
            pr.print();
        }

        // 拼串
        String json = SpellString((List<T>) purchaseList);

        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(json);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 查询所有借用入库单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchAllGetOrBorrowRequisition(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 查询采购入库
        System.out.println("开始 查询借用归还入库");

        // 采购入库查询
        List<get_or_borrow_Requisition> borrowList = getOrBorrowService.searchBorrowUnreturn();

        System.out.println("getOrBorrowService.searchUnreturn()返回的数据");
        for (get_or_borrow_Requisition gbr : borrowList) {
            gbr.print();
        }

        // 拼串
        String json = this.SpellString((List<T>) borrowList);

        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(json);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonString);
        // 借用入库查询
//        List<get_or_borrow_Requisition> borrowList = getOrBorrowService.searchBorrowUnreturn();
    }

    /**
     * 查询所有其他入库单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchAllOtherRequisition(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 查询采购入库
        System.out.println("开始 查询借用归还入库");

        // 采购入库查询
        List<Other_in_Warehouse> otherList = otherInService.findAll();

        System.out.println("otherInService.searchUnreturn()返回的数据");
        for (Other_in_Warehouse gbr : otherList) {
            gbr.print();
        }

        // 拼串
        String json = this.SpellString((List<T>) otherList);

        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(json);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonString);
        // 借用入库查询
//        List<get_or_borrow_Requisition> borrowList = getOrBorrowService.searchBorrowUnreturn();
    }

    /**
     * 查询所有入库单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchAllInWarehouse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 查询采购入库
        System.out.println("开始 查询全部入库单");

        // 采购入库查询
        List<Purchase_Requisition> purchaseList = purchaseRequisitionService.searchUnreturn();
        List<get_or_borrow_Requisition> borrowList = getOrBorrowService.searchBorrowUnreturn();
        List<Other_in_Warehouse> otherList = otherInService.findAll();

        System.out.println("purchaseRequisitionService.searchUnreturn()返回的数据");
        for (Purchase_Requisition gbr : purchaseList) gbr.print();
        System.out.println("getOrBorrowService.searchUnreturn()返回的数据");
        for (get_or_borrow_Requisition gbr : borrowList) gbr.print();
        System.out.println("otherInService.searchUnreturn()返回的数据");
        for (Other_in_Warehouse gbr : otherList) gbr.print();

        // 拼串
        String json = "";
        json += this.SpellString((List<T>) borrowList);
        json += '@';
        json += this.SpellString((List<T>) purchaseList);
        json += '@';
        json += this.SpellString((List<T>) otherList);

        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(json);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonString);
        // 借用入库查询
//        List<get_or_borrow_Requisition> borrowList = getOrBorrowService.searchBorrowUnreturn();
    }

    /**
     * 拼串
     * @param list
     * @return
     */
    public String SpellString(List<T> list) {
        // 拼串
        String json = "";
        for (T pr : list) {
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
        System.out.println("拼的Purchase_Requisition串json\n"+json);
        return json;
    }


    /**
     * 查询一个申请单的物品列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchOneByOrderId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Map<String, String[]> map = request.getParameterMap();
        String orderId = request.getParameter("order_id");;
        System.out.println("####orderID="+orderId);
        //2.封装对象
//        try {
//            BeanUtils.populate(orderId, map);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }nu
        List<Item> itemList = new ArrayList<>();
        List<Object_Entry> object_entries = orderService.search(orderId);
        if (object_entries != null) {
            for (Object_Entry oe : object_entries) {
                itemList.add(itemService.search(oe.getObject_id()));
            }
        }
        String json = "";
        for (Item it : itemList) {
            try {
                json += writeValueAsString(it);
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

        System.out.println("itemList.size()"+itemList.size());


//        // 拼串
//        String json = "";
//        for (Object_Entry OE : object_entries) {
//            try {
//                json += writeValueAsString(OE);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        }
//        json = json.replace("}", "},");
//        json = json.replaceFirst(".$","");  //去掉最后的逗号
//        json += "]";
//        StringBuffer buf = new StringBuffer();
//        buf.append(json).insert(0, "[");
//        json = buf.toString();  //拼串完成
//        System.out.println("JSON=\n"+json);


        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(json);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(resultInfo);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonString);
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
        Purchase_in_Warehouse purchase_in_warehouse = new Purchase_in_Warehouse();
        //2.封装对象
        try {
            BeanUtils.populate(purchase_in_warehouse, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        List<Object_Entry> object_entries = new ArrayList<>();
        Object_Entry objectEntry = new Object_Entry();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("OrderId : " + purchase_in_warehouse.getPurchase_order_id() + "====");
        if (purchase_in_warehouse.getPurchase_order_id() == null) {
            purchase_in_warehouse.setPurchase_order_id(order_id);
            System.out.println("OrderId : " + purchase_in_warehouse.getPurchase_order_id() + "====");
        }
        // 缓冲中没有order_id对应的物品信息，则新建该缓存区
        if (order_id == "") {
            order_id = UuidUtil.getUuid();
        }
        temp_items.computeIfAbsent(order_id, k -> new ArrayList<Item>());
        System.out.println(temp_items.get(order_id));

        if (temp_items.get(order_id) != null) {
            for (Item item : temp_items.get(order_id)) {
                objectEntry.setObject_id(item.getObject_id());
                objectEntry.setNum((int) (item.getQuantity()));
                objectEntry.setOrder_id(purchase_in_warehouse.getPurchase_order_id());
                object_entries.add(objectEntry);
                item.setQuantity(0);
                System.out.println(item.getNotes());
            }
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");

        if (temp_items.get(order_id) != null) itemService.add(temp_items.get(order_id));

        System.out.println("Servlt : " + purchase_in_warehouse.getPurchase_in_warehouse_id());
        template_order to = new template_order(purchase_in_warehouse, object_entries);

        boolean flag = purchaseInService.add(to);
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
     * 借用入库
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void borrowIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        Borrow_in_Warehouse borrow_in_warehouse = new Borrow_in_Warehouse();
        //2.封装对象
        try {
            BeanUtils.populate(borrow_in_warehouse, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        System.out.println("Servlet " + borrow_in_warehouse.getBorrow_in_order_id());
        boolean flag = borrowInService.add(borrow_in_warehouse);
        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(flag);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

//        Map<String, String[]> map = request.getParameterMap();
//        template_order templateOrder = new template_order();
//        //2.封装对象
//        try {
//            BeanUtils.populate(templateOrder, map);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        boolean flag = borrowInService.add((Borrow_in_Warehouse) templateOrder.getTable());
//        ResultInfo info = new ResultInfo();
//        // 4. 响应结果
//        info.setFlag(flag);
//        // 将 info 对象序列化为 json
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(info);
//        // 将 json 数据写回客户端
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(json);
    }

    /**
     * 其他入库
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void otherIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(" 其 他 入 库 ---------------------------");
        Map<String, String[]> map = request.getParameterMap();
        Other_in_Warehouse otherInWarehouse = new Other_in_Warehouse();
        //2.封装对象
        try {
            BeanUtils.populate(otherInWarehouse, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        List<Object_Entry> object_entries = new ArrayList<>();
        Object_Entry objectEntry = new Object_Entry();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Id : " + otherInWarehouse.getOther_in_warehouse_id() + "====");
        if (otherInWarehouse.getOther_in_warehouse_id() == null) {
            otherInWarehouse.setOther_in_warehouse_id(UuidUtil.getUuid());
//            System.out.println("OrderId : " + otherInWarehouse.getOther_in_order_id() + "====");
        }
        // 缓冲中没有order_id对应的物品信息，则新建该缓存区
        if (order_id == "") {
            order_id = UuidUtil.getUuid();
            System.out.println(" ^^^^     OrderId : " + order_id);
        }
        temp_items.computeIfAbsent(order_id, k -> new ArrayList<Item>());
        System.out.println(temp_items.get(order_id));

        if (temp_items.get(order_id) != null) {
            for (Item item : temp_items.get(order_id)) {
                objectEntry.setObject_id(item.getObject_id());
                objectEntry.setNum((int) (item.getQuantity()));
                objectEntry.setOrder_id(order_id);
                object_entries.add(objectEntry);
                item.setQuantity(0);
                System.out.println(item.getNotes());
            }
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");

        if (temp_items.get(order_id) != null) itemService.add(temp_items.get(order_id));

        System.out.println("Servlt : " + otherInWarehouse.getOther_in_warehouse_id());
        template_order to = new template_order(otherInWarehouse, object_entries);
        boolean flag = otherInService.add(to);

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

    //获得order_id
    public void getOrderId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        order_id = request.getParameter("order_id");
    }

    // 更新清单
    public void UpdateItemsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String order_id = request.getParameter("order_id");
        System.out.println("PurchaseServlet order_id="+order_id);

        Item item_changed = new Item();
        Map<String, String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(item_changed, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("前端传回来的item对象如下：");
        item_changed.print();
        // 如果当前order_id没有关联的物品行，新建一个对应的缓存
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
            System.out.println("补充id属性");
            item_changed.print();
        }
        temp_items.get(order_id).add(item_changed);  // 缓存 前端修改的item


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

    // 初始化 物品清单列表
    public void initListTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("PurchaseServlet：进入 初始化物品清单弹窗表格 后端");
        String order_id = request.getParameter("order_id");
        System.out.println("PurchaseServlet order_id="+order_id);
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
        System.out.println("-------*************************----------"+result.size());
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
        return json;
    }


}
