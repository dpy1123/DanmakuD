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


/**
 * 〈Windows平台下的基于Mencoder的Coder〉
 * 
 * @author [董鹏音]
 * @version [1.0, 2012-11-22]
 * @since [知识商城/视频转换模块]
 */
public class MenCoder extends Coder {
	
	/**
	 * 读取配置属性
	 */
	protected Properties props;
	
	public MenCoder() {
		props = new Properties();
		//从配置文件中读取相关信息
		try {
			props.load(MenCoder.class.getClassLoader().getResourceAsStream("config/properties/fileConvertConfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			String classFileRealPath = URLDecoder.decode(this.getClass().getResource("").getPath(), "UTF-8");
			this.coderPath = classFileRealPath.substring(0, classFileRealPath.indexOf("WEB-INF")) + "coder/mencoder.exe";//根路径
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
//		this.coderPath = props.getProperty("mencoder_exe_path");//mencoder.exe的路径
	}
	
	
	/**
	 * <转换视频文件>
	 * @param 待转换文件
	 * @throws ThreeException
	 * @exception [E0301] 格式转换失败
	 */
	public void convert(String source, String dest, Map<String,String> commands) throws RuntimeException{
		//准备转换参数的设置
		List<String> cmd = buildCommands(source, dest, commands);//准备参数并返回命令行
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
	 * 读取配置文件中的默认设置
	 * @return
	 */
	private Map<String, String> prepareDefaultSettings(){
		Map<String, String> commands = new HashMap<String, String>();
		//添加一些默认设置
		commands.put("-oac", props.getProperty("oac"));//编码文件的音频部分编码器
		commands.put("-lameopts", props.getProperty("lameopts"));//设置音频参数
		commands.put("-ovc", props.getProperty("ovc"));//编码文件的视频部分编码器
		commands.put("-lavcopts", props.getProperty("lavcopts"));//设置音频参数
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

		List<String> command = new ArrayList<String>();
		command.add(coderPath);//mencoder.exe的路径
		command.add(source);
		for (Map.Entry<String, String> opt : commands.entrySet()) {
			command.add(opt.getKey());
			if(opt.getValue() != null)
				command.add(opt.getValue().toString());
		}
		command.add("-o");
		command.add(dest);
		return command;
	}
	
	
	
	/**
	 * <判断文件格式是否可被该Coder转换，目前只针对rm和rmvb格式的转换>
	 * @param 待转换文件
	 * @return [boolean]文件是否可转换
	 */
	public boolean convertable(String source) {
		String sourceType = getSourceFileType(source);
		if("rm".equals(sourceType)){
			return true;
		}else if("rmvb".equals(sourceType)){
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
			int lastIndex=source.lastIndexOf(".");
			if(lastIndex >- 1)
			type = source.substring(lastIndex + 1).toLowerCase();
		}
		return type;
	}

}
