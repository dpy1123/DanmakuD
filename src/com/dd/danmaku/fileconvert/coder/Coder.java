package com.dd.danmaku.fileconvert.coder;

import java.util.Map;


/**
 * 〈视频转换器用到的Coder(转码器)的模版，抽象类〉
 * 
 * @author dd
 * @version [1.0, 2012-10-31]
 */
public abstract class Coder {

	protected String coderPath = null;
	
	/**
	 * <转换视频文件>
	 * @param 待转换文件
	 * @throws Exception
	 * @exception 格式转换失败
	 */
	public abstract void convert(String source, String dest, Map<String,String> commands) throws Exception;

	/**
	 * <判断文件格式是否可被该Coder转换>
	 * @param 待转换文件
	 * @return [boolean]文件是否可转换
	 */
	public abstract boolean convertable(String source);
	
}
