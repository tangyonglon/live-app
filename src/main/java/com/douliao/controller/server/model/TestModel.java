package com.douliao.controller.server.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="请求参数对象",description="所有请求参数")
@Data
public class TestModel {
	@ApiModelProperty(value="用户id",name="id",example="1")
	private int id;
	@ApiModelProperty(value="用户名",name="user_name",example="张三")
	private String user_name;
	@ApiModelProperty(value="用户密码",name="password",example="123456")
	private String password;
	@ApiModelProperty(value="用户性别",name="sex",example="1")
	private int sex;
	@ApiModelProperty(value="用户年龄",name="age",example="12")
	private int age;
	@ApiModelProperty(value="用户状态",name="status",example="1")
	private int status;
}
