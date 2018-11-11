package com.douliao.controller.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.PraiseParam;
import com.douliao.result.ResultView;
import com.douliao.service.PraiseService;
import com.douliao.util.Log4jUtil;

@RestController
public class PraiseController {
	@Autowired
	private PraiseService praiseService;
	
	//点赞
	@RequestMapping(value="/praise/praiseVideo",method=RequestMethod.POST)
	public ResultView<String> praiseVideo(PraiseParam PraiseParam) {
		//用户点赞 避免同时操作数据库  写入redis 定时去修改数据库
		//对每个视频都维护一个计数器和一个set集合
		ResultView<String> resultView=praiseService.praiseVideo(PraiseParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
}
