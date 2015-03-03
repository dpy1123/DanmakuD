package com.dd.danmaku.fileconvert.convertor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dd.danmaku.fileconvert.coder.Coder;
import com.dd.danmaku.fileconvert.coder.FFmpegCoder;
import com.dd.danmaku.fileconvert.coder.MenCoder;


/**
 * 〈视频转换器的Windows平台的实现类〉
 * 
 * @author [董鹏音]
 * @version [1.0, 2012-10-31]
 * @version [1.1, 2012-11-20] 增加生成视频预览图功能
 * @version [1.2, 2012-11-22] 增加rm和rmvb文件的转换功能
 * @since [知识商城/视频转换模块]
 */
public class FileConvertorImpl implements FileConvertor {

	private List<Coder> coders;//管理可用的Coder(转码器)
	private Coder avalibleCoder;//当前使用的Coder
	
	public FileConvertorImpl() {
		coders=new ArrayList<Coder>();
		// 初始化所有可用的Coder,优先使用的coder先放入list
		coders.add(new FFmpegCoder());
		coders.add(new MenCoder());
	}

	/**
	 * <转换视频文件>
	 * @return 转换是否成功
	 * @throws Exception
	 */
	public boolean convert(String source, String dest, Map<String,String> commands) throws Exception {
		boolean result = false;
		FFmpegCoder coder = new FFmpegCoder();

		//如果avalibleCoder是MenCoder，要用FFmpeg再包装一次，MenCoder合成为MP4不写头索引
		if(avalibleCoder instanceof MenCoder){
			File preMp4File = new File(source.substring(0, source.lastIndexOf("."))+".temp.mp4");//中间mp4文件
			avalibleCoder.convert(source, preMp4File.getPath(), commands);
			System.out.println("2次包装");
			coder.convert(preMp4File.getPath(), dest, null);
			
			if(preMp4File.exists()){
				preMp4File.delete();
			}
		}else{
			avalibleCoder.convert(source, dest, commands);
		}
		
		//判断转换是否完成
		result = coder.isCovertComplete(source, dest);
		return result;
	}

	
	/**
	 * <视频文件预览图的生成接口>
	 * @param source 待转换文件，必须是FFmpeg能转换的格式
	 * @throws Exception
	 */
	public boolean generatePreviewPicture(String source, String dest) throws Exception {
		FFmpegCoder coder = new FFmpegCoder();
		coder.generatePreviewPicture(source, dest);
		return new File(dest).exists();
	}
	
	
	/**
	 * <判断文件格式是否可被本系统转换>
	 * @param 待转换文件
	 * @return [false]文件不可转换；[true]文件可转换，并将要用的Coder赋予avalibleCoder
	 */
	public boolean convertable(String source) {
		for (Coder coder : coders) {
			if(coder.convertable(source)){
				avalibleCoder = coder;
				return true;
			}
		}
		return false;
	}

	
	public long getVideoLength(String source) throws Exception {
		FFmpegCoder coder = new FFmpegCoder();
		return coder.getVideoLength(coder.getVideoInfo(source));
	}


}
