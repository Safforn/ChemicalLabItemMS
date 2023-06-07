package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import org.apache.commons.beanutils.BeanUtils;
import service.*;
import service.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
        template_order templateOrder = new template_order();
        //2.封装对象
        try {
            BeanUtils.populate(templateOrder, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
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
