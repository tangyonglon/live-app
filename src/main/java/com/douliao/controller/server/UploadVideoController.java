package com.douliao.controller.server;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.douliao.controller.server.model.AuditParam;
import com.douliao.controller.server.model.DeleteParam;
import com.douliao.controller.server.model.VideoByUserIdParam;
import com.douliao.model.VideoClips;
import com.douliao.model.database.Everyday_live_list;
import com.douliao.model.database.VideoType;
import com.douliao.model.database.Video_List;
import com.douliao.result.ResultView;
import com.douliao.service.NewLiveService;
import com.douliao.service.UploadService;
import com.douliao.util.FileUploadTool;
import com.douliao.util.ImgUpload;
import com.douliao.util.Log4jUtil;
import com.douliao.util.TimeFormat;

@RestController
public class UploadVideoController {
	@Value("${web.videoPath}")
	private String videoPath;
	@Value("${web.everydayvideoPath}")
	private String everydayvideoPath;
//	@Value("${web.ffmpegPath}")
//	private String ffmpegPath;
	@Value("${web.playUrl}")
	private String playUrl;
	@Autowired
	private UploadService uploadService;
	@Autowired
	private NewLiveService newLiveService; 
	
	@Value("${web.file}")
	private String filePath;
	@Value("${web.showFile}")
	private String showFilePath;
	//视频上传
	//1.每个用户都创建一个视频文件夹存放视频 然后分两个子文件夹 1.1每日打招呼视频文件夹  1.2小视频文件夹  1.3 用户图片文件夹
	//1.1 所有用户第一次成为主播时认识上传一张图片和打招呼视频 先使用后审核 后面修改打招呼视频时 先审核后上传
	//视频存放地址 tomcat目录下面的file文件夹 然后分多个子文件夹 以用户ID作为文件夹名字
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public ResultView<Video_List> upload(@RequestParam(value = "file", required = false) MultipartFile[] multipartFile,@RequestParam("userId") Integer userId,@RequestParam("type") Integer type,@RequestParam("country_id") Integer country_id,HttpServletRequest request,HttpServletResponse response) {
		response.reset();
		ResultView<Video_List> resultView=new ResultView<Video_List>();
		if(userId==null || type==null  || multipartFile.length==0 || country_id==null) {
			resultView.setCode(2010);
  		  	resultView.setMessage("请检查提交的参数");
  		  	return resultView; 
		}
		String title=request.getParameter("title");
		String description=request.getParameter("description");
		FileUploadTool fileUploadTool = new FileUploadTool();
		String imgPath=null;			//上传图片存放的路径
		String showImg=null;			//图片显示地址
		String videoPath=null;		//上传视频存放的路径
		String showVideo=null;		//视频播放地址
		Video_List video_List=new Video_List();
		
		for(MultipartFile file:multipartFile) {
			if(!file.isEmpty()) {
				if(file.getContentType().contains("image")) {
					//上传图片 上传图片路径则为 file\\userId\\imges
					imgPath=filePath+File.separator+userId+File.separator+"imges";
					showImg=showFilePath+File.separator+userId+File.separator+"imges";
					//图片上传
					showImg=ImgUpload.uploadImg(file, userId, imgPath, showImg);
					if(showImg==null || "".equals(showImg)) {
						resultView.setCode(2020);
			    		resultView.setMessage("上传头像失败");
			    		return resultView;
					}
					continue;
				}else {
					//否则为上传视频
					//类型 1.上传15秒视频     2.上传每日打招呼小视频
					if(type==1) {
						videoPath=filePath+File.separator+userId+File.separator+"videos";
						showVideo=showFilePath+File.separator+userId+File.separator+"videos";
					}else {
						videoPath=filePath+File.separator+userId+File.separator+"everydayvideos";
						showVideo=showFilePath+File.separator+userId+File.separator+"everydayvideos";
					}
					//视频上传
					video_List = fileUploadTool.createFile(file, request,videoPath,showVideo);
					if(video_List.getVideo_url()==null || "".equals(video_List.getVideo_url())) {
						resultView.setCode(2021);
			    		resultView.setMessage("上传视频失败");
			    		return resultView;
					}
					continue;
				}
			}
		}
		
		video_List.setImg_url(showImg);
		video_List.setUser_id(userId);
		video_List.setCountry_id(country_id);
		video_List.setStatus(1);
		video_List.setCreate_time(TimeFormat.getSimple());
		video_List.setVideo_title(title);
		video_List.setVideo_description(description);
	    try {
	    	  int result = 0;
	    	  if(type==1) {
	    		  //上传视频
	    		  result=uploadService.insVideo(video_List);
	    	  }else if(type==2) {
	    		  result=newLiveService.uploadLiveVideo(video_List);
	    		  
	    		  
//	    		  //上传每日小视频
//	    		  Everyday_live_list everyday_live_list=uploadService.isHasVideo(video_List);
//	    		  if(everyday_live_list!=null) {
//	    			  //更新每日小视频
//	    			  //result=uploadService.updateEverydayVideo(video_List);
//	    			  //删除原来的地址
//	    			  //video_List.setCount(2);
//	    			  result=uploadService.updateEverydayVideo(video_List);
//	    			  resultView.setCode(1006);
//	    			  resultView.setMessage("你提交的视频还在审核中");
//	    		  }else {
//	    			  double profit=uploadService.selProfit(video_List.getUser_id());
//	    			  video_List.setProfit(profit);
//	    			  result=uploadService.insEverydayVideo(video_List);
//	    			  User_tv_info user_tv_info=uploadService.hasTVInfo(video_List);
//	    			  if(user_tv_info==null) {
//	    				//生成主播信息
//		    			uploadService.insUserTVInfo(video_List);
//		    			uploadService.updateLiveAuth(video_List);
//	    			  }
//	    		  }
	    		  
	    	  }
	    	  
	    	  if(result>0) {
	    		  resultView.setCode(1000);
	    		  resultView.setMessage("成功");
	    		  resultView.setData(video_List);
	    		  Log4jUtil.info(resultView.toString());
	    		  return resultView;
	    	  }else {
	    		  resultView.setCode(2021);
	    		  resultView.setMessage("上传视频失败");
	    	  }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    Log4jUtil.info(resultView.toString());
	    return resultView;
	}
	
	/**
	 * 查询是否有在审核中的打招呼视频
	 */
	@RequestMapping(value="/hasAudit",method=RequestMethod.POST)
	public ResultView<String> hasAudit(Integer userId) {
		ResultView<String> resultView=new ResultView<String>();
		if(userId!=null) {
			Everyday_live_list everyday_live_list=uploadService.isAudit(userId);
			if(everyday_live_list!=null) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
				resultView.setData(String.valueOf(everyday_live_list.getStatus()));
			}else {
				resultView.setCode(1001);
				resultView.setMessage("暂无数据");
			}
		}else {
			resultView.setCode(2010);
			resultView.setMessage("请检查提交的参数");
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 获取审核通过的审核视频和图片
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/playAuditVideo",method=RequestMethod.POST)
	public ResultView<Everyday_live_list> playAuditVideo(Integer userId) {
		ResultView<Everyday_live_list> resultView=new ResultView<Everyday_live_list>();
		if(userId!=null) {
			Everyday_live_list everyday_live_list=uploadService.playAuditVideo(userId);
			if(everyday_live_list!=null) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
				resultView.setData(everyday_live_list);
			}else {
				resultView.setCode(1001);
				resultView.setMessage("暂无数据");
			}
		}else {
			resultView.setCode(2010);
			resultView.setMessage("请检查提交的参数");
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 后台审核每日打招呼视频接口
	 * @param auditParam
	 * @return
	 */
	@RequestMapping(value="/auditVideo",method=RequestMethod.POST)
	public ResultView<String> auditVideo(AuditParam auditParam) {
		ResultView<String> resultView=new ResultView<String>();
		auditParam.setUpdate_time(TimeFormat.getSimple());
		Everyday_live_list everyday_live_list=uploadService.selOldData(auditParam);
		File file=new File(everyday_live_list.getSave_url());
		if(file.exists()) {
			file.delete();
		}
		int result=uploadService.updateData(auditParam);
		if(result>0) {
			resultView.setCode(1000);
			resultView.setMessage("操作成功");
		}else {
			resultView.setCode(2022);
			resultView.setMessage("操作失败");
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 获取视频类型
	 * @return
	 */
	@RequestMapping(value="/getVideoType",method=RequestMethod.POST)
	public ResultView<List<VideoType>> getVideoType(int country_id) {
		Log4jUtil.info(uploadService.selVideoType(country_id).toString());
		return uploadService.selVideoType(country_id);
	}
	
	
	/**
	 * 删除视频
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteVideo",method=RequestMethod.POST)
	public ResultView<String> deleteVideo(DeleteParam deleteParam) {
		ResultView<String> resultView=uploadService.deleteVideo(deleteParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 根据用户ID查找用户上传视频
	 * @param userId
	 */
	@RequestMapping(value="/selVideoByUserId",method=RequestMethod.POST)
	public ResultView<List<VideoClips>> selVideoByUserId(VideoByUserIdParam videoByUserIdParam) {
		ResultView<List<VideoClips>> resultView=uploadService.selVideoByUserId(videoByUserIdParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
}
