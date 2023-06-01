package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.GoodsDao;
import dao.impl.GoodsDaoImpl;
import domain.Goods;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import java.io.*;
import util.JsonUtil;

import javax.servlet.ServletException;


public class AdminServletTest {

    @Test
    public void searchAll() {
        List<Goods> goodsList = new ArrayList<Goods>();
        Goods g1 = new Goods("1","2","3",4.0,
                "5",6,"7","8",0);
        Goods g2 = new Goods("2","2","3",4.0,
                "5",6,"7","8",0);
        goodsList.add(g1);
        goodsList.add(g2);
        for (Goods g : goodsList) {
            System.out.println(g.getGid());
        }
    }

    public static String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    public static void main(String[] args) throws ServletException, IOException {
        GoodsDao dao = new GoodsDaoImpl();
        List<Goods> goodsList = dao.searchAll();  //数据库取对象
        // 拼串
        String json = "";
        for (Goods g : goodsList) {
            json += writeValueAsString(g);
            //json.replace("[", "");
            //json.replace("]", "");
        }
        json = json.replace("}", "},");
        json = json.replaceFirst(".$","");  //去掉最后一组数据块结尾的逗号
        json += "]";
        StringBuffer buf = new StringBuffer();
        buf.append(json).insert(0, "[");
        json = buf.toString();  //拼串完成

        String jsonpath = "D:\\develop\\WebLab\\src\\main\\webapp\\json\\GoodsData.json";
        JsonUtil.writeJson(jsonpath, json);  //写入Data.json文件
        System.out.println(json);
    }

    @Test
    public void search() {
        try {
            String jsonpath = "D:\\develop\\WebLab\\src\\main\\webapp\\json\\GoodsData.json";
            JsonUtil.search(jsonpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}