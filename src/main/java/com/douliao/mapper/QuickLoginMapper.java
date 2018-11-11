package com.douliao.mapper;

import com.douliao.controller.server.model.BindParam;
import com.douliao.controller.server.model.QuickLoginParam;
import com.douliao.model.database.UserToken;

public interface QuickLoginMapper {
	
	int insRegister(QuickLoginParam quickLoginParam);
	
	int insUserInfo(QuickLoginParam quickLoginParam);
	
	UserToken selIsOnline(QuickLoginParam quickLoginParam);
	
	int updateToken(QuickLoginParam quickLoginParam);
	
	int insToken(QuickLoginParam quickLoginParam);
	
	int updateAccount(BindParam bindParam);
	
}
