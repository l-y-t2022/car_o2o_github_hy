package cn.wolfcode.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class ShrioTest2 {
    @Test
    public void testLogin(){
        //先有Realm（指定ini文件的位置） ---- DefaultSecurityManager ---- Subject（用户）
        IniRealm iniRealm = new IniRealm("classpath:Shiro-authc.ini");
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(iniRealm);

        //给工厂类设置DefaultSecurityManager
        SecurityUtils.setSecurityManager(securityManager);

        //从工厂类获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        //获取登录对象
        UsernamePasswordToken toke = new UsernamePasswordToken("lisi", "666");

        //调用Subject方法登录
        subject.login(toke);

        //获取登录信息
        System.out.println("登录状态:"+subject.isAuthenticated());

        //退出登录
        subject.logout();

        //退出登录结果
        System.out.println("登录状态:"+subject.isAuthenticated());
    }
}
