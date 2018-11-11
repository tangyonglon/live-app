package com.douliao.result;

public class ResultView<T> {
	private int code;
	private String message;
	private T Data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return Data;
	}
	public void setData(T data) {
		Data = data;
	}
	
	@Override
	public String toString() {
		return "code="+code+",message="+message+",Data="+Data;
	}
	
}
