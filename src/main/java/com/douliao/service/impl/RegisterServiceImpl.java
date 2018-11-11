package com.douliao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.RegisterParam;
import com.douliao.controller.server.model.UpdatePasswordParam;
import com.douliao.mapper.RegisterMapper;
import com.douliao.model.CountryInfo;
import com.douliao.model.database.Country;
import com.douliao.result.ResultView;
import com.douliao.service.RegisterService;
import com.douliao.util.CreateOrder;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private RegisterMapper registerMapper;
	
	
	@Override
	public ResultView<List<CountryInfo>> findAllCountry(int language_id) {
		ResultView<List<CountryInfo>> resultView=new ResultView<List<CountryInfo>>();
		List<CountryInfo> list=registerMapper.findAllCountry(language_id);
		if(list.size()>0) {
			resultView.setCode(1000);
			resultView.setMessage("获取成功");
			resultView.setData(list);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

	@Override
	public ResultView<RegisterParam> insRegister(RegisterParam registerParam,ResultView<RegisterParam> resultView) {
//		LiveAppUser liveAppUser=new LiveAppUser();
		registerMapper.insRegister(registerParam);
		if(registerParam.getId()>0) {
			int resultset=registerMapper.insUserInfo(registerParam);
			if(resultset>0) {
				resultView.setCode(1000);
				resultView.setMessage("注册成功");
//				liveAppUser.setId(registerParam.getId());
				resultView.setData(registerParam);
			}else {
				resultView.setCode(2005);
				resultView.setMessage("注册失败,该手机号已注册");
			}
		}else {
			resultView.setCode(2005);
			resultView.setMessage("注册失败,该手机号已注册");
		}
		
		return resultView;
	}

	@Override
	public int updatePassword(UpdatePasswordParam updatePasswordParam) {
		return registerMapper.updatePassword(updatePasswordParam);
	}

}
