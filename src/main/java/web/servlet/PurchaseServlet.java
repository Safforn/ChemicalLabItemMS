package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.impl.purchase_requisition_service_impl;
import service.purchase_requisition_service;
import web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@WebServlet("/purchase/*")
public class PurchaseServlet extends BaseServlet {

    private purchase_requisition_service service = new purchase_requisition_service_impl();

    public void createOrUpdateItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PurchaseServlet：进入 采购申请 后端");
        //1.获取数据（领用申请表基本信息 + 领用申请物品清单）
        Map<String, String[]> map = request.getParameterMap(); //获取前端数据，考虑分两次传输 申请表信息和物品清单
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + Arrays.toString(entry.getValue()));
        }
        //2.封装对象
        Purchase_Requisition purchase_requisition = new Purchase_Requisition();
        try {
            BeanUtils.populate(purchase_requisition, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // TODO: 调试，显示前端传回的数据
        purchase_requisition.print();

        List<Object_Entry> object_entries = new ArrayList<>();
        template_order to = new template_order(purchase_requisition, object_entries);
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
        // TODO: 更新前端表格页面，这个好像不需要做，前端会自动删除（加个"确认删除"的弹窗）
        //  只需要把tableId传给后端即可

    }
    /**
     * 获取当前登录用户user_id
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //从 session 中获取登录用户
        Object user = request.getSession().getAttribute("user");
        System.out.println("purchase findone="+((User)user).getUser_id());
        //将 user 写回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }
}
