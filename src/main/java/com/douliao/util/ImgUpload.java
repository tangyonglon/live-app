package com.douliao.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class ImgUpload {
	//图片桶名
	private static final String bucketName="usa-imges";
	
	public String uploadImg(MultipartFile file,int userId,String webUploadPath) {
		if (!file.isEmpty()) {
			if (file.getContentType().contains("image")) {
				try {
					  String temp = "images" + File.separator + "upload" + File.separator;
					  // 获取图片的文件名
					  String fileName = file.getOriginalFilename();
					  // 获取图片的扩展名
					  String extensionName = StringUtils.substringAfter(fileName, ".");
					  // 新的图片文件名 = 获取时间戳+"."图片扩展名
					  String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
					  // 数据库保存的目录
					  String datdDirectory = temp.concat(String.valueOf(userId)).concat(File.separator);
					  // 文件路径
					  String filePath = webUploadPath.concat(datdDirectory);
					  
					  File dest = new File(filePath, newFileName);
					  if (!dest.getParentFile().exists()) {
						  dest.getParentFile().mkdirs();
					  }
					  // 上传到指定目录
					  file.transferTo(dest);
					  return filePath;
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			} else {
				return null;
			}
		}
		return null;
	}
	
	public static String uploadImg(MultipartFile file,int userId,String imgPath,String showImg) {
		System.err.println("图片上传");
		if (!file.isEmpty()) {
			if (file.getContentType().contains("image")) {
				try {
					  // 获取图片的文件名
					  String fileName = file.getOriginalFilename();
					  // 获取图片的扩展名
					  String extensionName = StringUtils.substringAfter(fileName, ".");
					  // 新的图片文件名 = 获取时间戳+"."图片扩展名
					  String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
					  // 数据库保存的目录
					  showImg = showImg+File.separator+newFileName;
					  // 文件路径
					  imgPath = imgPath+File.separator+newFileName;
					  
					  File dest = new File(imgPath);
					  if (!dest.getParentFile().exists()) {
						  dest.getParentFile().mkdirs();
					  }
					  // 上传到指定目录
					  file.transferTo(dest);
					  
					  
					  //上传到s3
					  Log4jUtil.info("开始上传到s3桶----");
					  UploadTest upload=new UploadTest();
					  File uploadFile = new File(imgPath);
					  String uploadKey = newFileName;
					  String result=upload.uploadToS3(uploadFile,uploadKey,bucketName);
					  Log4jUtil.info("上传到s3桶完毕，返回结果："+result);
					  if (dest.exists()) {
						  dest.delete();
					  }
					  return result;
//					  return showImg;
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			} else {
				return null;
			}
		}
		return null;
	}
		
}
