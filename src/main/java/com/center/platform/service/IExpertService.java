package com.center.platform.service;

import com.center.platform.entity.Expert;
import java.util.Map;

public abstract interface IExpertService
{
    public abstract Expert validExpert(Expert paramExpert);

    /**
     * 查询专家列表 返回根据部门 姓名首字母排序的map
     * @param paramString
     * @return
     */
    public abstract Map query(String paramString);
}