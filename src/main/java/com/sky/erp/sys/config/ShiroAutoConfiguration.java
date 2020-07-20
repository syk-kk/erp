package com.sky.erp.sys.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.sky.erp.sys.realm.UserRealm;
import lombok.Data;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Configuration
@Component
@ConfigurationProperties(prefix = "shiropropertity")
public class ShiroAutoConfiguration {

    private String hashAlgorithmName="md5";
    private int hashIterations=2;
    private String[] anonUrls;
    private String[] authcUlrs;

    /**
     * 配置凭证匹配器
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        credentialsMatcher.setHashIterations(hashIterations);
        return credentialsMatcher;
    }

    /**
     * 配置realm
     */
    @Bean
    public UserRealm userRealm(HashedCredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    /**
     * 配置安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 配置shiro过滤器
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition shiroFilterChainDefinition =
                new DefaultShiroFilterChainDefinition();
        Map<String,String> map = new HashMap<>();
        map.put("/logout","logout");
        if (anonUrls!=null&&anonUrls.length>0){
            for (String anon : anonUrls) {
                map.put(anon,"anon");
            }
        }
        if (authcUlrs!=null&&authcUlrs.length>0){
            for (String authc : authcUlrs) {
                map.put(authc,"authc");
            }
        }
        shiroFilterChainDefinition.addPathDefinitions(map);
        return shiroFilterChainDefinition;

    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
