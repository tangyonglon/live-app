package com.douliao.service;

import java.util.List;

import com.douliao.controller.server.model.ApplyCashHistoryParam;
import com.douliao.controller.server.model.ApplyCashParam;
import com.douliao.controller.server.model.BindBlankParam;
import com.douliao.controller.server.model.CheckBlankParam;
import com.douliao.controller.server.model.ExtractParam;
import com.douliao.controller.server.model.User_blank_account;
import com.douliao.model.ExtractModel;
import com.douliao.model.database.User_apply_cash;
import com.douliao.result.ResultView;

public interface ScoreService {
	
	ResultView<ExtractModel> selExtractInfo(ExtractParam extractParam);
	
	ResultView<String> bindBlank(BindBlankParam bindBlankParam);
	
	ResultView<String> applyCash(ApplyCashParam applyCashParam);
	
	ResultView<User_blank_account> hasBindBlank(CheckBlankParam checkBlankParam);
	
	ResultView<List<User_apply_cash>> applyCashHistory(ApplyCashHistoryParam applyCashHistoryParam);
	
}
