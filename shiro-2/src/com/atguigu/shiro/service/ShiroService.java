package com.atguigu.shiro.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

public class ShiroService {

    //注意如果有事务注解，则角色注解加载控制器上
    @RequiresRoles(value = {"admin"})
    public void testShiroAnnotation(){
        //Shiro里的session可以在Service层获取值
        Session session1 = SecurityUtils.getSubject().getSession();
        Object key1 = session1.getAttribute("key");
        System.out.println("Shiro的Session里的值:"+key1);
        System.out.println("shiro:"+new Date());
    }
}
