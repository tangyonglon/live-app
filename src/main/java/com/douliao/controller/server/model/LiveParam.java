package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class LiveParam {
	private int userId;
	private int status;
	private String start_time;
	private String end_time;
	private int time;
	private String date;
}
