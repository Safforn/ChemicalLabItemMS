package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Goods;
import domain.ResultInfo;
import domain.bean.PageBean;
import service.GoodsService;
import service.impl.GoodsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/goods/*")
public class GoodsServlet extends BaseServlet {
    GoodsService goodsService = new GoodsServiceImpl();

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String gid = request.getParameter("gid");
        Goods goods = goodsService.findByGid(gid);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(goods);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException{
        List<Goods> goodsList = goodsService.findAll();
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(goodsList);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findByType(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String type = request.getParameter("type");
        List<Goods> goodsList = goodsService.findByType(type);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(goodsList);
        System.out.println(goodsList);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        //接受 gname 线路名称
        String gname = request.getParameter("gname");
        //2.处理参数
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }
        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示 5 条记录
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }
        //3. 调用 service 查询 PageBean 对象
        PageBean<Goods> pb = goodsService.pageQuery( currentPage, pageSize,gname);
        //4. 将 pageBean 对象序列化为 json，返回
        writeValue(pb,response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getPrice(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String gid = request.getParameter("gid");
        double price = goodsService.searchPrice(gid);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(price);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);
    }
}
