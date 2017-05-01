package com.center.platform.dao;

import com.center.platform.entity.Role;
import com.center.platform.entity.Roles;

import java.util.List;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/9
 * @email jimboLix@163.com
 */
public interface RolesDao {
     Roles isExist(String name);

     List<Role> findbyAccountRole(String accountId);

    void deleteAccountRole(String accountId);

    List<Role> getRolesByResId(String resourcesId);


}
