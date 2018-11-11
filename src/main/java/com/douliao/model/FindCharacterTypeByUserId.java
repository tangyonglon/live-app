package com.douliao.model;

import lombok.Data;

@Data
public class FindCharacterTypeByUserId {
	private int user_id;
	private int belong_id;
	private int character_id;
	private String type_name;
	private int sex;
	private int status;
	
}
