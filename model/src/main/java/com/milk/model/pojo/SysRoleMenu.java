package com.milk.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.milk.model.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "角色菜单")
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色id")
	@TableField("role_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long roleId;

	@ApiModelProperty(value = "菜单id")
	@TableField("menu_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long menuId;

}

