package com.center.platform.service.impl;

import com.center.platform.dao.ResourcesDao;
import com.center.platform.entity.Resources;
import com.center.platform.service.ResourcesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/9
 * @email jimboLix@163.com
 */
@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService {
    @Resource
    private ResourcesDao resourcesDao;
    public List<Resources> findAccountResourcess(String accountId) {
        return resourcesDao.findAccountResourcess(accountId);
    }

    public List<Resources> findRoleRes(String roleId) {
        return null;
    }

    public List<Resources> queryAll(Resources resources) {
        return resourcesDao.queryAll(resources);
    }
}
