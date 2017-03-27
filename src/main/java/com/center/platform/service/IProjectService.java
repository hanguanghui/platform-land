package com.center.platform.service;

import com.center.platform.entity.Company;
import com.center.platform.entity.Expert;
import com.center.platform.entity.Hproject;
import com.center.platform.entity.Relation;
import com.center.platform.entity.User;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public abstract interface IProjectService
{
    public List<Hproject> find(Hproject pro);

    public abstract boolean save(Hproject paramHproject, User paramUser, List<Expert> paramList, Company paramCompany);

    public abstract Page queryTasks(String paramString, int paramInt1, int paramInt2);

    public abstract Page queryRelationInfo(String paramString, int paramInt1, int paramInt2);

    public abstract Map rewordProject(String paramString1, String paramString2, String paramString3);

    public abstract List<Hproject> findProjectinfo(Relation paramRelation);

    public abstract boolean updateProjectStep(String paramString, int paramInt);

    public abstract Map cityReword(String[] paramArrayOfString, String paramString1, String paramString2);

    public abstract Map approve(String paramString1, String paramString2, String paramString3);
}