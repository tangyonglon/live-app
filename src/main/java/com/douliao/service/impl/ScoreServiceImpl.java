package com.douliao.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.ApplyCashHistoryParam;
import com.douliao.controller.server.model.ApplyCashParam;
import com.douliao.controller.server.model.BindBlankParam;
import com.douliao.controller.server.model.CheckBlankParam;
import com.douliao.controller.server.model.ExtractParam;
import com.douliao.controller.server.model.User_blank_account;
import com.douliao.mapper.ScoreMapper;
import com.douliao.model.ExtractModel;
import com.douliao.model.database.Score_money;
import com.douliao.model.database.User_apply_cash;
import com.douliao.result.ResultView;
import com.douliao.service.ScoreService;
import com.douliao.util.TimeFormat;

@Service
public class ScoreServiceImpl implements ScoreService{
	@Autowired
	private ScoreMapper scoreMapper;

	@Override
	public ResultView<ExtractModel> selExtractInfo(ExtractParam extractParam) {
		ResultView<ExtractModel> resultView=new ResultView<ExtractModel>();
		ExtractModel extractModel=scoreMapper.selExtractInfo(extractParam);
		List<Score_money> list=scoreMapper.selScoreList();
		if(list.size()>0) {
			extractModel.setList(list);
		}
		if(extractModel!=null) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(extractModel);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

	@Override
	public ResultView<String> bindBlank(BindBlankParam bindBlankParam) {
		ResultView<String> resultView=new ResultView<String>();
		if(StringUtils.isNoneBlank(bindBlankParam.getBlank_account()) && StringUtils.isNoneBlank(String.valueOf(bindBlankParam.getBlank_type())) && StringUtils.isNoneBlank(String.valueOf(bindBlankParam.getCountry_id()))) {
			bindBlankParam.setCreate_time(TimeFormat.getSimple());
			bindBlankParam.setStatus(1);
			User_blank_account user_blank_account=scoreMapper.selBlankAccount(bindBlankParam);
			int result;
			if(user_blank_account!=null) {
				result=scoreMapper.updateBlankAccount(bindBlankParam);
			}else {
				result=scoreMapper.insBlankAccount(bindBlankParam);
			}
			
			if(result>0) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
			}else {
				resultView.setCode(1002);
				resultView.setMessage("服务器繁忙，请稍后重试");
			}
		}else {
			resultView.setCode(2010);
			resultView.setMessage("请检查提交的参数");
		}
		
		return resultView;
	}

	@Override
	public ResultView<String> applyCash(ApplyCashParam applyCashParam) {
		ResultView<String> resultView=new ResultView<String>();
		if(StringUtils.isNoneBlank(String.valueOf(applyCashParam.getUser_id())) && StringUtils.isNoneBlank(applyCashParam.getBlank_account()) && StringUtils.isNoneBlank(String.valueOf(applyCashParam.getBlank_type())) && StringUtils.isNoneBlank(applyCashParam.getTotal_money())) {
			applyCashParam.setCreate_time(TimeFormat.getSimple());
			applyCashParam.setStatus(1);
			int result=scoreMapper.applyCash(applyCashParam);
			if(result>0) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
			}else {
				resultView.setCode(1002);
				resultView.setMessage("服务器繁忙，请稍后重试");
			}
		}else {
			resultView.setCode(2010);
			resultView.setMessage("请检查提交的参数");
		}
		
		return resultView;
	}

	@Override
	public ResultView<User_blank_account> hasBindBlank(CheckBlankParam checkBlankParam) {
		ResultView<User_blank_account> resultView=new ResultView<User_blank_account>();
		User_blank_account user_blank_account=scoreMapper.hasBindBlank(checkBlankParam);
		if(user_blank_account!=null) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(user_blank_account);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

	@Override
	public ResultView<List<User_apply_cash>> applyCashHistory(ApplyCashHistoryParam applyCashHistoryParam) {
		ResultView<List<User_apply_cash>> resultView=new ResultView<List<User_apply_cash>>();
		int page=applyCashHistoryParam.getPage()>0?applyCashHistoryParam.getPage():1;
		int number=applyCashHistoryParam.getNumber()>0?applyCashHistoryParam.getNumber():5;
		int start=(page-1)*number;
		applyCashHistoryParam.setStart(start);
		List<User_apply_cash> list=scoreMapper.applyCashHistory(applyCashHistoryParam);
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
