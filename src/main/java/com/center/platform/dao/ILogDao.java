package com.center.platform.dao;

import com.center.platform.entity.Log;

import java.util.List;

public interface ILogDao {

    boolean delete(String id);

    boolean insert(Log record);

    List<Log> find(Log record);

    List<String> findYears(Log log);
}