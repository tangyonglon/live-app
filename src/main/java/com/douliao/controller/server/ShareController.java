package com.douliao.controller.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.ShareInfoParam;
import com.douliao.result.ResultView;
import com.douliao.service.ShareService;

@RestController
public class ShareController {
	@Autowired
	private ShareService shareService;

	//每日第一次分享赠送5金币
	@RequestMapping(value="/share/shareInfo",method=RequestMethod.POST)
	public ResultView<String> shareInfo(ShareInfoParam shareInfoParam) {
		return shareService.shareInfo(shareInfoParam);
	}
	
	
}
