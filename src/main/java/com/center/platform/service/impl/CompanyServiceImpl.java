package com.center.platform.service.impl;

import com.center.platform.dao.ICompanyDao;
import com.center.platform.dao.IUserDao;
import com.center.platform.entity.Company;
import com.center.platform.entity.User;
import com.center.platform.service.ICompanyService;
import com.center.platform.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl extends BaseLogger
        implements ICompanyService
{

    @Autowired
    private ICompanyDao companyDao;

    @Autowired
    private IUserDao userDao;

    public Map find(String where)
    {
        try
        {
            Map map = new HashMap();
            List condition = Arrays.asList(where.split(","));
            Company company = new Company();
            company.setConditions(condition);

            List<Company> lst = this.companyDao.find(company);
            if ((isNotNull(lst)) && (lst.size() > 0)) {
                for (Company company1 : lst) {
                    String pFirstCode = Utils.getPYIndexStr(company1.getCompanyname(), true);
                    if (map.containsKey(pFirstCode)) {
                        List l = (List)map.get(pFirstCode);
                        l.add(company1);
                        map.put(pFirstCode, l);
                    } else {
                        List l = new ArrayList();
                        l.add(company1);
                        map.put(pFirstCode, l);
                    }
                }
            }
            return Utils.sortMapByKey(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Company validCompany(Company company, String type)
    {
        try
        {
            List lst = this.companyDao.find(company);
            if ((isNotNull(lst)) && (lst.size() > 0)) {
                return (Company)lst.get(0);
            }
            Company c = new Company();
            String role = ((Company)this.companyDao.find(c).get(0)).getRolevalue();

            company.setRolevalue(role);
            company.setCompanytype(type);

            boolean istrue = this.companyDao.save(company);

            User user = new User();
            user.setDepart(company.getCompanyname());
            user.setPassword(Utils.getPYIndexStr1(company.getCompanyname(), false));
            user.setId(company.getCompanyid());
            user.setRole(company.getRolevalue());
            user.setType("0");
            user.setName(company.getCompanyname());
            istrue = this.userDao.save(user);
            if (istrue) {
                return company;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}