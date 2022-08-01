package cn.wolfcode;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

public class ShrioTest {
    @Test
    public void test(){
        System.out.println(new Md5Hash("罗怡曼"));
        System.out.println(new Md5Hash("罗怡曼"));
        System.out.println(new Md2Hash("罗怡曼"));
        System.out.println(new Sha1Hash("罗怡曼"));
        System.out.println(new Sha256Hash("罗怡曼"));
        System.out.println(new Sha512Hash("罗怡曼"));
        System.out.println(new Md5Hash("555", "zs", 2));
        System.out.println(new Md5Hash("666", "lisa", 2));


    }

    //授权，认证的数据来源于配置文件，文件后缀ini
    //配置文件中没有写认证数据，写用户的信息
    @Test
    public void testAuthorByIni(){
        //subject对象---》SecurityManger对象
        //创建对象Realms对象，从配置文件中获取数据
        IniRealm iniRealm = new IniRealm("classpath:Shiro-authc.ini");
        //创建SecurityManger对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //创建subject对象
        securityManager.setRealm(iniRealm);

        //给这个工具类 SecurityUtils 设置 SecurityManager 对象
        SecurityUtils.setSecurityManager(securityManager);

        //创建Subject对象，创建这个对象过程，获取SecurityManager对象，给Subject绑定
        Subject subject = SecurityUtils.getSubject();
        //调用其认证（登录）的方法
        //toke邓庄登录用户名和密码
        UsernamePasswordToken toke = new UsernamePasswordToken("zhangsan", "555");
        subject.login(toke);

        //调用Subject对象方法，判断是否登录成
        System.out.println("登录之后认证结果:"+subject.isAuthenticated());

        //添加授权
        //怎么判断访问用户有无角色
        System.out.println(subject.hasAllRoles(Arrays.asList("hr", "seller")));
        System.out.println(subject.hasRole("hr"));
        System.out.println(subject.hasRole("mag"));
        System.out.println(Arrays.toString(subject.hasRoles(Arrays.asList("hr", "seller", "abc"))));
        System.out.println("---------权限---------");
        //判断用户有无权限
        System.out.println(subject.isPermittedAll("user:delete", "user:save", "user:list"));//一起判断
        System.out.println(Arrays.toString(subject.isPermitted("user:list", "user:delete")));//逐个判断
        System.out.println(subject.isPermitted("user:delete"));//true
        System.out.println(subject.isPermitted("user:save"));//false

        //调用Subject对象方法，退出
        subject.logout();

        System.out.println("认证结果："+subject.isAuthenticated());

    }
    //权限认证，认证的数据来源于配置文件，文件后缀ini
    //配置文件中没有写认证数据，写用户的信息
    @Test
    public void testAuthcByIni(){
        //密文密码认证
        //subject对象---》SecurityManger对象
        //创建对象Realms对象，从配置文件中获取数据
        Realm iniRealm = new IniRealm("classpath:Shiro-authc.ini");
        //创建SecurityManger对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //创建subject对象
        securityManager.setRealm(iniRealm);

        //给这个工具类 SecurityUtils 设置 SecurityManager 对象
        SecurityUtils.setSecurityManager(securityManager);

        //创建Subject对象，创建这个对象过程，获取SecurityManager对象，给Subject绑定
        Subject subject = SecurityUtils.getSubject();
        //调用其认证（登录）的方法
        //toke邓庄登录用户名和密码
        UsernamePasswordToken toke = new UsernamePasswordToken("zhangsan", "555");
        subject.login(toke);

        //调用Subject对象方法，判断是否登录成
        System.out.println("登录之后认证结果:"+subject.isAuthenticated());

        //调用Subject对象方法，退出
        subject.logout();

        System.out.println("认证结果："+subject.isAuthenticated());
    }
}
