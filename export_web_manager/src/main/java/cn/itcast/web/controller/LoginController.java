package cn.itcast.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    /**
     * 1、访问地址 http://localhost:8080/index.jsp
     * 2、index.jsp页面跳转：window.location.href = "login.do";
     * 3、LoginController处理请求, 跳转到:/WEB-INF/pages/home/main.jsp
     */
    @RequestMapping(path = "/login")
    public String login() {
        //默认为登录成功，跳转到主页
        return "home/main";
    }

    /**
     * 处理main.jsp中的iframe内嵌框架中的/home.do请求
     */
    @RequestMapping(path = "/home")
    public String home(){
        return "home/home";
    }
}
