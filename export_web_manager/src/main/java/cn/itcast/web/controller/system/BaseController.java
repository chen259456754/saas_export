package cn.itcast.web.controller.system;


import cn.itcast.domain.system.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public abstract class BaseController {
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpSession session;

    /**
     * 获取登录用户所属企业的id
     */
    public String getLoginCompanyId() {
        return getLoginUser().getCompanyId();
    }

    /**
     * 获取登录用户所属企业的名称
     */
    public String getLoginCompanyName() {
        return getLoginUser().getCompanyName();
    }

    /**
     * 获取登录的User信息
     *
     * @return
     */
    public User getLoginUser() {
        return (User) session.getAttribute("loginUser");
    }
}
