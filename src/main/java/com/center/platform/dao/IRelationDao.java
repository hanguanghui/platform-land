package com.center.platform.dao;

import com.center.platform.entity.Relation;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface IRelationDao
{
    public abstract boolean delete(String paramString);

    public abstract boolean save(Relation paramRelation);

    public abstract List<Relation> find(Relation paramRelation);

    public abstract List<Relation> findByCompany(Relation paramRelation);

    public abstract boolean update(Relation paramRelation);
}