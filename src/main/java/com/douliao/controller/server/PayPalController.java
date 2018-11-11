package com.douliao.controller.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.childThread.AddUserPackage;
import com.douliao.controller.server.model.CreateOrderParam;
import com.douliao.controller.server.model.PayHistoryParam;
import com.douliao.controller.server.paypal.PaypalPaymentIntent;
import com.douliao.controller.server.paypal.PaypalPaymentMethod;
import com.douliao.controller.server.paypal.URLUtils;
import com.douliao.model.CreateOrderModel;
import com.douliao.model.PayHistoryModel;
import com.douliao.result.ResultView;
import com.douliao.service.PaypalService;
import com.douliao.util.CreateOrder;
import com.douliao.util.Log4jUtil;
import com.douliao.util.ReadResourceConfigUtils;
import com.douliao.util.TimeFormat;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class PayPalController {
	public static final String PAYPAL_SUCCESS_URL = "pay/success";
    public static final String PAYPAL_CANCEL_URL = "pay/cancel";
    
    
//    @Value("${paypal.token_url}")
//    private String TOKEN_URL;
//    
//    @Value("${paypal.payment_detail}")
//    private String PAYMENT_DETAIL;
//    
//    @Value("${paypal.appClientId}")
//    private String clientId;
//    
//    @Value("${paypal.appSecret}")
//    private String secret;
    
    private static final String TOKEN_URL = new ReadResourceConfigUtils().getRedisConfig("/config/paypal.properties","TOKEN_URL");
    private static final String PAYMENT_DETAIL = new ReadResourceConfigUtils().getRedisConfig("/config/paypal.properties","PAYMENT_DETAIL");
    private static final String appclientId =  new ReadResourceConfigUtils().getRedisConfig("/config/paypal.properties","appclientId");
    private static final String appsecret = new ReadResourceConfigUtils().getRedisConfig("/config/paypal.properties","appsecret");
    
    @Autowired
    private PaypalService paypalService;
	
    /**
     * 创建订单接口
     * @param createOrderParam
     */
    @RequestMapping(value="/paypal/createOrder",method=RequestMethod.POST)
    public ResultView<CreateOrderParam> createOrder(CreateOrderParam createOrderParam) {
    	ResultView<CreateOrderParam> resultView=new ResultView<CreateOrderParam>();
    	CreateOrderModel createOrder=paypalService.selGoods_price(createOrderParam);
    	createOrderParam.setOrder_id(CreateOrder.getOrderIdByTime22());
    	createOrderParam.setCreate_time(TimeFormat.getSimple());
    	createOrderParam.setStatus(1);
    	createOrderParam.setGoods_price(createOrder.getGoods_price());
    	createOrderParam.setCurrency_code(createOrder.getCurrency_code());
    	int result=paypalService.insOrder(createOrderParam);
    	if(result>0) {
    		resultView.setCode(1000);
    		resultView.setMessage("成功");
    		resultView.setData(createOrderParam);
    	}else {
    		resultView.setCode(2024);
    		resultView.setMessage("创建订单失败");
    	}
    	Log4jUtil.info(resultView.toString());
    	return resultView;
    }
    
    
    /**
     * 创建paypal支付接口
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value="/paypal/pay",method=RequestMethod.POST)
	public String createPayment(HttpServletRequest request,HttpServletResponse response) {
		String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
        Log4jUtil.info("回调地址successUrl="+successUrl);
        Double total=Double.valueOf(request.getParameter("total"));
        String currency=request.getParameter("currency");
        String order_id=request.getParameter("order_id");
        		
        try {
            Payment payment = paypalService.createPayment(
            		total, 
            		currency, 
                    PaypalPaymentMethod.paypal, 
                    PaypalPaymentIntent.sale,
                    order_id, 
                    cancelUrl, 
                    successUrl);
            for(Links links : payment.getLinks()){
            	Log4jUtil.info("返回结果："+links.getRel());
                if(links.getRel().equals("approval_url")){
//                    return "redirect:" + links.getHref();		//唤起Paypal支付页面
                    return "{\"payUrl\":\"" + links.getHref()+"\"}";
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "";			//唤起失败
	}
	
	/**
	 * 支付失败回调接口
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = PAYPAL_CANCEL_URL)
    public String cancelPay(){
        return "cancel";
    }
	
	
	@RequestMapping(value="/paypal/callback",method=RequestMethod.POST)
	public void Callback(HttpServletRequest request) {
		Log4jUtil.info("paypal回调接口----------");
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
			System.out.println(paraName+": "+request.getParameter(paraName));  
		}
		Log4jUtil.info(request.getParameter("paymentId"));
		Log4jUtil.info(request.getParameter("payerId"));
	}
	
	
	/**
	 * 执行支付接口
	 * @param paymentId
	 * @param payerId
	 * @return
	 */
    @RequestMapping(method = RequestMethod.POST, value = PAYPAL_SUCCESS_URL)
    public ResultView<String> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId){
    	ResultView<String> resultView=new ResultView<String>();
    	System.out.println("paymentId========"+paymentId);
    	System.out.println("PayerID========"+payerId);
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment!=null) {
            	List<Transaction> transactions=payment.getTransactions();
                
                String order_id=transactions.get(0).getDescription();
                System.out.println("集合大小"+transactions.size());
                System.out.println("描述里面放订单号："+transactions.get(0).getDescription());
                System.out.println(payment.getState());
                if(order_id!=null) {
                	Map<String, Object> map=new HashMap<String,Object>();
                    map.put("order_id", order_id);
                    map.put("paymentId", paymentId);
                    map.put("payerId", payerId);
                    map.put("end_time", TimeFormat.getSimple());
                    if(payment.getState().equals("approved")){
                    	//表示支付成功
                    	map.put("status", 2);
                    }else {
                    	map.put("status", 3);
                    }
                    int result=paypalService.updateOrder(map);
                    //创建子线程去增加用户的金币余额
                    AddUserPackage.AddUserGold(order_id);
                    
                    if(result>0) {
                    	resultView.setCode(1000);
                    	resultView.setMessage("成功");
                    }else {
                    	resultView.setCode(1002);
                    	resultView.setMessage("服务器繁忙，请稍后重试");
                    }
                }else {
                	resultView.setCode(1002);
                	resultView.setMessage("服务器繁忙，请稍后重试");
                }
            }
            
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            resultView.setCode(1002);
        	resultView.setMessage("服务器繁忙，请稍后重试");
        }
        Log4jUtil.info(resultView.toString());
        return resultView;
    }
    
    /**
     * 交易历史记录
     * @param payHistoryParam
     * @return
     */
    @RequestMapping(value="/paypal/payHistory",method=RequestMethod.POST)
    public ResultView<List<PayHistoryModel>> payHistory(PayHistoryParam payHistoryParam) {
    	ResultView<List<PayHistoryModel>> resultView=paypalService.payHistory(payHistoryParam);
    	Log4jUtil.info(resultView.toString());
    	return resultView;
    }
    
    /**
     * 获取身份令牌
     * @return
     */
    @RequestMapping(value="/paypal/getToken",method=RequestMethod.POST)
    public String getToken() {
    	return getAccessToken();
    }
    
    
    /**
     * 获取支付订单详情
     * @param paymentId
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/paypal/paymentDetails",method=RequestMethod.POST)
    public ResultView<String> PaymentDetails(String paymentId) throws Exception {
    	ResultView<String> resultView=new ResultView<String>();
    	PayPalController pay=new PayPalController();
    	Map<String, Object> map=pay.verifyPayment(paymentId);
    	String order_id=String.valueOf(map.get("order_id"));
    	if(order_id!=null) {
    		 int result=paypalService.updateOrder(map);
             //创建子线程去增加用户的金币余额
             AddUserPackage.AddUserGold(order_id);
             
             if(result>0) {
             	resultView.setCode(1000);
             	resultView.setMessage("成功");
             }else {
             	resultView.setCode(1002);
             	resultView.setMessage("服务器繁忙，请稍后重试");
             }
    	}else {
    		resultView.setCode(2031);
    		resultView.setMessage("paypal支付该订单失败");
    	}
    	Log4jUtil.info(resultView.toString());
    	return resultView;
    }
    
    /**
     * 获取token
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @return
     */
    private String getAccessToken(){
        try{
            URL url = new URL(TOKEN_URL);
            String authorization = appclientId+":"+appsecret;
            authorization = Base64.encodeBase64String(authorization.getBytes());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// 提交模式
            //设置请求头header
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Language", "en_US");
            conn.setRequestProperty("Authorization", "Basic "+authorization);
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            conn.setDoOutput(true);// 是否输入参数
            String params = "grant_type=client_credentials";
            conn.getOutputStream().write(params.getBytes());// 输入参数

            InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            StringBuilder result = new StringBuilder();
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                result.append(lineTxt);
            }
            reader.close();
            String accessTokey = JSONObject.fromObject(result.toString()).optString("access_token");
            Log4jUtil.info("获取访问令牌getAccessToken:"+accessTokey);
            return accessTokey;
        }catch(Exception err){
            err.printStackTrace();
        }
        return null;
    }
    /**
     * 获取支付详情
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @param paymentId 支付ID，来自于用户客户端
     * @return
     */
    public String getPaymentDetails(String paymentId){
        try{
            URL url = new URL(PAYMENT_DETAIL+paymentId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");// 提交模式
            //设置请求头header
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+getAccessToken());
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            StringBuilder result = new StringBuilder();
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                result.append(lineTxt);
            }
            reader.close();
            return result.toString();
        }catch(Exception err){
            err.printStackTrace();
        }
        return null;
    }
    /**
     * 获取支付详情
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @param paymentId 支付ID，来自于用户客户端
     * @return
     */
    public Map<String, Object> verifyPayment(String paymentId) throws Exception {
        String str = getPaymentDetails(paymentId);
        Log4jUtil.info("返回结果："+str);
        JSONObject detail = JSONObject.fromObject(str);
        Map<String, Object> map=new HashMap<String,Object>();
        //校验订单是否完成
        if("approved".equals(detail.optString("state"))){
        	//订单完成
        	//表示支付成功
        	map.put("status", 2);
            
            map.put("paymentId", detail.optString("id"));
            map.put("end_time", TimeFormat.getSimple());
            	
        	
            JSONObject transactions = detail.optJSONArray("transactions").optJSONObject(0);
            JSONObject amount = transactions.optJSONObject("amount");
            JSONObject payer=detail.optJSONObject("payer");
            JSONObject payer_info=payer.optJSONObject("payer_info");
            String payerId=payer_info.optString("payer_id");
            Log4jUtil.info("payer_id="+payerId);
            map.put("payerId", payerId);
            
            
            String order_id=transactions.optString("description");
            Log4jUtil.info("order_id="+order_id);
            map.put("order_id", order_id);
            
            return map;
        }else {
        	map.put("end_time", TimeFormat.getSimple());
        	map.put("status", 3);
        	return map;
        }
        
    }

//    public static void main(String[] args) {
//        PayPalVerifyPayment payment = new PayPalVerifyPayment();
//        boolean success = false;
//        try {
//            success = payment.verifyPayment("PAY-9MTXXXXXXXXXXXXXXXXXMI");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(success ? "支付完成" : "支付校验失败");
//    }
    
    
}
