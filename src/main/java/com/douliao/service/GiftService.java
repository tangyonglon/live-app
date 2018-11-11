package com.douliao.service;

import java.util.List;

import com.douliao.controller.server.model.RewardParam;
import com.douliao.controller.server.model.VideoGiftsParam;
import com.douliao.model.database.Gifts;
import com.douliao.result.ResultView;

public interface GiftService {
	
	ResultView<List<Gifts>> findAllGifts();
	
	double rewareGifts(RewardParam rewardParam);
	
	double rewareVideoGifts(VideoGiftsParam videoGiftsParam);
}
