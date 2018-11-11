package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class LoginOutParam {
	private int userId;
	private String token;
	private int status;
	private int loginMode;
	
}
