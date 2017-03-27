package com.center.platform.service;

import com.center.platform.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hangu on 2017/1/2.
 */
public interface IRoleService {

    public List<Role> find();

}
