package com.douliao.service;

import java.util.List;
import java.util.Map;

import com.douliao.controller.server.model.CreateOrderParam;
import com.douliao.controller.server.model.PayHistoryParam;
import com.douliao.controller.server.paypal.PaypalPaymentIntent;
import com.douliao.controller.server.paypal.PaypalPaymentMethod;
import com.douliao.model.CreateOrderModel;
import com.douliao.model.PayHistoryModel;
import com.douliao.result.ResultView;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {
	
	public Payment createPayment(Double total, 
            String currency, 
            PaypalPaymentMethod method, 
            PaypalPaymentIntent intent, 
            String description, 
            String cancelUrl, 
            String successUrl) throws PayPalRESTException;
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
	
	CreateOrderModel selGoods_price(CreateOrderParam createOrderParam);
	
	int insOrder(CreateOrderParam createOrderParam);
	
	int updateOrder(Map<String, Object> map);
	
	ResultView<List<PayHistoryModel>> payHistory(PayHistoryParam payHistoryParam);
}
