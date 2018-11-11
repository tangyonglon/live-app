package com.douliao.service;

import com.douliao.controller.server.model.ShareInfoParam;
import com.douliao.result.ResultView;

public interface ShareService {
	ResultView<String> shareInfo(ShareInfoParam shareInfoParam);
	
}
