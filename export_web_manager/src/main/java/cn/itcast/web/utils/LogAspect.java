package cn.itcast.web.utils;

import cn.itcast.domain.system.SysLog;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 日志切面类
 */
@Component
@Aspect
public class LogAspect {
    @Resource
    private SysLogService sysLogService;

    @Resource
    private HttpSession session;

    @Resource
    private HttpServletRequest request;

    /**
     * 使用环绕通知对controller方法进行增强，自动记录日志
     *
     * @param pjp
     * @return
     */
    @Around(value = "execution(* cn.itcast.web.controller.*.*.*(..)) && !bean(sysLogController))")
    public Object log(ProceedingJoinPoint pjp) {

        //获取用户登录信息
        User user = (User) session.getAttribute("loginUser");
        //获得调用的方法名和类名
        String methodName = pjp.getSignature().getName();
        String fullClassName = pjp.getTarget().getClass().getName();
        //创建SysLog对象
        SysLog log = new SysLog();
        //记录创建时间
        log.setTime(new Date());
        if (user != null) {
            //记录访问用户名
            log.setUserName(user.getUserName());
            //记录公司id
            log.setCompanyId(user.getCompanyId());
            //记录公司名
            log.setCompanyName(user.getCompanyName());
        }
        //记录访问的IP地址
        log.setIp(request.getLocalAddr());
        //记录调用的方法名
        log.setMethod(methodName);
        //记录方法的类名
        log.setAction(fullClassName);

        try {
                //保存日志信息到数据库
                sysLogService.save(log);
            //执行方法
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
