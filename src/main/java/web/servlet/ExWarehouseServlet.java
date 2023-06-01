package web.servlet;


import domain.*;
import service.ExWarehouseService;
import service.impl.ExWarehouseServiceImpl;
import util.JsonUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ex/*")
public class ExWarehouseServlet extends BaseServlet {
    private ExWarehouseService  exWarehouseService= new ExWarehouseServiceImpl();

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    // 读取data.json文件数据，通过httpServlet_resp传递给前端
    public void searchex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateAddtoWarehouseJson();
        String filename = "ExWarehouseData.json";
        String data = JsonUtil.search(filename);  //读取

        ResultInfo resultInfo = new ResultInfo();  //封包
        resultInfo.setData(data);
        response.setContentType("application/json;charset=utf-8");
        writeValue(resultInfo, response);  //发送给前端
    }

    /**
     *
     * @throws IOException
     */
    private void updateAddtoWarehouseJson() throws IOException {
        List<ExInfo> ExList = exWarehouseService.searchAllEx();
        // 拼串
        String json = "";
        for (ExInfo o : ExList) {
            json += writeValueAsString(o);
        }
        json = json.replace("}", "},");
        json = json.replaceFirst(".$","");  //去掉最后的逗号
        json += "]";
        StringBuffer buf = new StringBuffer();
        buf.append(json).insert(0, "[");
        json = buf.toString();  //拼串完成
        String filename = "ExWarehouseData.json";
        JsonUtil.writeJson(filename, json);
    }

}
