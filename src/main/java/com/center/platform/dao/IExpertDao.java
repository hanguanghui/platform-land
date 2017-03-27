package com.center.platform.dao;

import com.center.platform.entity.Expert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExpertDao {

    public List<Expert> find(Expert expert);

    public boolean delete(String expertid);

    public boolean save(Expert expert);

    public boolean update(Expert expert);
}