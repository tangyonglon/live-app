package com.douliao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TransfMediaTool {
	private static boolean checkfile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return false;
        }
        return true;
    }
	/**
	 * 转换成MP4格式
	 * @param ffmpegPath
	 * @param oldfilepath
	 * @param outputPath
	 * @return
	 */
	private static boolean processMp4(String ffmpegPath,String oldfilepath,String outputPath) {
	    if (!checkfile(oldfilepath)) {
	        System.out.println(oldfilepath + " is not file");
	        return false;
	    }
	    List<String> command = new ArrayList<String>();
	    command.add(ffmpegPath + "ffmpeg");
	    command.add("-i");
	    command.add(oldfilepath);
	    command.add("-c:v");
	    command.add("libx264");
	    command.add("-mbd");
	    command.add("0");
	    command.add("-c:a");
	    command.add("aac");
	    command.add("-strict");
	    command.add("-2");
	    command.add("-pix_fmt");
	    command.add("yuv420p");
	    command.add("-movflags");
	    command.add("faststart");
	    command.add(outputPath + "a.mp4");
	    try {

	        // 方案1
//	        Process videoProcess = Runtime.getRuntime().exec(ffmpegPath + "ffmpeg -i " + oldfilepath 
//	                + " -ab 56 -ar 22050 -qscale 8 -r 15 -s 600x500 "
//	                + outputPath + "a.flv");

	        // 方案2
	        Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();

	        new PrintStream(videoProcess.getErrorStream()).start();

	        new PrintStream(videoProcess.getInputStream()).start();

	        videoProcess.waitFor();

	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	/**
	   * 视频转码flv
	   *
	   * @param ffmpegPath
	   *      转码工具的存放路径
	   * @param upFilePath
	   *      用于指定要转换格式的文件,要截图的视频源文件
	   * @param codcFilePath
	   *      格式转换后的的文件保存路径
	   * @param mediaPicPath
	   *      截图保存路径 
	   * @return
	   * @throws Exception
	   */
	  public void processFLV(String ffmpegPath, String upFilePath, String codcFilePath,String mediaPicPath) {
		// 创建一个List集合来保存转换视频文件为flv格式的命令  
	        List<String> convert = new ArrayList<String>();  
	        convert.add(ffmpegPath); // 添加转换工具路径  
	        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件  
	        convert.add(upFilePath); // 添加要转换格式的视频文件的路径  
	        convert.add("-qscale");     //指定转换的质量  
	        convert.add("6");  
	        convert.add("-ab");        //设置音频码率  
	        convert.add("64");  
	        convert.add("-ac");        //设置声道数  
	        convert.add("2");  
	        convert.add("-ar");        //设置声音的采样频率  
	        convert.add("22050");  
	        convert.add("-r");        //设置帧频  
	        convert.add("24");  
	        convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件  
	        convert.add(codcFilePath);  
	  
	        // 创建一个List集合来保存从视频中截取图片的命令  
	        List<String> cutpic = new ArrayList<String>();  
	        cutpic.add(ffmpegPath);  
	        cutpic.add("-i");  
	        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）  
	        cutpic.add("-y");  
	        cutpic.add("-f");  
	        cutpic.add("image2");  
	        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间  
	        cutpic.add("3"); // 添加起始时间为第3秒  
	        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间  
	        cutpic.add("0.001"); // 添加持续时间为1毫秒  
	        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小  
	        cutpic.add("300*280"); // 添加截取的图片大小为350*240  
	        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径  
	    /*
	     * convert.add("-qscale"); // 指定转换的质量 convert.add("6");
	     * convert.add("-ab"); // 设置音频码率 convert.add("64"); convert.add("-ac");
	     * // 设置声道数 convert.add("2"); convert.add("-ar"); // 设置声音的采样频率
	     * convert.add("22050"); convert.add("-r"); // 设置帧频 convert.add("24");
	     * convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
	     */
	    convert.add(codcFilePath);
	    try {
	      Process videoProcess = new ProcessBuilder(convert).redirectErrorStream(true).start();
	      new PrintStream(videoProcess.getInputStream()).start();
	      videoProcess.waitFor();
	    } catch (IOException e1) {
	      e1.printStackTrace();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	  }
	  /**
	   * 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 先用mencoder转换为avi(ffmpeg能解析的)格式
	   *
	   * @param mencoderPath
	   *      转码工具的存放路径
	   * @param upFilePath
	   *      用于指定要转换格式的文件,要截图的视频源文件
	   * @param codcFilePath
	   *      格式转换后的的文件保存路径
	   * @return
	   * @throws Exception
	   */
	  public String processAVI(String mencoderPath, String upFilePath, String codcAviPath) {
//	    boolean flag = false;
	    List<String> commend = new ArrayList<String>();
	    commend.add(mencoderPath);
	    commend.add(upFilePath);
	    commend.add("-oac");
	    commend.add("mp3lame");
	    commend.add("-lameopts");
	    commend.add("preset=64");
	    commend.add("-lavcopts");
	    commend.add("acodec=mp3:abitrate=64");
	    commend.add("-ovc");
	    commend.add("xvid");
	    commend.add("-xvidencopts");
	    commend.add("bitrate=600");
	    commend.add("-of");
	    commend.add("avi");
	    commend.add("-o");
	    commend.add(codcAviPath);
	    try {
	      // 预处理进程
	      ProcessBuilder builder = new ProcessBuilder();
	      builder.command(commend);
	      builder.redirectErrorStream(true);
	      // 进程信息输出到控制台
	      Process p = builder.start();
	      BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	      String line = null;
	      while ((line = br.readLine()) != null) {
	        System.out.println(line);
	      }
	      p.waitFor();// 直到上面的命令执行完，才向下执行
	      return codcAviPath;
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null;
	    }
	  }
	}
	class PrintStream extends Thread {
	  java.io.InputStream __is = null;
	  public PrintStream(java.io.InputStream is) {
	    __is = is;
	  }
	  public void run() {
	    try {
	      while (this != null) {
	        int _ch = __is.read();
	        if (_ch != -1)
	          System.out.print((char) _ch);
	        else
	          break;
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
}
