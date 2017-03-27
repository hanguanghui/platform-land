package com.center.platform.dao;


import com.center.platform.entity.ConstructLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConstructLogDao {

    boolean save(ConstructLog record);

    boolean delete(String constructlogid);

    List<ConstructLog> find(ConstructLog constructLog);
}