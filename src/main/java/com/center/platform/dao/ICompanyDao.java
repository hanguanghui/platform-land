package com.center.platform.dao;

import com.center.platform.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompanyDao {
    List<Company> find(Company company);

    boolean save(Company company);

    boolean update(Company company);

    boolean delete(String companyid);
}