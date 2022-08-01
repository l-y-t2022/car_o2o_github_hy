package cn.wolfcode.homework;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class ShiroTest {
    @Test
    public void test(){
        //创建iniRealm，关联ini文件
        IniRealm iniRealm = new IniRealm("classpath:Shiro-authc.ini");
        //创建DefaultSecurityManger对象,并给其设置realm
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(iniRealm);
        //给SecurityUtils设置SecurityManager
        SecurityUtils.setSecurityManager(securityManager);
        //从SecurityUtils获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        //Subject对象用来提供用户(UsernamePasswordToken)认证的方法
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "555");
        subject.login(token);
        //获取认证状态
        System.out.println("认证状态"+subject.isAuthenticated());

        //退出登录
        subject.logout();
        System.out.println("认证状态"+subject.isAuthenticated());

    }
}
