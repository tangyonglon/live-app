package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class UpdatePasswordParam {
	private String phoneCode;
	private String userPhone;
	private String password;
	private String confirmPassword;
	
}
