package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class UpdatePersonParam {
	private String userName;
	private int userAge;
	private int userSex;
	private double userHeight;
	private double userWeight;
	private String makeFriendsGoal;
	private int userId;
	private String token;
	private String mac;
	private int loginMode;
	
}
