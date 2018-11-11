package com.douliao.mapper;

import java.util.List;

import com.douliao.controller.server.model.AllGoodsParam;
import com.douliao.model.database.Goods;

public interface GoodsMapper {
	
	List<Goods> selAllGoods(AllGoodsParam allGoodsParam);
	
}
