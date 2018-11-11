package com.douliao.controller.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.PersonCenterParam;
import com.douliao.model.response.PersonCenterModel;
import com.douliao.result.ResultView;
import com.douliao.service.PersonCenterService;
import com.douliao.util.Log4jUtil;

@RestController
public class PersonCenterController {
	@Autowired
	private PersonCenterService personCenterService;
	
	/**
	 * 我的（个人中心）
	 * @param personCenterParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/personCenter",method=RequestMethod.POST)
	public ResultView<PersonCenterModel> personCenter(PersonCenterParam personCenterParam) {
		ResultView<PersonCenterModel> resultView=new ResultView<PersonCenterModel>();
		PersonCenterModel personCenterModel=personCenterService.selPersonInfo(personCenterParam.getUserId());
		if(personCenterModel!=null) {
			resultView.setCode(1000);
			resultView.setMessage("获取成功");
			resultView.setData(personCenterModel);
		}else {
			resultView.setCode(2018);
			resultView.setMessage("读取数据失败，请联系开发人员");
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	
}
