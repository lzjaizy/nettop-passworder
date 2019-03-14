package com.nettop.config;

import com.nettop.shiro.config.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShiroConfig {

   private static final String casFilterUrlPattern = "/shiro-cas";

   /**
    * CAS Filter
    */
   @Bean(name = "casFilter")
   public CasFilter getCasFilter(@Value("${shiro.cas}") String casServerUrlPrefix,
                                 @Value("${shiro.server}") String shiroServerUrlPrefix) {
      CasFilter casFilter = new CasFilter();
      casFilter.setName("casFilter");
      casFilter.setEnabled(true);
      String loginUrl = casServerUrlPrefix + "/login?service=" + shiroServerUrlPrefix + casFilterUrlPattern;
      casFilter.setFailureUrl(loginUrl);
      return casFilter;
   }

   @Bean(name = "shiroFilter")
   public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager,
                                            CasFilter casFilter,
                                            @Value("${shiro.cas}") String casServerUrlPrefix,
                                            @Value("${shiro.server}") String shiroServerUrlPrefix) {
      System.out.println("ShiroConfiguration.shirFilter()");
      ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
      shiroFilterFactoryBean.setSecurityManager(securityManager);
      //拦截器.
      Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
      // 配置不会被拦截的链接 顺序判断，因为前端模板采用了thymeleaf，这里不能直接使用 ("/static/**", "anon")来配置匿名访问，必须配置到每个静态目录

      filterChainDefinitionMap.put("/css/**", "anon");
      filterChainDefinitionMap.put("/fonts/**", "anon");
      filterChainDefinitionMap.put("/img/**", "anon");
      filterChainDefinitionMap.put("/js/**", "anon");
      filterChainDefinitionMap.put("/html/**", "anon");
      filterChainDefinitionMap.put("/login", "anon");
      filterChainDefinitionMap.put(casFilterUrlPattern, "casFilter");
      filterChainDefinitionMap.put("/logout","logout");
      //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
      //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
      //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
      filterChainDefinitionMap.put("/**", "authc");
      // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
      String loginUrl = casServerUrlPrefix + "/login?service=" + shiroServerUrlPrefix + casFilterUrlPattern;
      shiroFilterFactoryBean.setLoginUrl(loginUrl);

      Map<String, Filter> filters = new HashMap<>();
      LogoutFilter logoutFilter = new LogoutFilter();
      logoutFilter.setRedirectUrl(casServerUrlPrefix + "/logout?service=" + shiroServerUrlPrefix);
      filters.put("logout",logoutFilter);
      filters.put("casFilter", casFilter);
      shiroFilterFactoryBean.setFilters(filters);

      // 登录成功后要跳转的链接
      shiroFilterFactoryBean.setSuccessUrl("/index");

      //未授权界面;
      shiroFilterFactoryBean.setUnauthorizedUrl("/403");
      shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
      return shiroFilterFactoryBean;
   }

   /**
    * 凭证匹配器
    * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
    * ）
    * @return
    */
   @Bean
   public HashedCredentialsMatcher hashedCredentialsMatcher(){
      HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
      hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
      hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
      return hashedCredentialsMatcher;
   }

   @Bean
   public MyShiroRealm myShiroRealm(){
      MyShiroRealm myShiroRealm = new MyShiroRealm();
      myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
      return myShiroRealm;
   }


   @Bean
   public SecurityManager securityManager(@Value("${shiro.cas}") String casServerUrlPrefix,
                                          @Value("${shiro.server}") String shiroServerUrlPrefix){
      DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//      securityManager.setRealm(myShiroRealm());

      CasRealm casRealm = new CasRealm();
      casRealm.setDefaultRoles("ROLE_USER");
      casRealm.setCasServerUrlPrefix(casServerUrlPrefix);
      casRealm.setCasService(shiroServerUrlPrefix + casFilterUrlPattern);
      securityManager.setRealm(casRealm);
      securityManager.setCacheManager(new MemoryConstrainedCacheManager());
      securityManager.setSubjectFactory(new CasSubjectFactory());
      return securityManager;
   }

   /**
    *  开启shiro aop注解支持.
    *  使用代理方式;所以需要开启代码支持;
    * @param securityManager
    * @return
    */
   @Bean
   public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
      AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
      authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
      return authorizationAttributeSourceAdvisor;
   }

   @Bean(name="simpleMappingExceptionResolver")
   public SimpleMappingExceptionResolver
   createSimpleMappingExceptionResolver() {
      SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
      Properties mappings = new Properties();
      mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
      mappings.setProperty("UnauthorizedException","/user/403");
      r.setExceptionMappings(mappings);  // None by default
      r.setDefaultErrorView("error");    // No default
      r.setExceptionAttribute("exception");     // Default is "exception"
      //r.setWarnLogCategory("example.MvcLogger");     // No default
      return r;
   }

   @Bean
   public FilterRegistrationBean filterRegistrationBean() {
      FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
      filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
      filterRegistration.addInitParameter("targetFilterLifecycle", "true");
      filterRegistration.setEnabled(true);
      filterRegistration.addUrlPatterns("/*");
      return filterRegistration;
   }

   @Bean(name = "lifecycleBeanPostProcessor")
   public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
      return new LifecycleBeanPostProcessor();
   }
}