package com.douliao.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class TestHttps {
	
	public static String doPost(String url, Map<String, String> map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
//			httpPost.setHeader("Content-Type", "application/json, charset=utf-8");
//			httpPost.setHeader("Api-Token", "15855dba6ce7f95ccc23566fec6bf0e27e54d711");
			httpPost.addHeader("Content-Type", "application/json, charset=utf-8");
			httpPost.addHeader("Api-Token", "15855dba6ce7f95ccc23566fec6bf0e27e54d711");
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator
						.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			System.out.println(list.toString());
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						charset);
				System.out.println(entity.toString());
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	private String url = "https://api.sendbird.com/v3/group_channels";//接口只是把差un过来的参数返回
	private String charset = "utf-8";
//	private TestHttps httpClientUtil = null;
	
	
 
//	public TestHttps() {
//		httpClientUtil = new TestHttps();
//	}
 
	public void test() {
		JSONObject json=new JSONObject();
		json.put("name", "Chat with Lizzy");
		json.put("channel_url", "group_channel_tyl");
		json.put("cover_url", "");
		json.put("custom_type", "personal");
		json.put("data", "");
		json.put("user_ids", "[\"terry5\", \"elizabeth2\"]");
		json.put("operator_ids", "[\"terry5\", \"elizabeth2\"]");
		json.put("is_distinct", "true");
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("name", "Chat with Lizzy");
		createMap.put("channel_url", "group_channel_tyl");
		createMap.put("cover_url", "");
		createMap.put("custom_type", "personal");
		createMap.put("data", "");
		createMap.put("user_ids", "[\"terry5\", \"elizabeth2\"]");
		createMap.put("operator_ids", "[\"terry5\", \"elizabeth2\"]");
		createMap.put("is_distinct", "true");
		createMap.put("data", json.toString());
		String httpOrgCreateTestRtn = doPost(url, createMap,
				charset);
		System.out.println("result:" + httpOrgCreateTestRtn); 
	}
 
	public static void main(String[] args) {
		TestHttps main = new TestHttps();
		main.test();
//		main.getRemoteId();
	}
	
	
	public void getRemoteId(){
		List<String> list1=new ArrayList<String>();
		Boolean b=true;
		list1.add("terry5");
		list1.add("elizabeth2");
		List<String> list2=new ArrayList<String>();
		list2.add("terry5");
		list2.add("elizabeth2");
		JSONObject json=new JSONObject();
		json.put("name", "Chat with Lizzy");
		json.put("channel_url", "group_channel_tyl");
		json.put("cover_url", "https://sendbird.com/main/img/cover/cover_08.jpg");
		json.put("custom_type", "personal");
		json.put("data", "");
		json.put("user_ids", list1);
		json.put("operator_ids", list2);
		json.put("is_distinct", b);
		System.out.println(json.toString());
		String add_url = "https://api.sendbird.com/v3/group_channels";
//        String add_url = "http://test.com:8080/report.jo";
//        String query = " {\"mainUser\":{\"name\":\""+name+"\",\"gender\":\""+gender+"\",\"birthDate\":\""+birthDate+"\",\"birthDateAccurate\":\""+birthDateAccurate+"\",\"addrId\":\""+addrId+"\"},\"productId\":\""+productId+"\"}";
        try {
            URL url = new URL(add_url);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json, charset=utf-8");
            connection.setRequestProperty("Api-Token","15855dba6ce7f95ccc23566fec6bf0e27e54d711");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = new JSONObject();
             
            String token = "d5f224c9f83874da5b5025794c773e8e";
//            obj.put("query", json);
            out.writeBytes(json.toString());
            out.flush();
            out.close();
             
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sbf = new StringBuffer();
             while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    sbf.append(lines);
                }
                System.out.println(sbf);
                reader.close();
                // 断开连接
                connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	
	
	
	
	
	
	
}
