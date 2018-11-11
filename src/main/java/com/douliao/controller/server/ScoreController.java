package com.douliao.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.ApplyCashHistoryParam;
import com.douliao.controller.server.model.ApplyCashParam;
import com.douliao.controller.server.model.BindBlankParam;
import com.douliao.controller.server.model.CheckBlankParam;
import com.douliao.controller.server.model.ExtractParam;
import com.douliao.controller.server.model.User_blank_account;
import com.douliao.model.ExtractModel;
import com.douliao.model.database.User_apply_cash;
import com.douliao.result.ResultView;
import com.douliao.service.ScoreService;
import com.douliao.util.Log4jUtil;

@RestController
public class ScoreController {
	@Autowired
	private ScoreService scoreService;
	
	
	/**
	 * 积分提现界面接口
	 * @param extractParam
	 * @return
	 */
	@RequestMapping(value="/score/extractScore",method=RequestMethod.POST)
	public ResultView<ExtractModel> extractScore(ExtractParam extractParam) {
		ResultView<ExtractModel> resultView=scoreService.selExtractInfo(extractParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 申请积分提现
	 * @param applyCashParam
	 * @return
	 */
	@RequestMapping(value="/score/applyCash",method=RequestMethod.POST)
	public ResultView<String> applyCash(ApplyCashParam applyCashParam) {
		ResultView<String> resultView=scoreService.applyCash(applyCashParam);
		return resultView;
	}
	
	/**
	 * 判断用户是否已有账号
	 * @param checkBlankParam
	 * @return
	 */
	@RequestMapping(value="/score/hasBindBlank")
	public ResultView<User_blank_account> hasBindBlank(CheckBlankParam checkBlankParam) {
		checkBlankParam.setStatus(1);
		ResultView<User_blank_account> resultView=scoreService.hasBindBlank(checkBlankParam);
		return resultView;
	}
	
	
	/**
	 * 绑定银行卡或paypal账号
	 * @param bindBlankParam
	 * @return
	 */
	@RequestMapping(value="/score/bindBlank",method=RequestMethod.POST)
	public ResultView<String> bindBlank(BindBlankParam bindBlankParam) {
		ResultView<String> resultView=scoreService.bindBlank(bindBlankParam);
		return resultView;
	}
	
	
	/**
	 * 查看积分提现历史记录
	 * @param applyCashHistoryParam
	 * @return
	 */
	@RequestMapping(value="/score/applyCashHistory",method=RequestMethod.POST)
	public ResultView<List<User_apply_cash>> applyCashHistory(ApplyCashHistoryParam applyCashHistoryParam) {
		ResultView<List<User_apply_cash>> resultView=scoreService.applyCashHistory(applyCashHistoryParam);
		return resultView;
	}
	
	
}
