package com.center.platform.service;

import com.center.platform.entity.Company;
import java.util.Map;

public abstract interface ICompanyService
{
    /**
     * 查询
     * @param paramString
     * @return
     */
    public abstract Map find(String paramString);

    /**
     * 验证公司是否存在  不存在则创建
     * @param paramCompany
     * @param paramString
     * @return
     */
    public abstract Company validCompany(Company paramCompany, String paramString);
}