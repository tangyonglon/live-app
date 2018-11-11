package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class AuditParam {
	private int id;
	private int userId;
	private int status;
	private String update_time;
	
}
