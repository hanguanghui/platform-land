package com.center.platform.security;

import com.center.platform.entity.Resources;
import com.center.platform.entity.Role;
import com.center.platform.service.ResourcesService;
import com.center.platform.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/8
 * @email jimboLix@163.com
 */
@Service
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private ResourcesService resourcesService ;
    @Autowired
    private RolesService rolesService;
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
    /**
     * @PostConstruct是Java EE 5引入的注解，
     * Spring允许开发者在受管Bean中使用它。当DI容器实例化当前受管Bean时，
     * @PostConstruct注解的方法会被自动触发，从而完成一些初始化工作，
     *
     * //加载所有资源与权限的关系
     */
    @PostConstruct
    private void loadResourceDefine() {
        if (resourceMap == null) {
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            List<Resources> menus = resourcesService.queryAll(new Resources());
            if (null != menus && menus.size() > 0) {
                for (Resources m : menus) {
                    Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
//                    通过资源名称来表示具体的权限 注意：必须"ROLE_"开头
                    List<Role> roles = rolesService.getRolesByResId(m.getId());
                    if(roles == null ||roles.size() <= 0){
                        ConfigAttribute configAttribute = new SecurityConfig("ROLE_ADMIN");
                        configAttributes.add(configAttribute);
                        resourceMap.put(m.getResUrl(), configAttributes);
                    }else {
                        for (Role role : roles) {
                            ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + role.getRolename());
                            configAttributes.add(configAttribute);
                            resourceMap.put(m.getResUrl(), configAttributes);
                        }
                    }
                }
            }
        }
    }
    //返回所请求资源所需要的权限
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        if(resourceMap == null) {
            loadResourceDefine();
        }
        if(requestUrl.indexOf("?")>-1){
            requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
        }
        Collection<ConfigAttribute> configAttributes = resourceMap.get(requestUrl);
        return configAttributes;
    }
}
