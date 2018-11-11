package com.douliao.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.AllGoodsParam;
import com.douliao.model.database.Goods;
import com.douliao.result.ResultView;
import com.douliao.service.GoodsService;
import com.douliao.util.Log4jUtil;

@RestController
public class GoodsController {
	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 获取这个区域的所有商品
	 * @param allGoodsParam
	 * @return
	 */
	@RequestMapping(value="/goods/selAllGoods",method=RequestMethod.POST)
	public ResultView<List<Goods>> selAllGoods(AllGoodsParam allGoodsParam) {
		ResultView<List<Goods>> resultView=goodsService.selAllGoods(allGoodsParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
}
