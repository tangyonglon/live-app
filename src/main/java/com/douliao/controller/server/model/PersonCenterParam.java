package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class PersonCenterParam {
	private int userId;
	private String token;
	private int loginMode;
	private String mac;

}
