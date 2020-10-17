package cn.itcast.web.shiro;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义realm
 */
public class AuthRealm extends AuthorizingRealm {
    @Resource
    UserService userService;
    @Resource
    ModuleService moduleService;


    /**
     * 登录认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取到用户界面输入的邮箱地址和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //获取用户出入的邮箱和密码
        String email = upToken.getUsername();
        //根据邮箱查询用户信息
        User user = userService.findByEmail(email);
        if (user != null) {
            String password = user.getPassword();
            String name = this.getName();
            /*
             *第一个参数：安全数据（user对象）
             * 第二个参数：密码（数据库中的用户密码）
             * 第三个参数：当前调用realm域的名称
             * */
            return new SimpleAuthenticationInfo(user, password, name);
        }
        return null;
    }

    /**
     * 获取授权信息
     * 授权访问校验
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前登录的用户对象
        User user = (User) principals.getPrimaryPrincipal();
        String userId = user.getId();
        //获取用户拥有的权限
        List<Module> moduleList = moduleService.findModuleByUserId(userId);
        //获取存放可操作模块名称的容器
        Set<String> permissions = new HashSet<>();
        //遍历集合将模块名称存入容器
        for (Module module : moduleList) {
            String name = module.getName();
            permissions.add(name);
        }
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        //将容器存入到授权对象中
        sai.addStringPermissions(permissions);
        return sai;
    }
}
