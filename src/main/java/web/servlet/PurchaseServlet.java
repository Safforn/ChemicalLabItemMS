package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.impl.item_service_impl;
import service.impl.order_service_impl;
import service.impl.purchase_requisition_service_impl;
import service.item_service;
import service.order_service;
import service.purchase_requisition_service;
import util.JsonUtil;
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
    private order_service o_service = new order_service_impl();
    private item_service i_service = new item_service_impl();

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

        purchase_requisition.print();  // 调试，显示前端传回的数据

        // TODO: 前端表格里的物品清单数据还没传进来
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


    public void deleteTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // 从前端获取 待删除申请表行的 tableId
        String tableId = request.getParameter("tableId");
        // 调用service删除
        service.deleteTable(tableId);
        // TODO: 更新前端表格页面，这个好像不需要做，前端会自动删除（加个"确认删除"的弹窗）
        //  只需要把tableId传给后端即可

    }

    public void initTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("PurchaseServlet：进入 初始化表格 后端");
        String user_id = request.getParameter("user_id");
        System.out.println("PurchaseServlet user_id="+user_id);
        String purchaseDataList = "";
        if (user_id != null)
            purchaseDataList = updatePurchaseData(user_id);

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


    public String updatePurchaseData(String user_id) throws IOException {
        System.out.println("进入updatePurchaseDataList");
        List<Purchase_Requisition> pr_list = service.searchTableByUser(user_id);
        for (Purchase_Requisition pr : pr_list) {
            System.out.println(pr.getPurchase_requisition_id());
        }
        // 拼串
        String json = "";
        for (Purchase_Requisition pr : pr_list) {
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

        String filename = "PurchaseData.txt";
        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
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
        System.out.println("更新 物品清单列表"+order_id);

        // 获取物品清单的 全部物品行
        List<Object_Entry> oe_list = o_service.search(order_id);

        // 根据 order_id 查找 物品信息
        List<Item> items = new ArrayList<>();
        for (Object_Entry oe : oe_list) {
            System.out.println("物品id="+oe.getObject_id());
            Item item = i_service.search(oe.getObject_id());  // 获取物品信息
            item.setQuantity(oe.getQuantity());  // 修改数量（采购物品的数量在object_entry中）
            item.print();  // 显示对象信息
            items.add(item);  //添加进 物品信息表
        }

        // 拼串
        String json = "";
        for (Item i : items) {
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
        String filename = "PurchaseListData.txt";
        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
    }

}
