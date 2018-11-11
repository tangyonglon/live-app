package com.douliao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.mapper.AdvertisementMapper;
import com.douliao.model.database.Advertisement;
import com.douliao.result.ResultView;
import com.douliao.service.AdvertisementService;

@Service
public class AdvertisementServiceImpl implements AdvertisementService{
	@Autowired
	private AdvertisementMapper advertisementMapper;

	@Override
	public ResultView<List<Advertisement>> selAdvertisement() {
		ResultView<List<Advertisement>> resultView=new ResultView<List<Advertisement>>();
		List<Advertisement> list=advertisementMapper.selAdvertisement();
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
