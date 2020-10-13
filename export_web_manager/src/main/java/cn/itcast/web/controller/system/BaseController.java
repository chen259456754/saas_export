package cn.itcast.web.controller.system;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BaseController {
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
        return "1";
    }

    /**
     * 获取登录用户所属企业的名称
     */
    public String getLoginCompanyName() {
        return "传智播客教育股份有限公司";
    }
}
