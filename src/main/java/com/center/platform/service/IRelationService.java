package com.center.platform.service;

import com.center.platform.entity.Hproject;
import com.center.platform.entity.Relation;

import java.util.List;

/**
 * @author hanguanghui
 * @version V1.0, 2016/12/18
 */
public interface IRelationService {
    boolean delete(String relationId);

    boolean save(Relation record);

    List<Relation> find(Relation relation);

    List<Relation> findByCompany(Relation relation);

    boolean update(Relation record);

    int validRelateProgress(String proid);

}
