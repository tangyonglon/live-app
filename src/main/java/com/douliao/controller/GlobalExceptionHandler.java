package com.douliao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Map<String, Object> resultMap(){
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("code", 500);
		map.put("message", "系统错误！");
		return map;
	}
	
}
