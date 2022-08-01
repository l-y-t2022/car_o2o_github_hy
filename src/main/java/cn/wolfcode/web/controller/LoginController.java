package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.service.IEmployeeService;
import cn.wolfcode.service.IPermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/login")
    @ResponseBody
    //响应json
    public JsonResult login(String username, String password, HttpSession session){
        //调用subject对象调用方法
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username,password));
        } catch (UnknownAccountException e) {
            return new JsonResult(false,"用户名错误");
        } catch (IncorrectCredentialsException e) {
            return new JsonResult(false,"密码错误");
        } catch (Exception e) {
            return new JsonResult(false,"服务器维护中，请稍后访问");
        }

        return new JsonResult(true,"登录成功");
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //清除session中的数据
     /*   UserContext.removeEmployee();
        UserContext.removeExpressions();*/
        //重定向到登录页面
        return "redirect:/static/login.html";
    }
}
