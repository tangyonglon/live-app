package com.douliao.util.yunpian;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.douliao.util.Log4jUtil;



public class YunpianUtil{
	
	public static void main(String[] args) throws Exception {
		int code=(int)((Math.random()*6+1)*100000);
		System.out.println(code);
		String apikey="0bf0e8c1853d35bddb6b5ee025e00cd9";
		String text="【echat】your verification code:"+String.valueOf(code);
		String mobile="8615989463213";
		String url="https://sms.yunpian.com/v2/sms/single_send.json";
		boolean result=singleSend(apikey,text,mobile,url);
		System.out.println("返回结果："+result);
	}
	
	/**
	 * 单条短信发送
	 * @throws Exception 
	 */
	public static boolean singleSend(String apikey, String text, String mobile,String url) throws Exception {
	    List<BasicNameValuePair> content = new ArrayList<BasicNameValuePair>();
	    content.add(new BasicNameValuePair("apikey", apikey));
	    content.add(new BasicNameValuePair("text", text));
	    content.add(new BasicNameValuePair("mobile", "+"+mobile));
	    Log4jUtil.info(content.get(2).getName()+":"+content.get(2).getValue());
	    String result=sentHttpPostRequest(url, content);	//请自行使用post方式请求,可使用Apache HttpClient
//	    System.out.println("短信平台发送短信返回结果:"+result);
	    Log4jUtil.info("短信平台发送短信返回结果："+result);
	    
	    JSONObject jsStr = JSONObject.parseObject(result);
	    System.out.println(jsStr.get("code"));
	    String code=String.valueOf(jsStr.get("code"));
	    if("0".equals(code)) {
	    	return true;
	    }
	    return false;
	}
	
	
	public static String sentHttpPostRequest(String url, List<BasicNameValuePair> content) throws Exception {
        //构建HttpClient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();
        //指定POST请求
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);

        //包装请求体
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.addAll(content);
        HttpEntity request = new UrlEncodedFormEntity(params, "UTF-8");

        //发送请求
        httppost.setEntity(request);
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);

        //读取响应
        HttpEntity entity = httpResponse.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "UTF-8");
        }

        return result;
    }
}
