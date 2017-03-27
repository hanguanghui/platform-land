package com.center.platform.dao;

import com.center.platform.entity.Assignment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAssignmentDao {
    boolean delete(Object assignmentId);

    boolean save(Assignment record);

    List<Assignment> find(Assignment assignment);

    boolean update(Assignment record);

}