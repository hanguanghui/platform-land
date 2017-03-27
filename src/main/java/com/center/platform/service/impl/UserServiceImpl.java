package com.center.platform.service.impl;

import com.center.platform.dao.IMenuDao;
import com.center.platform.dao.IUserDao;
import com.center.platform.entity.Menu;
import com.center.platform.entity.User;
import com.center.platform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hanguanghui
 * @version V1.0, 2016/11/24
 */
@Service
public class UserServiceImpl extends BaseLogger implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IMenuDao menuDao;
    @Override
    public User validUser(User user) {
        Assert.notNull(user,"user is null!");
        try {
            if (isNotNull(user)) {
                List<User> list = userDao.find(user);
                if (isNotNull(list) && list.size() > 0) {
                    user = list.get(0);
                    return user;
                }
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
        return null;
    }

    /**
     * 根据用户角色获取菜单树
     * @param user
     * @return
     */
    @Override
    public List<Menu> getMenuLst(User user) {
        Assert.notNull(user.getRole(),"user role is null");
        try{
            String role = user.getRole();
            List<Menu> lst = new ArrayList<Menu>();
            if (isNotNull(role) ) {
                Menu menu = new Menu();
                menu.setRolevalue(role);
                lst = menuDao.find(menu);
            }
            return lst;
        }catch (Exception ee){
            throw new RuntimeException(ee);
        }
    }

    @Override
    public Page queryUsers(String where, int pageNum, int size) {
        try{
            List condition = Arrays.asList(where.split(","));
            User user = new User();
            user.setConditions(condition);
            List<User> lst = userDao.find(user);

            int total = lst.size();
            int start = (pageNum - 1) * size;
            int end = pageNum * size > lst.size() ? lst.size() : pageNum * size;
            lst = lst.subList(start, end);

            return new PageImpl(lst, new PageRequest(pageNum, size), total);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
