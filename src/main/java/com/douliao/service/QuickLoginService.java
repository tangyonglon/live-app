package com.douliao.service;

import com.douliao.controller.server.model.BindParam;
import com.douliao.controller.server.model.QuickLoginParam;
import com.douliao.result.ResultView;

public interface QuickLoginService {
	
	ResultView<QuickLoginParam> QuickLogin(QuickLoginParam quickLoginParam);
	
	ResultView<String> bindAccount(BindParam bindParam);
	
}
