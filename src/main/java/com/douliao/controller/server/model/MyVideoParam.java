package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class MyVideoParam {
	private int userId;
	private String token;
	private int loginMode;
	private String mac;
	private int page;
	private int number;
	private int start;
	
}
