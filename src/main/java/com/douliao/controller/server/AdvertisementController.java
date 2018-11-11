package com.douliao.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.model.database.Advertisement;
import com.douliao.result.ResultView;
import com.douliao.service.AdvertisementService;
import com.douliao.util.Log4jUtil;

@RestController
public class AdvertisementController {
	@Autowired
	private AdvertisementService advertisementService;
	
	/**
	 * 获取广告
	 */
	@RequestMapping(value="/getAdvertisement",method=RequestMethod.POST)
	public ResultView<List<Advertisement>> getAdvertisement() {
		Log4jUtil.info(advertisementService.selAdvertisement().toString());
		return advertisementService.selAdvertisement();
	}
	
}
