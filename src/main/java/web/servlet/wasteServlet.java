package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.impl.item_service_impl;
import service.impl.order_service_impl;
import service.impl.waste_requisition_service_impl;
import service.item_service;
import service.order_service;
import service.waste_requisition_service;
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

@WebServlet("/waste/*")
public class wasteServlet extends BaseServlet{
    private waste_requisition_service wasteRequisitionService = new waste_requisition_service_impl();
    private order_service orderService = new order_service_impl();
    private item_service itemService = new item_service_impl();
    private static Map<String, List<Item>> temp_items = new HashMap<>();
    private static String order_id = "";

    public void createOrUpdateItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取数据（领用申请表基本信息 + 领用申请物品清单）
        Map<String, String[]> map = request.getParameterMap(); //获取前端数据，考虑分两次传输 申请表信息和物品清单

        //2.封装对象
        Waste_Requisition wasteRequisition = new Waste_Requisition();
        // 前端传回申请表和物品列表的集合
        try {
            BeanUtils.populate(wasteRequisition, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (order_id == "") order_id = UuidUtil.getUuid();
//        wasteRequisition.print();
        List<Object_Entry> objectEntries = new ArrayList<>();
        Object_Entry object_entry = new Object_Entry();
        if (wasteRequisition.getWaste_order_id() == null) {
            wasteRequisition.setWaste_order_id(order_id);
        }
        if (temp_items.get(order_id) != null) {
            for (Item item : temp_items.get(order_id)) {
//            item.setOrder_id(wasteRequisition.getWaste_order_id());
//                if (item.getObject_id() == null) {
//                    item.setObject_id(UuidUtil.getUuid());
//                }
                object_entry.setOrder_id(order_id);
                object_entry.setNum((int) item.getQuantity());
                object_entry.setObject_id(item.getObject_id());
                objectEntries.add(object_entry);

            }
        }
        if (temp_items.get(order_id) != null)
            itemService.add(temp_items.get(order_id));

        List<Object_Entry> result = new ArrayList<>();
        for (Object_Entry objectEntry : objectEntries) {
            result.add(objectEntry);
        }
        List<Object_Entry> ls = orderService.search(order_id);
        for (Object_Entry objectEntry : ls) {
            boolean b = true;
            for (Object_Entry j : objectEntries) {
                if (j.getObject_id().equals(objectEntry.getObject_id())) {
                    b = false;
                    break;
                }
            }
            if (b) {
                result.add(objectEntry);
            }
        }

        template_order templateOrder = new template_order(wasteRequisition, result);
        wasteRequisitionService.createOrUpdate(templateOrder);
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
        System.out.println("--------UpdateItemsList-----------");
        item.print();
        // 如果当前order_id没有关联的物品行，新建一个对应的缓存
        if (order_id == "") {
            order_id = UuidUtil.getUuid();
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
        System.out.println("在 temp_items.get(order_id).add(item) 之前 137行");
        item.print();
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


    public void deleteTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // 从前端获取 待删除申请表行的 tableId
        String tableId = request.getParameter("purchase_requisition_id");
        String orderID = request.getParameter("order_id");
        // 调用service删除
        orderService.deleteEntryByOrder(orderID);
        wasteRequisitionService.deleteTable(tableId);
        // TODO: 更新前端表格页面，这个好像不需要做，前端会自动删除（加个"确认删除"的弹窗）
        //  只需要把tableId传给后端即可

    }

    public void initTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("WasteServlet：进入 初始化表格 后端");
        String user_id = request.getParameter("user_id");
        System.out.println("WasteServlet user_id="+user_id);
        String wasteDataList = "";
        if (user_id != null)
            wasteDataList = updatePurchaseData(user_id);

        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(wasteDataList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


    public String updatePurchaseData(String user_id) throws IOException {
        System.out.println("进入updatePurchaseDataList");
        List<Waste_Requisition> pr_list = wasteRequisitionService.searchTableByUser(user_id);
        for (Waste_Requisition pr : pr_list) {
            System.out.println(pr.getWaste_requisition_id());
        }
        // 拼串
        String json = "";
        for (Waste_Requisition pr : pr_list) {
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
        return json;
    }


    // 初始化 物品清单列表
    public void initListTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("WasteServlet：进入 初始化物品清单弹窗表格 后端");
        String order_id = request.getParameter("order_id");
        System.out.println("WasteServlet order_id="+order_id);
        String WasteDataList = "";
        if (order_id != null)
            WasteDataList = updateWasteListData(order_id);


        ResultInfo info = new ResultInfo();
        // 4. 响应结果
        info.setFlag(true);
        info.setData(WasteDataList);
        // 将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将 json 数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public String updateWasteListData(String order_id) throws IOException {
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
