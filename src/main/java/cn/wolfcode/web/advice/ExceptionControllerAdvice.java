package cn.wolfcode.web.advice;

import cn.wolfcode.qo.JsonResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;

//处理异常
@ControllerAdvice//增强控制器
public class ExceptionControllerAdvice {

    //Shiro权限异常
    @ExceptionHandler(AuthorizationException.class)
    public String handlerAuthorizationException(Model model, HandlerMethod handlerMethod, RuntimeException e, HttpServletResponse response) throws IOException {
        //根据不同的错误采取不同的处理方法（成功失败的响应都是同一种）
        //处理所有的错误
        e.printStackTrace();//在控制台留下错误信息
        ResponseBody annotation = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (annotation == null) {//响应html
            model.addAttribute("errorMsg","暂无权限");
            return "common/error";
        } else { //响应json
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            JsonResult jsonResult = new JsonResult(false,"暂无权限");
            writer.write(new ObjectMapper().writeValueAsString(jsonResult));
            return null;
        }
    }
    @ExceptionHandler(RuntimeException.class)
    public String handlerException2(Model model, HandlerMethod handlerMethod, RuntimeException e, HttpServletResponse response) throws IOException {
        //根据不同的错误采取不同的处理方法（成功失败的响应都是同一种）
        //处理所有的错误
        e.printStackTrace();//在控制台留下错误信息
        ResponseBody annotation = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (annotation == null) {//响应html
            model.addAttribute("errorMsg","服务器繁忙");
            return "common/error";
        } else { //响应json
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            JsonResult jsonResult = new JsonResult(false,"服务器繁忙");
            writer.write(new ObjectMapper().writeValueAsString(jsonResult));
            return null;
        }
    }
    //项目运行出了异常（没有处理的，没有try）才会执行这里的方法(贴注解@ExceptionHandler,可以指定处理异常的类型)
    // 方法形参也不一样，定义接收请求参数的形参，可以定义其他参数HandlerMethod类型 异常类型
    //HandelMethod（本质对Method对象封装而已）表示处理已成的处理请求的方法是哪个
    //异常类型，出的 异常信息对象给这里

    //返回类型跟处理请求的方法一样可以写这些，效果，一样
    // 第三个处理异常一般响应文件内容(但实际可以支持响应任意类型)
    //处理异常方法
    /*@ExceptionHandler(RuntimeException.class)//处理该异常类型及其子类异常
    public String handleException(Model model,HandlerMethod handlerMethod, RuntimeException e){//形参
        e.printStackTrace();//这行代码保留，不然项目后台出现异常，开发工具控制台看不出太多错误信息
        System.out.println("handleException1");
        model.addAttribute("msg",e.getMessage());
        return "error";//指定错误页面视图
    }*/
}
