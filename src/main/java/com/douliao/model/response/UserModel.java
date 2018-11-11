package com.douliao.model.response;

import lombok.Data;

@Data
public class UserModel {
	private String user_phone;
	private String user_password;
	private int user_id;
	private String user_name;
	private String user_head;
	private int user_age;
	private int user_sex;
	private double user_package;
	private double user_score;
	private int user_identity;
	private int user_authentication;
	private int user_level;
	private double user_weight;
	private double user_height;
	private String make_friends_goal;
	private String token;
	
}
