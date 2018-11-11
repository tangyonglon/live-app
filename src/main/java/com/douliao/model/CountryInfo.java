package com.douliao.model;

import lombok.Data;

@Data
public class CountryInfo {
	private int id;
	private String country_name;
	private String phone_code;
	private int language_id;
	private String language_code;
	private String currency_type;
	private String currency_code;
	
}
