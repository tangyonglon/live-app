package com.douliao.service;

import com.douliao.controller.server.model.GateParams;
import com.douliao.model.database.Gate;

public interface GateService {
	Gate selInterface(GateParams gateParams);
	
}
