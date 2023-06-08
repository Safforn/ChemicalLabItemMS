package web.servlet;

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
    private static Map<String, List<Object_Entry>> temp_items = new HashMap<>();
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

        for (Object_Entry object_entry : temp_items.get(purchase_requisition.getPurchase_order_id())) {
            object_entry.setOrder_id(purchase_requisition.getPurchase_order_id());
        }

        template_order templateOrder = new template_order(purchase_requisition, temp_items.get(purchase_requisition.getPurchase_order_id()));
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
        Object_Entry object_entry = new Object_Entry();
        Map<String, String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(object_entry, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // 如果当前order_id没有关联的物品行，新建一个对应的缓存
        temp_items.computeIfAbsent(order_id, k -> new ArrayList<Object_Entry>());

        if (object_entry.getObject_id()!= null) {  // 不是新建的item
            for (Object_Entry i : temp_items.get(order_id)) {  // 遍历已经缓存的关联该订单的Item
                if (i.getObject_id().equals(object_entry.getObject_id())) {  // 如果修改过的Item已经缓存过了
                    temp_items.get(order_id).remove(i);  // 从缓存中删除
                    break;  // 结束遍历
                }
            }
        } else {  // 新建的Item 补充id属性
            object_entry.setObject_id(UuidUtil.getUuid());
        }
        temp_items.get(order_id).add(object_entry);  // 缓存 前端修改的item


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


}
