package com.center.platform.service.impl;

import com.center.platform.dao.IExpertDao;
import com.center.platform.dao.IUserDao;
import com.center.platform.entity.Expert;
import com.center.platform.entity.User;
import com.center.platform.service.IExpertService;
import com.center.platform.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpertServiceImpl extends BaseLogger
        implements IExpertService
{

    @Autowired
    private IExpertDao expertDao;

    @Autowired
    private IUserDao userDao;

    public Expert validExpert(Expert exp)
    {
        try
        {
            List lst = this.expertDao.find(exp);
            if ((isNotNull(lst)) && (lst.size() > 0)) {
                return (Expert)lst.get(0);
            }

            Expert tmpExp = new Expert();
            List experts = this.expertDao.find(tmpExp);
            exp.setRolevalue(((Expert)experts.get(0)).getRolevalue());

            boolean istrue = this.expertDao.save(exp);

            User user = new User();
            user.setId(exp.getExpertid());
            user.setName(exp.getExpertname());
            user.setPassword(Utils.getPYIndexStr1(exp.getExpertname(), false));
            user.setRole(exp.getRolevalue());
            user.setType("0");
            user.setDepart(exp.getDepart());

            istrue = this.userDao.save(user);

            if (istrue) {
                return exp;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map query(String where)
    {
        try
        {
            Map resMap = new HashMap();

            Map nameMap = new HashMap();
            Map departMap = new HashMap();

            List condition = Arrays.asList(where.split(","));
            Expert exp = new Expert();
            exp.setConditions(condition);

            List<Expert> lst = this.expertDao.find(exp);

            if ((isNotNull(lst)) && (lst.size() > 0)) {
                for (Expert expert : lst)
                {
                    String pFirstCode = Utils.getPYIndexStr(expert.getExpertname(), true);
                    List tmpLst;
                    if (nameMap.containsKey(pFirstCode)) {
                        tmpLst = (List)nameMap.get(pFirstCode);
                        tmpLst.add(expert);
                    } else {
                        tmpLst = new ArrayList();
                        tmpLst.add(expert);
                    }
                    nameMap.put(pFirstCode, tmpLst);

                    String depart = expert.getDepart();
                    if (departMap.containsKey(depart)) {
                        tmpLst = (List)departMap.get(depart);
                        tmpLst.add(expert);
                    } else {
                        tmpLst = new ArrayList();
                        tmpLst.add(expert);
                    }
                    departMap.put(depart, tmpLst);
                }
            }

            nameMap = Utils.sortMapByKey(nameMap);
            departMap = Utils.sortMapByKey(departMap);

            resMap.put("name", Utils.MapToList(nameMap));
            resMap.put("depart", Utils.MapToList(departMap));
            return resMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}