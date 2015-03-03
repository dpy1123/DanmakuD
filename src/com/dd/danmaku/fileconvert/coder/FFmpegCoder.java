package com.dd.danmaku.fileconvert.coder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 〈Windows平台下的基于FFmpeg的Coder〉
 * 
 * @author [董鹏音]
 * @version [1.0, 2012-10-31]
 * @version [1.1, 2013-2-28] 修正了视频宽高比的设置
 * @version [1.2, 2013-3-6] 生成预览视频
 * @since [知识商城/视频转换模块]
 */
public class FFmpegCoder extends Coder {

	/**
	 * 读取配置属性
	 */
	protected Properties props;
	
	public FFmpegCoder() {
		props = new Properties();
		try {
			props.load(FFmpegCoder.class.getClassLoader().getResourceAsStream("config/properties/fileConvertConfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			String classFileRealPath = URLDecoder.decode(this.getClass().getResource("").getPath(), "UTF-8");
			this.coderPath = classFileRealPath.substring(0, classFileRealPath.indexOf("WEB-INF")) + "coder/ffmpeg.exe";//根路径
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
//		this.coderPath = props.getProperty("ffmpeg_exe_path");//ffmpeg.exe的路径
	}
	

	public void convert(String source, String dest, Map<String,String> commands) throws RuntimeException{
		List<String> cmd = buildCommands(source, dest, commands);// 准备参数并返回命令行
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(cmd);
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream in = process.getInputStream();
			byte[] bs = new byte[1024];
			while ((in.read(bs)) != -1) {//正在转换,输出cmd状态
				//System.out.println(new String(bs));
			}
		} catch (Exception e) {
			throw new RuntimeException("格式转换中发生异常");
		}
	}

	/**
	 * 判断视频转换是否完成，如果目标文件比源文件时长少3s以上，认为转换未成功
	 * @param source 转换前文件全路径
	 * @param dest 转换后文件全路径
	 * @return
	 */
	public boolean isCovertComplete(String source, String dest) {
		long srcLength = getVideoLength(getVideoInfo(source));
		long destLength = getVideoLength(getVideoInfo(dest));
		if(srcLength - destLength > 3){//如果目标文件比源文件时长少3s以上，认为转换未成功
			return false;
		}
		return true;
	}
	
	/**
	 * 得到视频文件的宽高比
	 * @param videoInfo 包含文件信息的字符串
	 * @return 宽/高，-1表示失败
	 */
	private float getVideoAspectRatio(String videoInfo) {
		if (videoInfo.contains("Stream") && videoInfo.contains("Video:")) {
			Pattern p = Pattern.compile(", (\\d+)x(\\d+)");
			Matcher m = p.matcher(videoInfo);
			if (m.find()) {
				String videoWidth = m.group(1);
				String videoHeight = m.group(2);
				return Float.parseFloat(videoWidth) / Float.parseFloat(videoHeight);
			}
		}
		return -1;
	}
	
	/**
	 * 得到视频文件长度
	 * @param videoInfo 包含文件信息的字符串
	 * @return 文件长度，单位s
	 */
	public long getVideoLength(String videoInfo) {
		long length = 0 ;
		//解析形如：Duration: 00:04:04.20,的字符串
		int beginOfDuration = videoInfo.indexOf("Duration: ");
		int endOfDuration = videoInfo.indexOf(",", beginOfDuration);
		if(beginOfDuration != -1 && beginOfDuration < endOfDuration){
			String duration = videoInfo.substring(beginOfDuration + "Duration: ".length(), endOfDuration);
			System.out.println("格式化前时长："+duration);
			String[] time = duration.split(":");
			float second = Float.parseFloat(time[2]);
			int minute = Integer.parseInt(time[1]);
			int hour = Integer.parseInt(time[0]);
			length = (long) ((hour * 60 + minute) * 60 + second);
			System.out.println("时长："+length);
		}
		return length;
	}


	/**
	 * 得到视频文件的信息
	 * @param source 源文件全路径
	 * @return 包含文件信息的字符串
	 */
	public String getVideoInfo(String source) {
		String result = null;
		List<String> command = new ArrayList<String>();
		command.add(coderPath);//ffmpeg.exe的路径
		command.add("-i");
		command.add(source);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(command);
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream in = process.getInputStream();
			byte[] bs = new byte[1024];
			while ((in.read(bs)) != -1) {//正在转换,输出cmd状态
				result += new String(bs);
			}
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	/**
	 * 读取配置文件中的默认设置
	 * @return
	 */
	private Map<String, String> prepareDefaultSettings(){
		Map<String, String> commands = new HashMap<String, String>();
		//添加一些默认设置
		commands.put("-ab", props.getProperty("ab"));//音频数据流,128、320kps等  
		commands.put("-ac", props.getProperty("ac"));//设置通道 缺省为1
		commands.put("-ar", props.getProperty("ar"));//声音的频率 22050 基本都是这个
		commands.put("-qscale", props.getProperty("qscale"));//清晰度 ,4 为最好可是文件大,一般6就可以了
		commands.put("-r", props.getProperty("r"));//播放帧数
		commands.put("-y", null);//覆盖输出文件
		commands.put("-s", props.getProperty("s"));//设置分辨率
		return commands;
	}
	
	/**
	 * 生成转换所需的cmd命令
	 * @param source 源文件全路径
	 * @param dest 目标文件路径
	 * @param commands 转换参数，如果commands是null则应用默认配置
	 * @return [List<String>]转换所需的cmd命令
	 */
	private List<String> buildCommands(String source, String dest, Map<String,String> commands) {
		if(commands == null || commands.size() == 0){//如果用户没有设置参数，使用默认参数
			commands = prepareDefaultSettings();
		}

		//转换之前，先确定视频的宽高比
		float rate = this.getVideoAspectRatio(this.getVideoInfo(source));
		if(rate != -1){
			String s = (String) commands.get("-s");//取出分辨率
			String width = s.split("x")[0];
			int h = (int) (Integer.parseInt(width)/rate);
			String height = String.valueOf(h);
			commands.put("-s", width+"x"+height);
		}
		
		List<String> command = new ArrayList<String>();
		command.add(coderPath);//ffmpeg.exe的路径
		command.add("-i");
		command.add(source);
		for (Map.Entry<String, String> opt : commands.entrySet()) {
			command.add(opt.getKey());
			if(opt.getValue() != null)
				command.add(opt.getValue().toString());
		}
		command.add(dest);
		return command;
	}
	
	
	/**
	 * <判断文件格式是否可被该Coder转换>
	 * @param 待转换文件
	 * @return [boolean]文件是否可转换
	 */
	public boolean convertable(String source) {
		String sourceType = getSourceFileType(source);
		if("flv".equals(sourceType)){
			return true;
		}else if("mp4".equals(sourceType)){
			return true;
		}else if("mov".equals(sourceType)){
			return true;
		}else if("avi".equals(sourceType)){
			return true;
		}else if("m4v".equals(sourceType)){
			return true;
		}else if("mpg".equals(sourceType)){
			return true;
		}else if("wmv".equals(sourceType)){
			return true;
		}else if("ogg".equals(sourceType)){
			return true;
		}else if("3gp".toString().equals(sourceType)){
			return true;
		}
		return false;
	}

	/**
	 * <获取待转换文件类型>
	 * @param source 待转换文件
	 * @return [String]待转换文件类型
	 */
	private String getSourceFileType(String source) {
		String type="unkown";
		if(source != null){
			int lastIndex = source.lastIndexOf(".");
			if(lastIndex >- 1)
			type = source.substring(lastIndex + 1).toLowerCase();
		}
		return type;
	}

	/**
	 * 生成jpg格式预览图
	 * @param source
	 * @param dest /path/XXX.jpg
	 * @throws Exception
	 */
	public void generatePreviewPicture(String source, String dest) throws Exception {
		// 添加相关设置
		Map<String, String> options = new HashMap<String, String>();
		options.put("-vcodec", props.getProperty("vcodec"));
		options.put("-ss", props.getProperty("ss"));//-ss position 搜索到指定的时间，默认是1秒的位置生成预览图
		options.put("-t", props.getProperty("t"));//-t duration 设置纪录时间
		options.put("-y", null);// 覆盖输出文件
		options.put("-s", props.getProperty("s"));//设置分辨率

		convert(source, dest, options);
	}
	
	/**
	 * 截取一段视频，从begin处开始，截取时长为duration的一段视频
	 * @param source
	 * @param dest
	 * @param begin HH:MM:SS
	 * @param duration HH:MM:SS
	 * @param additionalOps
	 * @throws Exception
	 */
	public void seekConvert(String source, String dest, String begin, String duration, Map<String, String> additionalOps) throws Exception {
		//读取默认设置
		Map<String, String> options = prepareDefaultSettings();
		//添加相关设置
		options.put("-ss", begin);//-ss position 搜索到指定的时间，单位是秒
		options.put("-t", duration);//-t duration 设置持续时间
		//覆盖追加设置
		if(additionalOps != null)
			options.putAll(additionalOps);
		
		convert(source, dest, options);
	}
	

}
