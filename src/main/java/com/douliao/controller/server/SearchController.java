package com.douliao.controller.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.SearchUserInfoParam;
import com.douliao.model.response.PersonInfo;
import com.douliao.result.ResultView;
import com.douliao.service.SearchService;
import com.douliao.util.Log4jUtil;

@RestController
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	
	/**
	 * 查看别人的详细信息接口
	 * @param searchUserInfoParam
	 * @return
	 */
	@RequestMapping(value="/search/searchUserInfo",method=RequestMethod.POST)
	public ResultView<PersonInfo> searchUserInfo(SearchUserInfoParam searchUserInfoParam) {
		ResultView<PersonInfo> resultView=searchService.searchUserInfo(searchUserInfoParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
		
	}
	
}
