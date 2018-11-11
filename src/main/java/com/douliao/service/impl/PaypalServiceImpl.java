package com.douliao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.CreateOrderParam;
import com.douliao.controller.server.model.PayHistoryParam;
import com.douliao.controller.server.paypal.PaypalPaymentIntent;
import com.douliao.controller.server.paypal.PaypalPaymentMethod;
import com.douliao.mapper.OrderMapper;
import com.douliao.model.CreateOrderModel;
import com.douliao.model.PayHistoryModel;
import com.douliao.result.ResultView;
import com.douliao.service.PaypalService;
import com.douliao.util.Log4jUtil;
import com.douliao.util.ReadResourceConfigUtils;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalServiceImpl implements PaypalService{
//	@Autowired
//	private APIContext apiContext;
	@Autowired
	private OrderMapper orderMapper;
	
    private static final String clientId = new ReadResourceConfigUtils().getRedisConfig("/config/paypal.properties","client");
    private static final String clientSecret =  new ReadResourceConfigUtils().getRedisConfig("/config/paypal.properties","secret");
    private static final String mode = new ReadResourceConfigUtils().getRedisConfig("/config/paypal.properties","mode");
    
    public Map<String, String> paypalSdkConfig(){
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mode);
        return sdkConfig;
    }

    public OAuthTokenCredential authTokenCredential(){
    	Log4jUtil.info("获取参数clientId="+clientId+";clientSecret="+clientSecret+";mode="+mode);
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    public APIContext apiContext() throws PayPalRESTException{
        APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig());
        return apiContext;
    }
	
	
	/**
	 * 创建订单
	 * @param total			支付总金额
	 * @param currency		货币种类
	 * @param method		支付方式1.信用卡  2.paypal支付
	 * @param intent		支付时间 1.立即支付 2.授权后支付 3.创建订单
	 * @param description	描述
	 * @param cancelUrl		支付失败回调地址
	 * @param successUrl	支付成功回调地址
	 * @return
	 * @throws PayPalRESTException
	 */
	@Override
	public Payment createPayment(Double total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent,
			String description, String cancelUrl, String successUrl) throws PayPalRESTException {
		Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
      

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        
        //配置apiContext对象
        APIContext apiContext=new PaypalServiceImpl().apiContext();

        return payment.create(apiContext);
	}
	
	 public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        //配置apiContext对象
        APIContext apiContext=new PaypalServiceImpl().apiContext();
        
        return payment.execute(apiContext, paymentExecute);
	}

	@Override
	public int insOrder(CreateOrderParam createOrderParam) {
		return orderMapper.insOrder(createOrderParam);
	}

	@Override
	public int updateOrder(Map<String, Object> map) {
		return orderMapper.updateOrder(map);
	}

	@Override
	public CreateOrderModel selGoods_price(CreateOrderParam createOrderParam) {
		return orderMapper.selGoods_price(createOrderParam);
	}

	@Override
	public ResultView<List<PayHistoryModel>> payHistory(PayHistoryParam payHistoryParam) {
		ResultView<List<PayHistoryModel>> resultView=new ResultView<List<PayHistoryModel>>();
		int page=payHistoryParam.getPage()>0?payHistoryParam.getPage():1;
		int number=payHistoryParam.getNumber()>0?payHistoryParam.getNumber():5;
		int start=(page-1)*number;
		payHistoryParam.setStart(start);
		List<PayHistoryModel> list=orderMapper.payHistory(payHistoryParam);
		if(list.size()>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(list);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}
	
}
