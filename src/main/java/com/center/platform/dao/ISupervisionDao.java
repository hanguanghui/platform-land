package com.center.platform.dao;


import com.center.platform.entity.Supervision;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ISupervisionDao {

    boolean save(Supervision record);

    List<Supervision> find(Supervision record);

    boolean delete(String id);
}