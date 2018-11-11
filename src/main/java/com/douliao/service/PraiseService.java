package com.douliao.service;

import com.douliao.controller.server.model.PraiseParam;
import com.douliao.result.ResultView;

public interface PraiseService {
	
	ResultView<String> praiseVideo(PraiseParam PraiseParam);
}
