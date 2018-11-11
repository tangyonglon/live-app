package com.douliao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.AllGoodsParam;
import com.douliao.mapper.GoodsMapper;
import com.douliao.model.database.Goods;
import com.douliao.result.ResultView;
import com.douliao.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public ResultView<List<Goods>> selAllGoods(AllGoodsParam allGoodsParam) {
		ResultView<List<Goods>> resultView=new ResultView<List<Goods>>();
		List<Goods> list=goodsMapper.selAllGoods(allGoodsParam);
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
