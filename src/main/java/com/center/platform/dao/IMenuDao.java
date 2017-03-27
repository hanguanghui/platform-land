package com.center.platform.dao;

import com.center.platform.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenuDao {
    List<Menu> find(Menu menu);

    Boolean save(Menu menu);

    Boolean delete(String id);

    Boolean update(Menu menu);

}