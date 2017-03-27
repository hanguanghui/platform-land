package com.center.platform.dao;

import com.center.platform.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
    boolean delete(Object id);

    boolean save(User record);

    List<User> find(User user);

    int update(User record);

}