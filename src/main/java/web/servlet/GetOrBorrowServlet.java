package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.get_or_borrow_service;
import service.impl.UserServiceImpl;
import service.impl.get_or_borrow_service_impl;
import web.servlet.BaseServlet;

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

    private get_or_borrow_service service = new get_or_borrow_service_impl();

    /**
     * 领用物品
     */
    public void createOrUpdateItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GetOrBorrowServlet：进入 领用物品 的后端");
        //1.获取数据（领用申请表基本信息 + 领用申请物品清单）
        Map<String, String[]> map = request.getParameterMap(); //获取前端数据，考虑分两次传输 申请表信息和物品清单

        //2.封装对象
        get_or_borrow_Requisition get_or_borrow_requisition = new get_or_borrow_Requisition();
        try {
            BeanUtils.populate(get_or_borrow_requisition, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        List<Object_Entry> object_entries = new ArrayList<>();
        template_order to = new template_order(get_or_borrow_requisition, object_entries);

        // 显示前端传回的数据
//        System.out.println("GetOrBorrowServlet: "+user.getAccount()+"|"+
//                user.getPassword()+"|"+user.getName()+"|"+
//                user.getEmail()+"|"+user.getPhonenumber());
        service.createOrUpdate(to);
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
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // 从前端获取 待删除申请表行的 tableId
        String tableId = request.getParameter("tableId");
        // 调用service删除
        service.deleteTable(tableId);
        // TODO: 更新前端表格页面，这个好像不需要做，前端会自动删除（加个"确认删除"的弹窗），只需要把tableId传给后端即可

    }
    /**
     * 获取当前登录用户user_id
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //从 session 中获取登录用户
        Object user = request.getSession().getAttribute("user");
        //将 user 写回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }
}
