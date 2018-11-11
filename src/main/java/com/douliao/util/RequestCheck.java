package com.douliao.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.douliao.util.model.RequestIp;

public class RequestCheck {
	
	/**
	 * 访问频率检查
	 * @param request
	 * @return
	 */
	public static boolean requestFrequencyCheck(HttpServletRequest request) {
		String ip=NetUtil.getRemortIP(request);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//取session中的IP对象
        RequestIp re = (RequestIp) request.getSession().getAttribute(ip);
        //第一次请求
		if(null == re){
		     //放入到session中
		     RequestIp reIp = new RequestIp();
		     reIp.setCreateTime(System.currentTimeMillis());
		     reIp.setReCount(1);
		     request.getSession().setAttribute(ip,reIp);
		     return true;
		}else{
		     Long createTime = re.getCreateTime();
		     if(null == createTime){
		         //时间请求为空
		         return false;
		     }else{
		         if(((System.currentTimeMillis() - createTime)/1000) > 30){
		             System.out.println("通过请求！"+((System.currentTimeMillis() - createTime)/1000));
		             //当前时间离上一次请求时间大于3秒，可以直接通过,保存这次的请求
		             RequestIp reIp = new RequestIp();
		             reIp.setCreateTime(System.currentTimeMillis());
		             reIp.setReCount(1);
		             request.getSession().setAttribute(ip,reIp);
		             return true;
		         }else{
		             //小于3秒，并且3秒之内请求了10次，返回提示
		             if(re.getReCount() > 3){
		                 return false;
		             }else{
		                 //小于3秒，但请求数小于10次，给对象添加
		                 re.setCreateTime(System.currentTimeMillis());
		                 re.setReCount(re.getReCount()+1);
		                 request.getSession().setAttribute(ip,re);
		                 return true;
		             }
		         }
		     }
		}
	}
}
