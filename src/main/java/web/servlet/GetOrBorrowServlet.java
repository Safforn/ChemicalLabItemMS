package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.get_or_borrow_service;
import service.impl.get_or_borrow_service_impl;
import service.impl.order_service_impl;
import service.order_service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@WebServlet("/getorborrow/*")
public class GetOrBorrowServlet extends BaseServlet {
    private static final int SUBMITTED = 1;

    private final get_or_borrow_service service = new get_or_borrow_service_impl();
    private final order_service orderService = new order_service_impl();

    /**
     * 领用物品
     */
    public void createOrUpdateItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GetOrBorrowServlet：进入 领用物品 的后端");
        //1.获取数据（领用申请表基本信息 + 领用申请物品清单）
        Map<String, String[]> map = request.getParameterMap(); //获取前端数据，考虑分两次传输 申请表信息和物品清单

        //2.封装对象
//        get_or_borrow_Requisition get_or_borrow_requisition = new get_or_borrow_Requisition();
        // 前端传回申请表和物品列表的集合
        template_order templateOrder = new template_order();
        try {
            BeanUtils.populate(templateOrder, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
//        List<Object_Entry> object_entries = new ArrayList<>();
//        template_order to = new template_order(get_or_borrow_requisition, object_entries);


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
        String tableId = request.getParameter("table_id");
        boolean flag = service.deleteTable(tableId);
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
