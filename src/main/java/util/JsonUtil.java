package util;


import java.io.*;


public class JsonUtil {

//    static String filePath = "E:\\GitHub\\WebLab\\src\\main\\webapp\\json\\";
    //tsr
//    static String filePath = "E:\\program\\课设\\ChemicalLabItemMS\\src\\main\\webapp\\json\\";

    static String filePath = "D:\\ChemicalLabItemMS\\src\\main\\webapp\\json\\";

    private JsonUtil() {}

    private static String getSpaceOrTab(int tabNum) {
        StringBuffer sbTab = new StringBuffer();
        for (int i = 0; i < tabNum; i++) {
            sbTab.append('\t');
        }
        return sbTab.toString();
    }

    public static String stringToJSON(String strJson) {
        int tabNum = 0;
        StringBuffer jsonFormat = new StringBuffer();
        int length = strJson.length();
        for (int i = 0; i < length; i++) {
            char c = strJson.charAt(i);
            if (c == '{') {
                tabNum++;
                jsonFormat.append(c).append("\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            } else if (c == '}') {
                tabNum--;
                jsonFormat.append("\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
                jsonFormat.append(c);
            } else if (c == ',') {
                jsonFormat.append(c).append("\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            } else {
                jsonFormat.append(c);
            }
        }
        return jsonFormat.toString();
    }

    public static String search(String filename) throws IOException {
        String data = "";
        FileInputStream fis = new FileInputStream(filePath+filename);
        InputStreamReader isr = new InputStreamReader(fis,"utf-8");
        try (BufferedReader br = new BufferedReader(isr)){
            String line;
            while ((line = br.readLine()) != null) {
                data += line;
            }
        }
        isr.close();
        fis.close();
        data = stringToJSON(data);
        return data;
    }

    public static void writeJson(String filename, String jsonString) throws IOException {
        File file = new File(filePath+filename);
        if(!file.exists()) file.createNewFile();
        FileOutputStream fileOutputStream=new FileOutputStream(file);  //实例化FileOutputStream
        OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream,"utf-8");  //字符流转字节流
        BufferedWriter bufferedWriter= new BufferedWriter(osw);  //创建字符缓冲输出流对象
        String JsonString = stringToJSON(jsonString);//将jsonarrray字符串格式化
        bufferedWriter.write(JsonString);//将格式化的jsonarray字符串写入文件
        bufferedWriter.flush();//清空缓冲区，强制输出数据
        bufferedWriter.close();//关闭输出流
        osw.close();
        fileOutputStream.close();
    }


}
