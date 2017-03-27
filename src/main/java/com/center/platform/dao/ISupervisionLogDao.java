package com.center.platform.dao;

import com.center.platform.entity.SupervisionLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISupervisionLogDao {

    boolean save(SupervisionLog record);

    boolean delete(String recordId);

    List<SupervisionLog> find(SupervisionLog record);

}