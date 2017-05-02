package com.center.platform.dao;

import com.center.platform.entity.Account;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/8
 * @email jimboLix@163.com
 */
public interface AccountDao {
    Account countAccount(Account account);

    Account querySingleAccount(String username);
}
