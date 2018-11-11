package com.douliao.mapper;

import java.util.List;

import com.douliao.controller.server.model.RegisterParam;
import com.douliao.controller.server.model.UpdatePasswordParam;
import com.douliao.model.CountryInfo;

public interface RegisterMapper {
	
	List<CountryInfo> findAllCountry(int language_id);
	
	void insRegister(RegisterParam registerParam);
	
	int updatePassword(UpdatePasswordParam updatePasswordParam);
	
	int insUserInfo(RegisterParam registerParam);
}
