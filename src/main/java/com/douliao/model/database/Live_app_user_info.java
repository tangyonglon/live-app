package com.douliao.model.database;

import lombok.Data;

@Data
public class Live_app_user_info {
	private int id;
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
	private int fans_number;
	private int version;
	private String praise_channel;
	private String comment_channel;
	private String gift_channel;
	
}
