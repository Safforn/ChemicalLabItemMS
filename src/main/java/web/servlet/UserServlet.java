package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Order;
import domain.ResultInfo;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/user/*") // /user/add /user/find
public class UserServlet extends BaseServlet {
    //声明 UserService 业务对象
    private UserService service = new UserServiceImpl();
    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用 service 完成注册
        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
        }else{
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        //将 info 对象序列化为 json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将 json 数据写回客户端
        //设置 content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //1.获取用户名和密码数据

        Map<String, String[]> map = request.getParameterMap();
        //2.封装 User 对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用 Service 查询
        // UserService service = new UserServiceImpl();
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        //4.判断用户对象是否为 null
        if(u == null) {
            //用户名密码或错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码或错误");
        }
        //6.判断登录成功
        if(u != null){
            request.getSession().setAttribute("user",u);//登录成功标记
            //登录成功
            info.setFlag(true);
            if(user.getUid().equals("admin_")) {
                System.out.println("管理员登录ing.................");
                info.setData("admin_");
            } else {
                info.setData(user.getUid() + "is logging in.");
            }
        }
        //响应数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }
    /**
     * 查询单个对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //从 session 中获取登录用户
        Object user = request.getSession().getAttribute("user");
        //将 user 写回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }
    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //1.销毁 session
        request.getSession().invalidate();
        //2.跳转登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public void leaseOrBuy(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        Order order = new Order();
        try {
            BeanUtils.populate(order,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println(order.getNum() + " " + order.getType() + " " + order.getExpressid() + " " + order.getLocation() + " " + order.getUsertelephone());

        User user = (User) request.getSession().getAttribute("user");
        ResultInfo resultInfo = new ResultInfo();
        if (user == null) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("请登录");
        }
        else {
            order.setUid(user.getUid());
            String msg = service.leaseAndBuy(order);
            if (msg.equals("成功！")) {
                resultInfo.setFlag(true);
            }
            else {
                resultInfo.setFlag(false);
            }
            resultInfo.setErrorMsg(msg);
        }

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);
    }

    public void searchOrderByType(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        int type = Integer.parseInt(request.getParameter("type"));
        User user = (User) request.getSession().getAttribute("user");
        List<Order> orders = service.searchOrderByType(type, user.getUid());
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(orders);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),resultInfo);
    }

    //更新用户信息
    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("$"+user.getUid());
        //3.调用 service 完成注册
        service.updateUser(user);
        ResultInfo info = new ResultInfo();
        info.setFlag(true);
        writeValue(info, response);
    }

    //自动重新登录
    public void reLogin(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.getSession().invalidate();
        String uid = request.getParameter("uid");
        User u = service.loginByUid(uid);
        request.getSession().setAttribute("user", u);//登录成功标记
    }

}

