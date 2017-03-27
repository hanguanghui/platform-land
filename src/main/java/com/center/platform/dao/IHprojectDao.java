package com.center.platform.dao;

import com.center.platform.entity.Assignment;
import com.center.platform.entity.Hproject;
import com.center.platform.entity.Relation;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface IHprojectDao
{
    public abstract boolean delete(String paramString);

    public abstract boolean save(Hproject paramHproject);

    public abstract List<Hproject> find(Hproject paramHproject);

    public abstract List<Hproject> queryByAssign(Assignment paramAssignment);

    public abstract boolean update(Hproject paramHproject);

    public abstract List<Hproject> findProjectinfo(Relation paramRelation);
}