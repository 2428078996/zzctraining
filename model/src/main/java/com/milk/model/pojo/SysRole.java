package com.milk.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.milk.model.common.BaseEntity;
import lombok.Data;


@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@TableField("role_name")
	private String roleName;

	@TableField("role_code")
	private String roleCode;

	@TableField("description")
	private String description;

}

