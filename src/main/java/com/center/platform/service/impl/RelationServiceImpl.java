package com.center.platform.service.impl;

import com.center.platform.dao.IRelationDao;
import com.center.platform.entity.Hproject;
import com.center.platform.entity.Relation;
import com.center.platform.service.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hanguanghui
 * @version V1.0, 2016/12/18
 */
@Service
public class RelationServiceImpl extends BaseLogger implements IRelationService  {
    @Autowired
    private IRelationDao relationDao;
    @Override
    public boolean delete(String relationId) {
        return relationDao.delete(relationId);
    }

    @Override
    public boolean save(Relation record) {
        return relationDao.save(record);
    }

    @Override
    public List<Relation> find(Relation relation) {
        return relationDao.find(relation);
    }

    @Override
    public List<Relation> findByCompany(Relation relation) {
        return relationDao.findByCompany(relation);
    }

    @Override
    public boolean update(Relation record) {
        return relationDao.update(record);
    }

    /**
     * 判断该项目 是否通过所有专家审核
     * @param proid
     * @return
     */
    @Override
    public int validRelateProgress(String proid) {
        try{
            int res = 2;
            Relation relation = new Relation();
            relation.setProjectId(proid);
            List<Relation> lst = this.find(relation);
            if(isNotNull(lst)&& lst.size()>0){
                for (Relation relation1 : lst) {
                    if("0".equals(relation1.getState())){
                       res = 0;
                        break;
                    }
                    if("2".equals(relation1.getState())){
                        res = 1;
                        break;
                    }
                }
            }
            return res;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
