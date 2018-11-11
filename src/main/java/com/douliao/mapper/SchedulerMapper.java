package com.douliao.mapper;

import java.util.List;
import java.util.Map;

public interface SchedulerMapper {
	
	List<Map<String, Object>> selRoomChat();
	
	int updatePackage(List<Map<String, Object>> list);
	
	int updateScore(List<Map<String, Object>> list);
	
	int updateTotalScore(List<Map<String, Object>> list);
	
	List<Map<String, Object>> selAllRoom();
	
	void insIncome(List<Map<String, Object>> list);
	
}
