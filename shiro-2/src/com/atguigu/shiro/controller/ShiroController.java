package com.atguigu.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("shiro")
public class ShiroController {

    @RequestMapping("login")
    public String login(@RequestParam("username")String username,@RequestParam("password")String password){
        // get the currently executing user: 获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        //测试当前用户是否已经被认证，即是否已经登录
        //调用subject.isAuthenticated()
        if (!currentUser.isAuthenticated()) {
            //把用户名密码封装为 UsernamePasswordToken
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //remenberMe
            token.setRememberMe(true);
            try {
                //执行登录
                System.out.println(token.hashCode());
                currentUser.login(token);
                //若没有指定的账户，shiro会抛出UnknownAccountException异常
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            //所有认证时异常的父类
            catch (AuthenticationException ae) {
                System.out.println("LOGIN FAIL："+ae.getMessage());
            }
        }
        return "redirect:/list.jsp";
    }

}
