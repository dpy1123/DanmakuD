package com.dd.danmaku.fileconvert;

import java.util.Map;

/**
 * 〈视频转换的接口〉
 * 
 * @author [董鹏音]
 * @version [1.1, 2012-11-15]
 * @since [知识商城/视频转换模块]
 */
public interface FileConvertService {
	
	/**
	 * 〈转换视频并修改文件库的状态〉
	 * @param source 源文件的地址,格式X:/directory/fileName
	 * @param destination 输出文件目录,格式X:/directory
	 * @param commands 参数
	 * @return [boolean]视频转换是否成功
	 * @throws Exception 
	 */
	public boolean convert(String source,String destination, Map<String,String> commands) throws Exception;
	
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
	 * @return 文件长度，单位s。-1表示获取失败
	 * @throws Exception 
	 */
	public long getVideoLength(String source) throws Exception;
}
