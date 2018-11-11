package com.douliao.util.sendBird;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.douliao.util.Log4jUtil;
import com.douliao.util.ReadResourceConfigUtils;

/**
 * push action
 * @author god
 *
 */
public class PushAction {
	
	public static final String TAG = PushAction.class.getSimpleName();
	
	private static final long serialVersionUID = -4342256852021697950L;
	
	public static void main(String[] args) {
		PushAction pushAction=new PushAction();
		pushAction.createGroupChannel("group_111","group_url_111");
		pushAction.sendGroupMessage("group_url_111");
	}
	
	
	/**
	 * 创建频道
	 * @param name			频道名字
	 * @param channelUrl	频道地址（频道地址不可重复，否则会创建失败）
	 * @return
	 * @throws Exception
	 */
	public String createGroupChannel(String name,String channelUrl) {
        String result = null;
        HttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        URI uri = null;
        
        try {
        	//创建请求地址
            uri = builder.setScheme("https").setHost("api.sendbird.com").setPath("/v3/group_channels").build();
            HttpPost post = new HttpPost(uri);
            
            //设置请求头，注意是addHeader不是setHeader
            post.addHeader("Content-Type", new ReadResourceConfigUtils().getRedisConfig("/config/sendbird.properties", "Content-Type"));
            post.addHeader("Api-Token", new ReadResourceConfigUtils().getRedisConfig("/config/sendbird.properties", "Api-Token"));
            
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("channel_url", channelUrl);
            
            String body = jsonObject.toString();//"{\"name\": \"groupchannel_"+ID+"\",\"channel_url\": \"groupchannel_"+ID+"\"}";

            Log4jUtil.info("createGroupChannel body"+body);
            
            //设置请求体
            post.setEntity(new StringEntity(body));
            
            //获取返回信息
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode()==200) {
            	result=EntityUtils.toString(response.getEntity());
            }
            Log4jUtil.info("创建群返回结果："+result);
        } catch (Exception e) {
        	Log4jUtil.info("createGroupChannel 接口请求失败"+e.getStackTrace());
        }
        
		return result;
	}
	
	/**
	 * 给群push消息
	 * @return
	 * @throws Exception
	 */
	public String sendGroupMessage(String channelUrl) {
        String result = null;
        HttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        URI uri = null;
        try {
        	//创建请求地址
            uri = builder.setScheme("https").setHost("api.sendbird.com").setPath("/v3/group_channels/"+channelUrl+"/messages").build();
            HttpPost post = new HttpPost(uri);
            
            //设置请求头，注意是addHeader不是setHeader
            post.addHeader("Content-Type", new ReadResourceConfigUtils().getRedisConfig("/config/sendbird.properties", "Content-Type"));
            post.addHeader("Api-Token", new ReadResourceConfigUtils().getRedisConfig("/config/sendbird.properties", "Api-Token"));
            
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("message_type", "ADMM");
            jsonObject.put("message", "hello");
            jsonObject.put("custom_type", "entrance_message");
            jsonObject.put("is_silent", "true");
            
            String body = jsonObject.toString();

            Log4jUtil.info("sendGroupMessage body"+body);
            
            //设置请求体
            post.setEntity(new StringEntity(body));
            
            //获取返回信息
            HttpResponse response = client.execute(post);
            
            if(response.getStatusLine().getStatusCode()==200) {
            	result=EntityUtils.toString(response.getEntity());
            }
            
        } catch (Exception e) {
        	Log4jUtil.info("sendGroupMessage 接口请求失败"+e.getStackTrace());
        }
        
        Log4jUtil.info("发送消息返回结果："+result);
		return result;
	}
}
