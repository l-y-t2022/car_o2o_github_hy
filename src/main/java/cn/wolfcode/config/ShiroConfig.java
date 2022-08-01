package cn.wolfcode.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.wolfcode.realm.EmployeeRealm;
import cn.wolfcode.web.controller.DepartmentController;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

@Configuration
public class ShiroConfig {

    //配置Realm对象,配置自定义Reaml，从数据库查询认证数据，和授权数据
    @Bean
    public EmployeeRealm employeeRealm(){
        EmployeeRealm employeeRealm = new EmployeeRealm();
        //给realm设置缓存策略
//        employeeRealm.setCacheManager(cacheManager());
        //设置加盐加密凭证匹配器
        employeeRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return employeeRealm;
    }
    @Bean
    //配置凭证匹配器，告述框架加密类型，跟加密次数（盐在认证(返回值)的时候加上）
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(5);
        return hashedCredentialsMatcher;
    }
    //配置SecurityManager对象
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(employeeRealm());
        //配置session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //这个Subject对象 SecurityUtils.getSubject(),这个对象，不要让Spring管理

    //框架可以帮我们做认证，它会在请求过来帮我们创建Subject对象，会关联SecurityManager对象，并调用认证的Subject对象.login()
    //Shiro怎么知道我们的请求的路径，退出的路径是哪个？(自己配置请求路径和退出路径)

    //假设我们通过配置也告述Shiro路径，但是它是怎么实现的？
        //请求对象.getRequestURI()过滤器
    //为什么不用拦截器，因为拦截器，代表Shiro框架耦合Spring MVC 但项目不一定有MVC层，因此

    //配置Shiro过滤器,本质是利用这个ShiroFilterFactoryBean类帮我们创建ShiroFilter，拿到路径做判断进行登录
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器，必须设置
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //告诉Shiro项目的登录地址
        // 用途1；假设没有认证，shiro会帮跳转到该地址
        //用途2：若发送请求/static/login.html,且是post帆帆发，shirohi帮我们做登录(认证)
        shiroFilterFactoryBean.setLoginUrl("/static/login.html");

        //登录成功之后跳转的页面（不配置，我们自己写）
        //shiroFilterFactoryBean.setSuccessUrl("/employee/list");
        //指定没有让Shiro跳转到哪里，像下面给这样写，没有角色或者没有权限都是响应 HTML 响应
        //shiroFilterFactoryBean.setUnauthorizedUrl("/permission/nopermission");
        //告诉Shiro路径规则，什么路径做什么事情
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();//LinkedHashMap中key是去重的，有顺序的
        // key指定路径(根据项目的路径)，value指定做的事情
        //角色，权限一般不在这里配置，用注解来配置
        linkedHashMap.put("/favicon.ico","anon");//页面加载的login图标
        linkedHashMap.put("/static/**","anon");//anon：匿名拦截器，即不需要登录即可访问，一般用于静态资源过滤
        linkedHashMap.put("/login","anon");//登录不拦截
        linkedHashMap.put("/logout","logout");//退出登录
        linkedHashMap.put("/**","authc");//其他资源必须登录之后才能访问
        /**
         *若请求路径是/favicon.ico 或者是/static 的路径，/login不能拦截，都可以匿名(不用登录就能访问)
         *若请求路径是 /logout,Shiro帮我们退出subject对象.logout()方法
         *其他资源必须登录之后才能访问
         *
         */
        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);
        return shiroFilterFactoryBean;
    }


    //session管理器，去除访问路径拼接上的sessionID
    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    //配置以下内容使权限注解生效

    /**
     * 开启 Shiro 注解通知器
     * 用代理帮我们搞定，贴Shiro的相关注解的控制器，创建这个控制器的代理对象
     * jdk动态代理要实现接口，控制器没有实现接口，所以使用cglib动态代理(cglib不用实现接口)
     *
     * 代理对象，按照贴shiro注解，帮我们写代码判断有无角色和权限，checkRole checkPermission
     * 没有权限就抛出异常，有角色或者有权限，再去调用真实对象的方法
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        //要使用subject对象，所以要配置安全管理器
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * 设置改成使用 CGlib 代理
     * 详情看 DefaultAopProxyFactory#createAopProxy
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    //让我们写在Shiro中的配置生效
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    //配置缓存管理器
  /*  @Bean
    public CacheManager cacheManager(){
        //以后若想换别的缓存技术，就配置别的CacheManager接口实现类
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
        return ehCacheManager;
    }*/

}
