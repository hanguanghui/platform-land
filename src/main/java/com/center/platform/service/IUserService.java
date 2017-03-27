package com.center.platform.service;

import com.center.platform.entity.Menu;
import com.center.platform.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author hanguanghui
 * @version V1.0, 2016/11/24
 */
public interface IUserService {

    /**
     * 校验用户
     * @param user
     * @return
     */
    public User validUser(User user);

    /**
     * 根据用户角色获取左侧菜单树
     * @param user
     * @return
     */
    public List<Menu> getMenuLst(User user);

    public Page queryUsers(String where, int pageNum, int size);
}
