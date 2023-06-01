package web.servlet;


import domain.Order;
import domain.ResultInfo;
import service.AdminService;
import service.OrderService;
import service.impl.AdminServiceImpl;
import service.impl.OrderServiceImpl;
import util.JsonUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/order/*")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();
    private AdminService adminService = new AdminServiceImpl();

    public void searchOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int type = Integer.parseInt(request.getParameter("type"));
        System.out.println(type);
        String filename;
        if (type == 1) {
            filename = "RentOrderData.json";
        }
        else {
            filename = "BuyOrderData.json";
        }
        updateOrderJson(type, filename);
        String data = JsonUtil.search(filename);  //读取
        ResultInfo resultInfo = new ResultInfo();  //封包
        resultInfo.setData(data);
        response.setContentType("application/json;charset=utf-8");
        writeValue(resultInfo, response);
    }

    private void updateOrderJson(int type, String filename) throws IOException {
        List<Order> orderList = orderService.searchOrderByType(type);
        // 拼串
        String json = "";
        for (Order o : orderList) {
            json += writeValueAsString(o);
        }
        json = json.replace("}", "},");
        json = json.replaceFirst(".$","");  //去掉最后的逗号
        json += "]";
        StringBuffer buf = new StringBuffer();
        buf.append(json).insert(0, "[");
        json = buf.toString();  //拼串完成

        JsonUtil.writeJson(filename, json);  //写入Data.json文件
    }

}
