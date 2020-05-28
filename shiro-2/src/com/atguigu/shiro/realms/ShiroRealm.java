package com.atguigu.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("[FirstRealm]--------");
        //1、将AuthenticationToken强转为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //2、从UsernamePasswordToken获取用户名
        String username = usernamePasswordToken.getUsername();
        //3、从数据库获取用户对应的记录
        System.out.println("从数据获取的用户名:" + username);
        //4、用户若不存在抛出UnknownAccountException异常
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户名不存在");
        }
        //5、根据用户信息情况决定是否抛出其他异常
        if ("monster".equals(username)) {
            throw new LockedAccountException("用户被锁定");
        }
        Object credentials = "";
        if ("admin".equals(username)) {
            credentials = "13215500ae4ea87b410201e85837af7f";
        } else if ("user".equals(username)) {
            credentials = "169f8cc66024d24306331acfe1064381";
        }
        //6、根据用户情况构建AuthenticationInfo对象
        /*
           1、principal:认证的实体信息，可以是数据库对应的实体类信息或者是用户名
           2、credentials:密码
           3、realmName:当前realm对象的name,调用父类的getName()即可
           4、盐值
         */
        ByteSource salt = ByteSource.Util.bytes(username);
        Object principal = username;
        String realmName = getName();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, credentials, salt, realmName);
        return simpleAuthenticationInfo;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "12345";
        ByteSource salt = ByteSource.Util.bytes("user");
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, 1024);
        System.out.println(result);
    }
    //授权时 回调的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1、从PrincipalCollection获取登录用户信息，有缓存时第二次就不会调用
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        //2、利用登录的用户信息查询其拥有的角色或权限("可能需要查询数据库")
        Set<String> roles=new HashSet<>();
        roles.add("user");
        if("admin".equals(primaryPrincipal)){
            roles.add("admin");
        }
        //3、创建SimpleAuthorizationInfo,并设置roles属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }
}
