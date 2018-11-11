package com.douliao.controller.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.douliao.result.ResultView;
import com.douliao.service.CheckTokenService;
import com.douliao.service.UploadService;
import com.douliao.util.ImgUpload;
import com.douliao.util.Log4jUtil;

@RestController
public class UploadImgController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private UploadService uploadService;
	@Autowired
	private CheckTokenService checkTokenService;

	@Value("${web.imagesPath}")
	private String webUploadPath;
	@Value("${web.file}")
	private String filePath;
	@Value("${web.showFile}")
	private String showFilePath;
	
	/**
	 * 头像上传
	 * @param file
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/img/uploadHead",method=RequestMethod.POST)
	public ResultView<String> fileUpload(@RequestParam("file") MultipartFile file, @RequestParam("userId") Integer userId,@RequestParam("token") String token,@RequestParam("mac") String mac,@RequestParam("loginMode") int loginMode) {
		ResultView<String> resultView = new ResultView<String>();
//		ImgUpload imgUpload=new ImgUpload();
		//上传图片 上传图片路径则为 file\\userId\\imges
		String imgPath=filePath+File.separator+userId+File.separator+"Headimges";
		String showImg=showFilePath+File.separator+userId+File.separator+"Headimges";
		//图片上传
		showImg=ImgUpload.uploadImg(file, userId, imgPath, showImg);
//		String filepath=imgUpload.uploadImg(file, userId, webUploadPath);
		if(showImg!=null) {
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("filepath", showImg);
			int result=uploadService.updateHead(map);
			if(result>0) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
				resultView.setData(showImg);
				Log4jUtil.info(resultView.toString());
				return resultView;
			}
		}
		resultView.setCode(2020);
		resultView.setMessage("上传头像失败");
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
}
