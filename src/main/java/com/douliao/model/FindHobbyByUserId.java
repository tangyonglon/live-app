package com.douliao.model;

import lombok.Data;

@Data
public class FindHobbyByUserId {
	private int user_id;
	private int hobby_id;
	private String hobby_name;
	private int status;
	
}
