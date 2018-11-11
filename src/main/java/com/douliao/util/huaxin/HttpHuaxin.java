package com.douliao.util.huaxin;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class HttpHuaxin {
	
	public static void main(String[] args) {
	    int code=(int)((Math.random()*4+1)*1000);
		String text="您的验证码："+code+"【逗聊】";
		System.out.println(text);
//		send("http://sh2.ipyy.com/sms.aspx?action=send","您的验证码：8850【逗聊】");
	}
	
	public static boolean send(String url,String text,String username,String pwd,String phone) {
		HttpClient client=new HttpClient();
		PostMethod post=new PostMethod(url);
		post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		NameValuePair userid=new NameValuePair("userid","");
		NameValuePair account=new NameValuePair("account",username);
		NameValuePair password=new NameValuePair("password",pwd);
		NameValuePair mobile=new NameValuePair("mobile",phone);
		NameValuePair content=new NameValuePair("content",text);
		NameValuePair sendTime=new NameValuePair("sendTime","");
		NameValuePair action=new NameValuePair("action","send");
		NameValuePair extno=new NameValuePair("extno","");
		post.setRequestBody(new NameValuePair[]{userid,account,password,mobile,content,sendTime,extno});
		
//		HttpMethod method=new PostMethod(Url);//带参数的Url
//		method.setRequestHeader("Content-type", "text/xml; charset=utf-8");
//		client.executeMethod(method);
//		String str = method.getResponseBodyAsString();
//		System.out.println("result="+str);
		
		int statu = 0;
		String str = null;
		try {
			statu = client.executeMethod(post);
			str=post.getResponseBodyAsString();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("statu="+statu);
		System.out.println(str);
		
		try {
			//将字符转化为XML
			Document doc=DocumentHelper.parseText(str);
			//获取根节点
			Element rootElt=doc.getRootElement();
			//获取根节点下的子节点的值
			String returnstatus=rootElt.elementText("returnstatus").trim();
			String message=rootElt.elementText("message").trim();
			String remainpoint=rootElt.elementText("remainpoint").trim();
			String taskID=rootElt.elementText("taskID").trim();
			String successCounts=rootElt.elementText("successCounts").trim();
			
			System.out.println("返回状态为："+returnstatus);
			System.out.println("返回信息提示："+message);
			System.out.println("返回余额："+remainpoint);
			System.out.println("返回任务批次："+taskID);
			System.out.println("返回成功条数："+successCounts);
			if("Success".equals(returnstatus)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return false;
	}
}
