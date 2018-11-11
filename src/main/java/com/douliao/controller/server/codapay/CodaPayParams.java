package com.douliao.controller.server.codapay;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Data;

public class CodaPayParams {
	
	private String apiKey = null;
	private String orderId = null;
	private short country = 0;
	private short currency = 0;
	private short payType = 0;
	private ArrayList<ItemInfo> items = null;
	private HashMap<String, String> profile	= null;
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public short getCountry() {
		return country;
	}
	public void setCountry(short country) {
		this.country = country;
	}
	public short getCurrency() {
		return currency;
	}
	public void setCurrency(short currency) {
		this.currency = currency;
	}
	public short getPayType() {
		return payType;
	}
	public void setPayType(short payType) {
		this.payType = payType;
	}
	public ArrayList<ItemInfo> getItems() {
		return items;
	}
	public void setItems(ArrayList<ItemInfo> items) {
		this.items = items;
	}
	public HashMap<String, String> getProfile() {
		return profile;
	}
	public void setProfile(HashMap<String, String> profile) {
		this.profile = profile;
	}
	
	@Override
	public String toString() {
		return "apiKey : " + apiKey + "\torderId : " + orderId + "\tcountry : " + country + "\tcurrency : " + currency + "\tpayType : " + payType;
	}
	
}
