package com.center.platform.entity;

import java.util.List;

/**
 * @author hanguanghui
 * @version V1.0, 2016/9/27
 */
public class BaseEntity {
    protected String orderByClause;

    protected boolean distinct;

    protected List<String> conditions;

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }
}
