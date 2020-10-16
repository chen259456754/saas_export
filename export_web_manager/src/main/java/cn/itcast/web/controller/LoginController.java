package cn.itcast.web.controller;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import cn.itcast.web.controller.system.BaseController;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class LoginController extends BaseController {

    @Resource
    UserService userService;
    @Resource
    ModuleService moduleService;

    /**
     * 用户登录的方法，判断用户登录信息是否正确
     * <p>
     * 1、访问地址 http://localhost:8080/index.jsp
     * 2、index.jsp页面跳转：window.location.href = "login.do";
     * 3、LoginController处理请求, 跳转到:/WEB-INF/pages/home/main.jsp
     */
    @RequestMapping(path = "/login")
    public String login(String email, String password) {
        //判断用户输入的数据是否为空
        if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password)) {
            return "forward:/login.jsp";
        }
        //通过邮箱账号查询用户信息
        User user = userService.findByEmail(email);
        //比较查询的用户信息和输入的密码是否一致
        if (user != null && password.equals(user.getPassword())) {
            //登录成功将用户数据保存到会话域中
            session.setAttribute("loginUser", user);
            //获取该用户拥有的权限
            String userId = user.getId();
            List<Module> modules = moduleService.findModuleByUserId(userId);
            session.setAttribute("modules", modules);
            return "home/main";
        } else {
            //登录失败则跳转到登录页面
            request.setAttribute("error", "用户名或密码错误！");
            return "forward:/login.jsp";
        }
    }

    /**
     * 处理main.jsp中的iframe内嵌框架中的/home.do请求
     */
    @RequestMapping(path = "/home")
    public String home() {
        return "home/home";
    }

    /**
     * 注销
     *
     * @return
     */
    @RequestMapping(path = "/logout")
    public String logout() {
        session.removeAttribute("loginUser");
        return "forward:/login.jsp";
    }
}
