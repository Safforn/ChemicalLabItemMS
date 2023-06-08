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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/ex/*")
public class ExServlet extends BaseServlet {
    private static final int APPROVED = 2;
    private final ex_service exService = new ex_service_impl();
    private final get_or_borrow_service getOrBorrowService = new get_or_borrow_service_impl();
    private final order_service orderService = new order_service_impl();
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
        boolean flag = exService.add(ex_warehouse);
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
