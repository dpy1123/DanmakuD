package com.dd.danmaku.fileconvert;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dd.danmaku.fileconvert.convertor.FileConvertorImpl;
import com.dd.danmaku.fileconvert.convertor.FileConvertor;


/**
 * 〈视频转换的实现类〉
 * 
 * @author [董鹏音]
 * @version [1.0, 2012-10-31]
 * @since [知识商城/视频转换模块]
 */
@Component
public class FileConvertServiceImpl implements FileConvertService {
	
	FileConvertor convertor = new FileConvertorImpl();

	public boolean convert(String source, String destination, Map<String, String> commands) throws Exception {
		boolean result = false;
		System.out.println("source:" +source + " desctination: "+destination);
		File sourceFile=new File(source);
		//如果源文件不存在，返回
		if(!sourceFile.exists()){
			return false;
		}
		//如果输出的目录不存在，则创建目录
		File destinationDirectory=new File(destination);
		if(!destinationDirectory.getParentFile().exists()){
			destinationDirectory.mkdirs();
		}
		//转换文件(面向接口)
		if (convertor.convertable(source)) {
			result = convertor.convert(source, destination, commands);
		}
		return result;
	}

	public boolean generatePreviewPicture(String source, String dest) throws Exception {
		boolean result = false;
		System.out.println("source:" +source + " desctination: "+ dest);
		File sourceFile=new File(source);
		//如果源文件不存在，返回
		if(!sourceFile.exists()){
			return false;
		}
		//如果输出的目录不存在，则创建目录
		File destinationDirectory=new File(dest);
		if(!destinationDirectory.getParentFile().exists()){
			destinationDirectory.mkdirs();
		}
		
		result = convertor.generatePreviewPicture(source, dest);
		return result;
	}

	public long getVideoLength(String source) throws Exception {
		long length = -1;
		length = convertor.getVideoLength(source);
		return length;
	}

}
