package cn.wolfcode;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

public class MyRealmTest {
    //授权，认证的数据来源于配置文件，文件后缀ini
    //配置文件中没有写认证数据，写用户的信息
    @Test
    public void testAuthorByIni(){
       /* //subject对象---》SecurityManger对象
        //创建对象Realms对象，从配置文件中获取数据
        MyRealm realm = new MyRealm();
        //创建SecurityManger对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //创建subject对象
        securityManager.setRealm(realm);

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
*/
    }
    //权限认证，认证的数据来源于配置文件，文件后缀ini
    //配置文件中没有写认证数据，写用户的信息
    @Test
    public void testAuthcByIni(){
       /* //subject对象---》SecurityManger对象
        //创建对象Realms对象，从配置文件中获取数据
        MyRealm myRealm = new MyRealm();

        //创建一个对象，用来比对密文密码
        //告述Shrio之前添加用户或者注册用户的时候的加密算法，盐，跟散列次数
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);

        //盐不是在上面的对象设置的，在MyRealm的认证中设置(在return的时候告诉盐)
        myRealm.setCredentialsMatcher(credentialsMatcher);

        //创建SecurityManger对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //创建subject对象
        securityManager.setRealm(myRealm);

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

        System.out.println("认证结果："+subject.isAuthenticated());*/
    }
}
