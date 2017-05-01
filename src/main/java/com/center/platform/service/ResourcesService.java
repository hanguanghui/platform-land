package com.center.platform.service;

import com.center.platform.entity.Resources;

import java.util.List;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/8
 * @email jimboLix@163.com
 */
public interface ResourcesService {
    //< 根据账号Id获取该用户的权限
     List<Resources> findAccountResourcess(String accountId);
     List<Resources> findRoleRes(String roleId);

    List<Resources> queryAll(Resources resources);
}
