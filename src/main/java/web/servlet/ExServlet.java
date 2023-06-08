package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.ex_service;
import service.get_or_borrow_service;
import service.impl.ex_service_impl;
import service.impl.get_or_borrow_service_impl;
import service.impl.order_service_impl;
import service.order_service;
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
    private static final Map<String, List<Object_Entry>> temp_items = new HashMap<>();
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

        for (Object_Entry object_entry : temp_items.get(ex_warehouse.getEx_order_id())) {
            object_entry.setObject_entry_id(UuidUtil.getUuid());
            object_entry.setObject_id(ex_warehouse.getEx_order_id());
        }


        boolean flag = exService.add(ex_warehouse, temp_items.get(ex_warehouse.getEx_order_id()));
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

        if (object_entry.getObject_entry_id()!= null) {  // 不是新建的item
            for (Object_Entry i : temp_items.get(order_id)) {  // 遍历已经缓存的关联该订单的Item
                if (i.getObject_id().equals(object_entry.getObject_id())) {  // 如果修改过的Item已经缓存过了
                    temp_items.get(order_id).remove(i);  // 从缓存中删除
                    break;  // 结束遍历
                }
            }
        } else {  // 新建的Item 补充id属性
            object_entry.setObject_entry_id(UuidUtil.getUuid());
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
}
