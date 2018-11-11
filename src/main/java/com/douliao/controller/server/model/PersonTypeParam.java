package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class PersonTypeParam {
	private int userId;
	private String type_id;
	private int belong_id;
}
