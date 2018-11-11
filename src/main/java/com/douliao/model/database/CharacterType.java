package com.douliao.model.database;

import lombok.Data;

@Data
public class CharacterType {
	private int id;
	private int sex;
	private String type_name;
	private String create_time;
	private String update_time;
	private int status;
	
}
