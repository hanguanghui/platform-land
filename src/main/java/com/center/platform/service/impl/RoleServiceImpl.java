package com.center.platform.service.impl;

import com.center.platform.dao.IRoleDao;
import com.center.platform.entity.Role;
import com.center.platform.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hangu on 2017/1/2.
 */
@Service
public class RoleServiceImpl extends BaseLogger implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    public List<Role> find() {
        Role role = new Role();
        List<Role> lst = roleDao.find(role);
        return lst;
    }
}
