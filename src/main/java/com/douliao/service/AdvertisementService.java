package com.douliao.service;

import java.util.List;

import com.douliao.model.database.Advertisement;
import com.douliao.result.ResultView;

public interface AdvertisementService {
	
	ResultView<List<Advertisement>> selAdvertisement();
}
