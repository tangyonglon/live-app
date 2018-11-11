package com.douliao.controller.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.InputSource;

import com.douliao.controller.server.childThread.AddUserPackage;
import com.douliao.controller.server.codapay.CodaPayParams;
import com.douliao.controller.server.codapay.Entry;
import com.douliao.controller.server.codapay.ItemInfo;
import com.douliao.controller.server.model.OrderInfo;
import com.douliao.result.ResultView;
import com.douliao.service.CodaPayService;
import com.douliao.util.CreateOrder;
import com.douliao.util.Log4jUtil;
import com.douliao.util.NetUtil;
import com.douliao.util.ReadResourceConfigUtils;
import com.douliao.util.TimeFormat;

import net.sf.json.JSONObject;

@RestController
public class CodaPayController {
	
	@Autowired
	private CodaPayService codaPayService;
	
	/**
	 * 创建订单接口
	 * @param orderInfo
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@RequestMapping(value="/coda/mycodapay",method=RequestMethod.POST)
	public ResultView<Map<String, Object>> codepay(OrderInfo orderInfo,HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException {
		ResultView<Map<String, Object>> resultView=new ResultView<Map<String, Object>>();
		orderInfo.setCreate_time(TimeFormat.getSimple());
		orderInfo.setStatus(1);
		String orderId = CreateOrder.getOrderIdByTime22();
		orderInfo.setOrder_id(orderId);
		codaPayService.insOrder(orderInfo);
		if(orderInfo.getId()>0) {
			Map<String, Object> map=initCodaPay(request,orderId);
			Log4jUtil.info("codapay创建支付订单返回结果："+map.toString());
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(map);
		}else {
			resultView.setCode(2024);
			resultView.setMessage("创建订单失败");
		}
		
		return resultView;
	}
	
	@RequestMapping(value="/codapay/callBack")
	public String callBack(HttpServletRequest request,HttpServletResponse response) {
		Log4jUtil.info("codapay回调接口");
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){
			String paraName=(String)enu.nextElement();  
			Log4jUtil.info(paraName+": "+request.getParameter(paraName));  
		}
		//ip过滤
		String ip=NetUtil.getRemortIP(request);
		Log4jUtil.info("ip："+ip);
		String ResultCode=request.getParameter("ResultCode");
		Map<String, Object> map=new HashMap<String,Object>();
		if("54.251.135.133".equals(ip)) {
			map.put("TxnId", request.getParameter("TxnId"));
			map.put("OrderId", request.getParameter("OrderId"));
			map.put("ResultCode", ResultCode);
			map.put("TotalPrice", request.getParameter("TotalPrice"));
			map.put("PaymentType", request.getParameter("PaymentType"));
			map.put("Checksum", request.getParameter("Checksum"));
			if("0".equals(ResultCode)) {
				map.put("status", 2);
			}else {
				map.put("status", 3);
			}
		}
		
		Log4jUtil.info("接收到的数据："+map.toString());
		
		int result=codaPayService.updateOrder(map);
		//创建子线程去增加用户的金币余额
        AddUserPackage.AddUserGold(request.getParameter("OrderId"));
        
		if(result>0) {
			return "ResultCode=0";
		}else {
			return "ResultCode=1";
		}
	}
	
	
	
	public Map<String, Object> initCodaPay(HttpServletRequest request,String orderId) throws IOException, DocumentException {
		Map<String, Object> orderMap=new HashMap<String,Object>();
		ReadResourceConfigUtils readResourceConfigUtils=new ReadResourceConfigUtils();
		CodaPayParams codaPayParams=new CodaPayParams();
		codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "airtime.apikey"));
		codaPayParams.setCountry(Short.parseShort(request.getParameter("country")));
		codaPayParams.setCurrency(Short.parseShort(request.getParameter("currency")));
		String country=request.getParameter("country");
		if("360".equals(country)){
			//Indonesia 印度尼西亚 360
			codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "Indonesia.apikey"));
		}
		if("458".equals(country)){
			//Malaysia 马来西亚 458
			codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "Malaysia.apikey"));
		}
		if("702".equals(country)){
			//Singapore 新加坡 702
			codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "Singapore.apikey"));
		}
		if("608".equals(country)){
			//Philippines 菲律宾 608
			codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "Philippines.apikey"));
		}
		if("704".equals(country)){
			//Vietnam 越南 704
			codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "Vietnam.apikey"));
		}
		if("104".equals(country)){
			//Myanmar 缅甸 104
			codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "Myanmar.apikey"));
		}
		if("116".equals(country)){
			//Cambodia 柬埔寨 116
			codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "Cambodia.apikey"));
		}
		if("764".equals(country)){
			//Thailand 泰国 764
			codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "Thailand.apikey"));
		}
		if("356".equals(country)){
			//India 印度 356 
			codaPayParams.setApiKey(readResourceConfigUtils.getRedisConfig("/coda.properties", "India.apikey"));
		}
		ArrayList<ItemInfo> itemList = new ArrayList<ItemInfo>();
		ItemInfo item = new ItemInfo();
		item.setName(request.getParameter("title"));
		item.setCode(request.getParameter("goods_id"));
		item.setPrice(Double.parseDouble(request.getParameter("price")));
		item.setType(Short.parseShort("1"));
		itemList.add(item);
		codaPayParams.setItems(itemList);
		orderMap.put("orderId", orderId);
		codaPayParams.setOrderId(orderId);
		codaPayParams.setPayType(Short.parseShort("1"));
		HashMap<String, String> profile = new HashMap<String, String>();
		Entry entry=new Entry();
		entry.setKey("user_id");
		entry.setValue("1");
		profile.put("entry", entry.toString());
		codaPayParams.setProfile(profile);
		//对象转换成JSON
		JSONObject json = JSONObject.fromObject(codaPayParams);
		Map<String, String> map=new HashMap<String,String>();
		map.put("initRequest", json.toString());
		System.out.println("请求参数："+map.toString());
		String result=sendPost(map.toString(),readResourceConfigUtils.getRedisConfig("/coda.properties", "airtime.rest.url"));
		System.out.println("返回结果："+result);
		//xml解析
		orderMap=analysisXML(result,orderMap);
		System.out.println("结果："+orderMap.toString());
		return orderMap;
	}
	
	
	
	/**
	 * 发送符合codapay的post请求
	 * @param params
	 * @param requestUrl
	 * @return
	 * @throws IOException
	 */
	public static String sendPost(String params, String requestUrl) throws IOException {

        byte[] requestBytes = params.getBytes("utf-8"); // 将参数转为二进制流
        HttpClient httpClient = new HttpClient();// 客户端实例化
        PostMethod postMethod = new PostMethod(requestUrl);
        // 设置请求头  Content-Type
        postMethod.setRequestHeader("Content-Type", "application/json");
        postMethod.setRequestHeader("Content-accept", "application/json");
        InputStream inputStream = new ByteArrayInputStream(requestBytes, 0,
                requestBytes.length);
        RequestEntity requestEntity = new InputStreamRequestEntity(inputStream,
                requestBytes.length, "application/json; charset=utf-8"); // 请求体
        postMethod.setRequestEntity(requestEntity);
        httpClient.executeMethod(postMethod);// 执行请求
        InputStream soapResponseStream = postMethod.getResponseBodyAsStream();// 获取返回的流
        byte[] datas = null;
        try {
            datas = readInputStream(soapResponseStream);// 从输入流中读取数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = new String(datas, "UTF-8");// 将二进制流转为String
        // 打印返回结果
        // System.out.println(result);

        return result;

    }

	 /**
     * 从输入流中读取数据
     * 
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    
    public Map<String, Object> analysisXML(String xml,Map<String, Object> map) throws DocumentException {
		InputSource in = new InputSource(new StringReader(xml));
		in.setEncoding("UTF-8");
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		List<Element> elements = root.elements();
		for(Iterator<Element> it = elements.iterator();it.hasNext();){
			 Element element = it.next();
			 System.out.println(element.getName()+" : "+element.getTextTrim());
			 map.put(element.getName(), element.getTextTrim());
		}
		System.out.println(map.toString());
		return map;
    }
    
	
    public static void main(String[] args) throws Exception {
    	  String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><initResult><resultCode>0</resultCode><txnId>5390787901485634750</txnId></initResult>";
    	  InputSource in = new InputSource(new StringReader(xml));
    	  in.setEncoding("UTF-8");
    	  SAXReader reader = new SAXReader();
    	  Document document = reader.read(in);
    	  Element root = document.getRootElement();
    	  List<Element> elements = root.elements();
    	  for(Iterator<Element> it = elements.iterator();it.hasNext();){
    	   Element element = it.next();
    	   System.out.println(element.getName()+" : "+element.getTextTrim());
    	  }

    }

    
    
}
