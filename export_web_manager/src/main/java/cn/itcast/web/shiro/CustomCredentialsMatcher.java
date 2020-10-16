package cn.itcast.web.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取用户登录时的用户名
        String username = (String) token.getPrincipal();
        //获取用户登录时输入的密码
        String password = new String(((char[]) token.getCredentials()));
        //对用户输入的密码加密、加盐，把用户名作为盐
        String md5password = new Md5Hash(password, username).toString();
        //获取认证后的正确密码，即数据库中的密码
        String dbpassword = (String) info.getCredentials();
        //进行比对并返回比对结果
        return md5password.equals(dbpassword);
    }
}
