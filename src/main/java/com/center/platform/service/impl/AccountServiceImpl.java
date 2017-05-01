package com.center.platform.service.impl;

import com.center.platform.dao.AccountDao;
import com.center.platform.entity.Account;
import com.center.platform.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/9
 * @email jimboLix@163.com
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;
    public Account countAccount(Account account) {
        return null;
    }

    public Account querySingleAccount(String username) {
        if(username != null){
            return accountDao.querySingleAccount(username);
        }
        return null;
    }
}
