package com.center.platform.dao;

import com.center.platform.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleDao {
    List<Role> find(Role role);

    Boolean save(Role role);

    Boolean delete(String id);

    Boolean update(Role role);
}