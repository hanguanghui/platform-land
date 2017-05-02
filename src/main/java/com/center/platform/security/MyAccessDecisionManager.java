package com.center.platform.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * 自己实现的过滤用户请求类
 * @author liruihui
 * @version 1.0
 * @date 2017/4/8
 * @email jimboLix@163.com
 */
@Service("accessDecisionManager")
public class MyAccessDecisionManager implements AccessDecisionManager {
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if(configAttributes == null) {
            return;
        }
        //所请求的资源拥有的权限(一个资源对多个权限)
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while(iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            //访问所请求资源所需要的权限
            String needPermission = configAttribute.getAttribute();
            //用户所拥有的权限authentication
            for(GrantedAuthority ga : authentication.getAuthorities()) {
                if(needPermission.equalsIgnoreCase(ga.getAuthority())) {
                    return;
                }
            }
        }
        //没有权限
        throw new AccessDeniedException(" 没有权限访问！ ");
    }

    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    public boolean supports(Class<?> aClass) {
        return true;
    }
}
