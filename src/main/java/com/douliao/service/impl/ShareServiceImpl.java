package com.douliao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.douliao.controller.server.model.ShareInfoParam;
import com.douliao.mapper.ShareMapper;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.Share_info;
import com.douliao.result.ResultView;
import com.douliao.service.ShareService;
import com.douliao.util.Log4jUtil;
import com.douliao.util.TimeFormat;

@Service
public class ShareServiceImpl implements ShareService{
	@Autowired
	private ShareMapper shareMapper;
	
	@Transactional
	@Override
	public ResultView<String> shareInfo(ShareInfoParam shareInfoParam) {
		ResultView<String> resultView=new ResultView<String>();
		try {
			Share_info share_info=shareMapper.selByUserId(shareInfoParam);
			Live_app_user_info live_app_user_info=shareMapper.selUserInfo(shareInfoParam);
			String time=TimeFormat.getDate();
			if(share_info!=null) {
				//存在则判断时期是否是今天  1.今天的日期则直接返回已送过 2.不是今天的日期则更新时间 在赠送金币 累计次数
				if(time.equals(share_info.getToday_time())) {
					resultView.setCode(1011);
					resultView.setMessage("今日已赠送");
					return resultView;
				}
				share_info.setToday_time(time);
				share_info.setCount(share_info.getCount()+1);
				live_app_user_info.setUser_package(live_app_user_info.getUser_package()+5.00);
				shareMapper.updateUserInfo(live_app_user_info);
				shareMapper.updateShareInfo(share_info);
			}else {
				Log4jUtil.info("---------"+shareInfoParam.getUser_id()+"==="+shareInfoParam.getShare_way_id());
				Share_info share_info2=new Share_info();
				//不存在该数据 则添加并且赠送金币 累计次数
				share_info2.setCount(1);
				share_info2.setUser_id(shareInfoParam.getUser_id());
				share_info2.setVersion(1);
				share_info2.setShare_way_id(shareInfoParam.getShare_way_id());
				share_info2.setToday_time(time);
				share_info2.setStatus(1);
				Log4jUtil.info("---------"+shareInfoParam.getUser_id()+"==="+shareInfoParam.getShare_way_id());
				shareMapper.updateUserInfo(live_app_user_info);
				shareMapper.insShareInfo(share_info2);
			}
			resultView.setCode(1000);
			resultView.setMessage("成功");
		} catch (Exception e) {
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultView.setCode(1002);
			resultView.setMessage("服务器繁忙，请稍后再重试");
		}
		
		return resultView;
	}
	
}
