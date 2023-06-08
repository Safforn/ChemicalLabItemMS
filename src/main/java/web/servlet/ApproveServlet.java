package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.*;
import service.impl.*;
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

@WebServlet("/approve/*")

public class ApproveServlet extends BaseServlet {

    private purchase_requisition_service service = new purchase_requisition_service_impl();
    private order_service o_service = new order_service_impl();
    private item_service i_service = new item_service_impl();
    private static Map<String, List<Item>> temp_items = new HashMap<>();
    private static String order_id = "";

    public void initPurchaseTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("ApproveServlet：进入 初始化表格 后端");

        String purchaseDataList = updatePurchaseData();

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


    public String updatePurchaseData() throws IOException {
        System.out.println("ApproveServlet：进入updatePurchaseDataList");
        List<Purchase_Requisition> pr_list = service.searchTableByState(1);
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

//        String filename = "PurchaseData.txt";
//        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
    }


    // 初始化 物品清单列表
    public void initPurchaseListTable(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("ApproveServlet：初始化 物品清单列表");
        String order_id = request.getParameter("order_id");
        System.out.println("ApproveServlet order_id="+order_id);
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
        System.out.println("===================");
        System.out.println("长度："+temp_items.keySet());
        System.out.println("===================");
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
//        String filename = "PurchaseListData.txt";
//        JsonUtil.writeJson(filename, json);  //写入Data.json文件
        return json;
    }

    public void approveOver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Purchase_Requisition purchase_requisition = new Purchase_Requisition();
        Map<String, String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(purchase_requisition, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        purchase_requisition.print();
        //service.createOrUpdate();
    }

}
