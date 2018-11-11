package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class FindPasswordParam {
	private String phoneCode;
	private String userPhone;
	private String checkCode;
	
}
