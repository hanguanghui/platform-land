package com.center.platform.security;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;
import java.util.Collection;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/8
 * @email jimboLix@163.com
 */
@Service
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private MySecurityMetadataSource securityMetadataSource;
    @Autowired
    private MyAccessDecisionManager accessDecisionManager;
    @Autowired
    private AuthenticationManager myAuthenticationManager;

    @PostConstruct
    public void init(){
        super.setAuthenticationManager(myAuthenticationManager);
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        // object为FilterInvocation对象
        //super.beforeInvocation(fi);源码
        //1.获取请求资源的权限
//        Collection<ConfigAttribute> attributes = securityMetadataSource.getAttributes(fi);
//        //2.是否拥有权限
//        this.accessDecisionManager.decide(authenticated, object, attributes);
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void destroy() {

    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }
}
