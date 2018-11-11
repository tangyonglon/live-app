package com.douliao.model.database;

import lombok.Data;

@Data
public class Hobby {
	private int id;
	private String hobby_name;
	private String hobby_create_time;
	private String hobby_update_time;
	private int status;
	
}
