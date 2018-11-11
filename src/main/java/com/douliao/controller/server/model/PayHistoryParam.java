package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class PayHistoryParam {
	private int userId;
	private int page;
	private int number;
	private int start;
	
}
