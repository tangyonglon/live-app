package com.douliao.service;

import com.douliao.controller.server.model.SearchUserInfoParam;
import com.douliao.model.response.PersonInfo;
import com.douliao.result.ResultView;

public interface SearchService {
	ResultView<PersonInfo> searchUserInfo(SearchUserInfoParam searchUserInfoParam);
}
