package cn.itcast.web.controller;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
     * 通过shiro进行登录认证
     * <p>
     * 1、访问地址 http://localhost:8080/index.jsp
     * 2、index.jsp页面跳转：window.location.href = "login.do";
     * 3、LoginController处理请求, 跳转到:/WEB-INF/pages/home/main.jsp
     */
    @RequestMapping(path = "/login")
    public String login(String email, String password) {
        //首先判断用户名密码是否为空
        if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password)) {
            return "forward:/login.jsp";
        }
        try {
            //获取Subject
            Subject subject = SecurityUtils.getSubject();
            //构造用户名及密码
            UsernamePasswordToken upToken = new UsernamePasswordToken(email, password);
            //借助subject完成用户登录
            subject.login(upToken);
            //通过shiro获取用户对象，保存到session中
            User user = (User) subject.getPrincipal();
            session.setAttribute("loginUser", user);
            //获取菜单数据
            List<Module> modules = moduleService.findModuleByUserId(user.getId());
            session.setAttribute("modules", modules);
            //跳转到成功页面
            return "home/main";
        } catch (Exception e) {
            e.printStackTrace();
            //登录失败跳转页面
            request.setAttribute("error", "用户名或密码错误");
            return "forward:login.jsp";
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
        //通过shiro实现退出（清除shiro中的认证信息）
        SecurityUtils.getSubject().logout();
        //清空session中的登录用户
        session.removeAttribute("loginUser");
        session.invalidate();
        return "forward:/login.jsp";
    }
}
