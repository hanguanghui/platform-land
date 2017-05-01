package com.center.platform.service;

import com.center.platform.entity.Account;

/**
 * @author liruihui
 * @version 1.0
 * @date 2017/4/8
 * @email jimboLix@163.com
 */
public interface AccountService {
    /**
     * 验证用户登陆
     * @param account
     * @return
     */
    Account countAccount(Account account);

    Account querySingleAccount(String username);


}
