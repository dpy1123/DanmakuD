package com.dd.danmaku.fileconvert.convertor;

import java.util.Map;


/**
 * 〈视频转换器的接口类〉
 * @author [董鹏音]
 * @version [1.0, 2012-10-31]
 * @version [1.1, 2012-11-20] 增加生成视频预览图功能
 * @since [知识商城/视频转换模块]
 */
public interface FileConvertor {
	
	/**
	 * <判断文件格式是否可转换>
	 * @param 待转换文件全路径
	 * @return [boolean]文件是否可转换
	 */
	public boolean convertable(String source);

	/**
	 * 转换视频文件
	 * @param source 源文件全路径
	 * @param dest 目标文件全路径
	 * @param commands 参数
	 * @throws Exception
	 */
	public boolean convert(String source, String dest, Map<String,String> commands) throws Exception;
	
	
	/**
	 * 生成jpg格式预览图
	 * @param source 源文件全路径
	 * @param dest 目标文件全路径,/path/XXX.jpg
	 * @throws Exception
	 */
	public boolean generatePreviewPicture(String source, String dest) throws Exception;

	/**
	 * 得到视频文件长度
	 * @param source 源文件全路径
	 * @return 文件长度，单位s
	 */
	public long getVideoLength(String source) throws Exception;
}
