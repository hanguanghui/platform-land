package com.center.platform.dao;

import com.center.platform.entity.Resources;

import java.util.List;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/9
 * @email jimboLix@163.com
 */
public interface ResourcesDao {
    List<Resources> queryAll(Resources resources);
    List<Resources> findAccountResourcess(String accountId);
}
