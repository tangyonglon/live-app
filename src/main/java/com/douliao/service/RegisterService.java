package com.douliao.service;

import java.util.List;

import com.douliao.controller.server.model.RegisterParam;
import com.douliao.controller.server.model.UpdatePasswordParam;
import com.douliao.model.CountryInfo;
import com.douliao.result.ResultView;

public interface RegisterService {
	
	ResultView<List<CountryInfo>> findAllCountry(int language_id);
	
	ResultView<RegisterParam> insRegister(RegisterParam registerParam,ResultView<RegisterParam> resultView);
	
	int updatePassword(UpdatePasswordParam updatePasswordParam);
}
