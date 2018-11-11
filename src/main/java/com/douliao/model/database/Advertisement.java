package com.douliao.model.database;

import lombok.Data;

@Data
public class Advertisement {
	private int id;
	private String show_url;
	private String save_url;
	private String img_title;
	private String description;
	private int status;
	
}
