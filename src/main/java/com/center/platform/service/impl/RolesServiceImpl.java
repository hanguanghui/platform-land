package com.center.platform.service.impl;

import com.center.platform.dao.RolesDao;
import com.center.platform.entity.Resources;
import com.center.platform.entity.Role;
import com.center.platform.entity.Roles;
import com.center.platform.service.ResourcesService;
import com.center.platform.service.RolesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/9
 * @email jimboLix@163.com
 */
@Service("rolesService")
public class RolesServiceImpl implements RolesService {
    @Resource
    private RolesDao rolesDao;
    public Roles isExist(String name) {
        return null;
    }

    public List<Role> findbyAccountRole(String accountId) {
        return rolesDao.findbyAccountRole(accountId);
    }

    public List<Role> getRolesByResId(String resourcesId) {
        if(resourcesId == null || resourcesId.equals(" ")) {
            return null;
        }
        return rolesDao.getRolesByResId(resourcesId);
    }
}
