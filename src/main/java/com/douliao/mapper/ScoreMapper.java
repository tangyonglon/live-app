package com.douliao.mapper;

import java.util.List;

import com.douliao.controller.server.model.ApplyCashHistoryParam;
import com.douliao.controller.server.model.ApplyCashParam;
import com.douliao.controller.server.model.BindBlankParam;
import com.douliao.controller.server.model.CheckBlankParam;
import com.douliao.controller.server.model.ExtractParam;
import com.douliao.controller.server.model.User_blank_account;
import com.douliao.model.ExtractModel;
import com.douliao.model.database.Score_money;
import com.douliao.model.database.User_apply_cash;

public interface ScoreMapper {
	
	ExtractModel selExtractInfo(ExtractParam extractParam);
	
	List<Score_money> selScoreList();
	
	int insBlankAccount(BindBlankParam bindBlankParam);
	
	int applyCash(ApplyCashParam ApplyCashParam);
	
	User_blank_account hasBindBlank(CheckBlankParam checkBlankParam);
	
	User_blank_account selBlankAccount(BindBlankParam bindBlankParam);
	
	int updateBlankAccount(BindBlankParam bindBlankParam);
	
	List<User_apply_cash> applyCashHistory(ApplyCashHistoryParam applyCashHistoryParam);
	
}
