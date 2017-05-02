package com.center.platform.security;

import com.center.platform.entity.Account;
import com.center.platform.entity.Resources;
import com.center.platform.entity.Role;
import com.center.platform.service.AccountService;
import com.center.platform.service.ResourcesService;
import com.center.platform.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/8
 * @email jimboLix@163.com
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private RolesService rolesService;

    // 登录验证
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //取得用户的权限
        Account users = accountService.querySingleAccount(username);
        if (users == null)
            throw new UsernameNotFoundException(username + " not exist!");
        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
        // 封装成spring security的user
        User userdetail = new User(
                users.getName(),
                users.getPassword(),
                "1".equals(users.getState()) ? true : false,  //账号状态  0 表示停用  1表示启用
                true,
                true,
                true,
                grantedAuths    //用户的权限
        );
        return userdetail;
    }

    // 取得用户的权限
    private Set<GrantedAuthority> obtionGrantedAuthorities(Account account) {
        List<Role> roleList = rolesService.findbyAccountRole(account.getId());
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                //  用户所拥有的权限 注意：必须"ROLE_"开头
                authSet.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
            }
        }
        return authSet;
    }
}
