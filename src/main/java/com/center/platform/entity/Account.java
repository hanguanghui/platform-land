package com.center.platform.entity;

import java.util.Date;


/**
 * 账号实体
 */
@SuppressWarnings("serial")
public class Account implements java.io.Serializable {
	
	private String id;

	private String name;//账号名
	

	private String password;//密码

	private String description;//说明

	private String state;//账号状态  0 表示停用  1表示启用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ",name=" + name + ", password=" + password + ",description=" + description + ", state=" + state +  "]";
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
