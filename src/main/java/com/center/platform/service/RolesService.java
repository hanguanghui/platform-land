package com.center.platform.service;

import com.center.platform.entity.Role;
import com.center.platform.entity.Roles;

import java.util.List;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/8
 * @email jimboLix@163.com
 */
public interface RolesService {
    Roles isExist(String name);

    List<Role> findbyAccountRole(String accountId);

    List<Role> getRolesByResId(String resourcesId);
}
