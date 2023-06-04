package web.servlet_temp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.dao_temp.GoodsDao;
import dao.dao_temp.impl.GoodsDaoImpl;
import domain.Goods;
import domain.Order;
import domain.ResultInfo;
import org.apache.commons.beanutils.BeanUtils;
import service.service_temp.AdminService;
import service.service_temp.GoodsService;
import util.JsonUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/*")
public class AdminServlet extends BaseServlet {
    private GoodsService service = new GoodsServiceImpl();
    private AdminService adminService = new AdminServiceImpl();

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void addGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        Goods goods = new Goods();
        try {
            BeanUtils.populate(goods, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        String uid = "admin_";
        service.addToWarehouse(goods, uid);  //需要入库单的信息？
        ResultInfo info = new ResultInfo();
        info.setFlag(true);
        writeValue(info, response);
        updateGoodsJson();
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void updateGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        Goods goods = new Goods();
        try {
            BeanUtils.populate(goods, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用 service 完成注册
        service.updateGoods(goods);
        ResultInfo info = new ResultInfo();
        info.setFlag(true);
        writeValue(info, response);
        updateGoodsJson();
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateGoodsJson();
        String filename = "GoodsData.json";
        String data = JsonUtil.search(filename);  //读取

        ResultInfo resultInfo = new ResultInfo();  //封包
        resultInfo.setData(data);
        response.setContentType("application/json;charset=utf-8");
        writeValue(resultInfo, response);  //发送给前端
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchByKeyword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //write
        String keyword = request.getParameter("keyword");
        if (keyword != null) updateGoodsDataList(keyword);
        String filename = "GoodsDataList.json";
        String data = JsonUtil.search(filename);  //读取
        ResultInfo resultInfo = new ResultInfo();  //封包
        resultInfo.setData(data);
        response.setContentType("application/json;charset=utf-8");
        writeValue(resultInfo, response);  //发送给前端
    }

    /**
     *
     * @param keyword
     * @throws IOException
     */
    public void updateGoodsDataList(String keyword) throws IOException {
        List<Goods> goodsList = service.searchByKeyword(keyword);
        for (Goods g : goodsList) {
            System.out.println(g.getGid());
        }
        // 拼串
        String json = "";
        for (Goods g : goodsList) {
            try {
                json += writeValueAsString(g);
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
        String filename = "GoodsDataList.json";
        JsonUtil.writeJson(filename, json);  //写入Data.json文件
    }

    /**
     *
     * @throws IOException
     */
    private void updateGoodsJson() throws IOException {
        GoodsDao dao = new GoodsDaoImpl();
        List<Goods> goodsList = dao.searchAll();  //数据库取对象
        // 拼串
        String json = "";
        for (Goods g : goodsList) {
            json += writeValueAsString(g);
        }
        json = json.replace("}", "},");
        json = json.replaceFirst(".$","");  //去掉最后的逗号
        json += "]";
        StringBuffer buf = new StringBuffer();
        buf.append(json).insert(0, "[");
        json = buf.toString();
        String filename = "GoodsData.json";
        JsonUtil.writeJson(filename, json);
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void deleteGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gid = request.getParameter("gid");
        service.deleteGoods(gid);
        updateGoodsJson();
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchByType(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        int type = Integer.parseInt(request.getParameter("type"));
        List<Order> orders = adminService.searchOrderByType(type);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(orders);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);
    }

}
