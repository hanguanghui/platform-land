package com.center.platform.dao;

import com.center.platform.entity.Material;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFileDao {
    List<Material> find(Material material);

    boolean delete(String fileid);

    boolean save(Material material);

    boolean update(Material material);
}