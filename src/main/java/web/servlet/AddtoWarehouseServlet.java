package web.servlet;

import domain.AddtoInfo;
import domain.ResultInfo;
import service.AddtoWarehouseService;
import service.impl.AddtoWarehouseServiceImpl;
import util.JsonUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/addto/*")
public class AddtoWarehouseServlet extends BaseServlet {
    private AddtoWarehouseService addtoWarehouseService = new AddtoWarehouseServiceImpl();

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchadd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateAddtoWarehouseJson();
        String filename = "AddtoWarehouseData.json";
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
        List<AddtoInfo> AddtoList = addtoWarehouseService.searchAllAdd();
        String json = "";
        for (AddtoInfo o : AddtoList) {
            json += writeValueAsString(o);
        }
        json = json.replace("}", "},");
        json = json.replaceFirst(".$","");  //去掉最后的逗号
        json += "]";
        StringBuffer buf = new StringBuffer();
        buf.append(json).insert(0, "[");
        json = buf.toString();  //拼串完成
        String filename = "AddtoWarehouseData.json";
        JsonUtil.writeJson(filename, json);  //写入Data.json文件
    }

}
