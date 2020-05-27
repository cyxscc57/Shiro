package com.atguigu.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;

public class ShiroRealm extends AuthenticatingRealm {


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1、将AuthenticationTokenq强转为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) authenticationToken;
        //2、从UsernamePasswordToken获取用户名
            String username=usernamePasswordToken.getUsername();
        //3、从数据库获取用户对应的记录
        System.out.println("从数据获取的用户名"+username);
        //4、用户若不存在抛出UnknownAccountException异常
        if("unknown".equals(username)){
            throw new UnknownAccountException("用户名不存在");
        }
        //5、根据用户信息情况决定是否抛出其他异常
        if("monster".equals(username)){
            throw new LockedAccountException("用户被锁定");
        }
        //6、根据用户情况构建AuthenticationInfo对象
        /*
           1、principal:认证的实体信息，可以是数据库对应的实体类信息或者是用户名
           2、credentials:密码
           3、realmName:当前realm对象的name,调用父类的getName()即可
         */
        Object principal=username;
        Object credentials="12345";
        String realmName=getName();
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(principal,credentials,realmName);
        return simpleAuthenticationInfo;
    }
}
