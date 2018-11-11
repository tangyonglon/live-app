package com.douliao.service;

import java.util.List;

import com.douliao.controller.server.model.AllGoodsParam;
import com.douliao.model.database.Goods;
import com.douliao.result.ResultView;

public interface GoodsService {
	
	ResultView<List<Goods>> selAllGoods(AllGoodsParam allGoodsParam);
	
}
