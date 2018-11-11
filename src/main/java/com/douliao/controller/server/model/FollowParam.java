package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class FollowParam {
	private int userId;
	private int fans_user_id;
	private String create_time;
	private int status;
	
}
