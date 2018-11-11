package com.douliao.controller.server.codapay;

import java.util.Map;

import lombok.Data;

@Data
public class CodaPayResult {
	
	private short resultCode = 0;
	private String resultDesc = null;
	private long txnId = 0;
	private Map<String, String> profile;
	
}
