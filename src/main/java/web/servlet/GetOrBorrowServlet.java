package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.get_or_borrow_service;
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


@WebServlet("/getorborrow/*")
public class GetOrBorrowServlet extends BaseServlet {
    private static final int SUBMITTED = 1;

    private final get_or_borrow_service service = new get_or_borrow_service_impl();
    private final order_service orderService = new order_service_impl();
    private static Map<String, List<Object_Entry>> temp_items = new HashMap<>();
    private static String order_id = "";

    /**
     * 领用物品
     */
    public void createOrUpdateItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GetOrBorrowServlet：进入 领用物品 的后端");
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
        for (Object_Entry object_entry : temp_items.get(get_or_borrow_requisition.getGet_or_borrow_order_id())) {
            object_entry.setOrder_id(get_or_borrow_requisition.getGet_or_borrow_order_id());
        }

        template_order templateOrder = new template_order(get_or_borrow_requisition, temp_items.get(get_or_borrow_requisition.getGet_or_borrow_order_id()));
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
