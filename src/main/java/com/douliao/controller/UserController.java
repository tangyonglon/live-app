package com.douliao.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.douliao.controller.server.model.AuditParam;
import com.douliao.controller.server.model.TestModel;
import com.douliao.model.User;
import com.douliao.service.UserService;
import com.douliao.util.UploadTest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@Api("测试接口相关API")
public class UserController {
	private final Logger log=LoggerFactory.getLogger(getClass());
	@Value("${web.imagesPath}")
	private String webUploadPath;
	
	public static void main(String[] args) {
		AuditParam auditParam=new AuditParam();
	}
	
	
	@ApiOperation("测试返回List数据")
	@ResponseBody
	@RequestMapping(value="/open/getUserAll",method=RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public List<String> getUserAll(@ApiParam(required=true ,name="name",value="名字",defaultValue="张三")@RequestParam String name){
		System.out.println(name);
		List<String> list=new ArrayList<String>();
		list.add(name);
		list.add("lisi");
		list.add("wangwu");
		list.add("这是live-app服务：端口号8080");
		return list;
	}
	
	@ApiOperation("测试返回String数据类型")
	@RequestMapping(value="/user",method=RequestMethod.GET)
	public String user(String name) {
		System.out.println(name);
		log.info("系统错误----");
		System.out.println("----"+webUploadPath);
//		int i=1/0;
		return name;
	}
	
	@ApiOperation("测试@ApiModel注解用法")
	@RequestMapping(value="/getTestModel",method=RequestMethod.POST)
	public TestModel getAllGoodsParam(@RequestBody TestModel testModel) {
		return testModel;
	}
	
	
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
//	@Cacheable(value="findAll")
	public List<User> findAll(){
		System.out.println("没有缓存");
		return userService.findAll();
	}
	
//	@Autowired  
//    private RedisClient redisClinet;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@RequestMapping(value="/set",method=RequestMethod.POST)
    public String set(String key, String value) throws Exception{  
		System.out.println(key+"========"+value);
		redisTemplate.opsForValue().set(key, value);
		//RedisUtil redis=new RedisUtil();
		//redis.set(key, value);
//        redisClinet.set(key, value);  
        return "success";  
    }  
      
	
    @RequestMapping(value="/get",method=RequestMethod.POST)  
    public String get(String key) throws Exception {  
        return (String) redisTemplate.opsForValue().get(key);  
    }
    
    @RequestMapping(value="/uploadS3",method=RequestMethod.POST)
    public String uploadS3(@RequestParam(value = "file", required = false) MultipartFile[] multipartFile,@RequestParam("userId") Integer userId,HttpServletRequest request,HttpServletResponse response) throws IOException {
    	String result=null;
    	for(MultipartFile file:multipartFile) {
			if(!file.isEmpty()) {
				System.out.println("file:"+file);
				String fileName = file.getOriginalFilename().toString();
				File filedirs = new File(fileName);
				System.err.println("文件路径："+filedirs);
				UploadTest upload=new UploadTest();
				result=upload.uploadToS3(filedirs, fileName);
			}
    	}
    	return result;
    }
    
    
}
