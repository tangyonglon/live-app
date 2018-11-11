package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class ProfitParam {
	private int userId;
	private int channel;
	private String date;
	private double user_score; 
	
}
