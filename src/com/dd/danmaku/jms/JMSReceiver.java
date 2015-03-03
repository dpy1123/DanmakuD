package com.dd.danmaku.jms;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import com.dd.danmaku.fileconvert.FileConvertService;
import com.dd.danmaku.resource.bean.Video;
import com.dd.danmaku.resource.service.ResourceService;
import com.dd.danmaku.resource.service.VideoService;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Component
public class JMSReceiver implements MessageListener {
	Logger logger = Logger.getLogger(JMSReceiver.class);
	
	@Resource
	protected VideoService videoService;
	
	@Resource
	protected GridFsTemplate gridFsTemplate;
	
	@Resource
	protected FileConvertService fileConvertService;
	
	@Resource
	protected ResourceService resourceService;
	
	@Resource
	protected JMSSender jmsSender;
	
	protected String convertTempPath;//视频转换的临时路径
	
	public JMSReceiver() {
		try {
			String classFileRealPath = URLDecoder.decode(this.getClass().getResource("").getPath(), "UTF-8");
			convertTempPath = classFileRealPath.substring(0, classFileRealPath.indexOf("WEB-INF")) + "temp_files/convert/";//根路径
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void onMessage(Message message) {
		ObjectMessage msg = (ObjectMessage) message;
		try {
			processMessage((JMSMessage) msg.getObject());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processMessage(JMSMessage jmsMsg) {
		//根据收到消息的不同action，调用不同的系统的方法
		if(JMSMessage.ACTION_VIDEO_CONVERT.equals(jmsMsg.getAction())){
			logger.info("..........process JMSMessage: "+ jmsMsg);
			
			//TODO 调用监控系统，如果本机cpu使用率不高，则开始视频转换
			boolean result = false;
			HashMap<String, Object> content = (HashMap<String, Object>) jmsMsg.getContent();
			
			//得到转换次数参数
			int tryTimes = (Integer) content.get("convertTimes");
			Map<String,String> commands = content.get("commands")==null?null:(Map<String,String>) content.get("commands");
			String videoId = (String) content.get("videoId");
			String resourceId = (String) content.get("resourceId");
			
			//更新文件状态
			videoService.updateVideoStatus(videoId, Video.CONVERTING);
			
			//获取待转换文件
			Video video = videoService.getById(videoId);
			String fsFileName = video.getFsFileName();
			GridFSDBFile file = gridFsTemplate.findOne(query(whereFilename().is(fsFileName)));
			//TODO 未防止文件名重复，originalFileName = UploaderId + file.get("aliases")
			String originalFileName = (String) file.get("aliases");
			InputStream ins = file.getInputStream();
			File sourceFile = null;
			//数据保存在本地临时文件	
			try {
				if(!new File(convertTempPath).exists()){
					new File(convertTempPath).mkdirs();
				}
				sourceFile = new File(convertTempPath , originalFileName);
				FileOutputStream out = new FileOutputStream(sourceFile);
				byte[] buffer = new byte[2048];
				int readed = 0;
				while ((readed = ins.read(buffer)) != -1) {
					out.write(buffer, 0, readed);
				}
				out.close();
				ins.close();
				logger.info("文件已保存至"+convertTempPath+"，文件大小："+sourceFile.length());
			} catch (IOException e) {
				logger.error("获取待转换文件失败", e);
			}
			
			com.dd.danmaku.resource.bean.Resource resource = resourceService.getById(resourceId);

			//转换
			File convertedFile = new File(convertTempPath , "CONVERT_"+originalFileName.substring(0, originalFileName.indexOf("."))+".mp4");
			long length = 0L;
			try {
				result = fileConvertService.convert(sourceFile.getPath(), convertedFile.getPath(), commands);
				length = fileConvertService.getVideoLength(sourceFile.getPath());
				
				//如果没有上传预览图，则生成预览图
				if(com.dd.danmaku.resource.bean.Resource.NO_PIC.equals(resource.getPreviewImg())){
					String imgPath = convertTempPath+originalFileName.substring(0, originalFileName.indexOf("."))+".jpg";
					File img = new File(imgPath);
					result = fileConvertService.generatePreviewPicture(sourceFile.getPath(), img.getPath());
					if(result){
						FileInputStream in = new FileInputStream(img);
						String newfile = UUID.randomUUID().toString();
						GridFSFile gfile = gridFsTemplate.store(in, newfile, "image/jpeg");
						gfile.save();
						
						resource.setPreviewImg(newfile);
						
						in.close();
					}
					img.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(result && length != -1){
				try {
					//保存转换后文件至fs
					FileInputStream convertedFileIns = new FileInputStream(convertedFile);
					String newfile = UUID.randomUUID().toString();
					GridFSFile gfile = gridFsTemplate.store(convertedFileIns, newfile, "video/mp4");
					gfile.put("aliases", convertedFile.getName());//在别名中存储文件原名
					gfile.save();
					logger.info("文件已保存至fs，文件大小："+gfile.getLength());
					convertedFileIns.close();
					//更新video信息
					video.setConvertedFsFileName(newfile);
					video.setDuration(length);
					video.setStatus(Video.CONVERTED);
					//更新资源的总时长信息
					resource.setDuration(resource.getDuration() + length);
				} catch (IOException e) {
					logger.error("文件已保存至fs失败", e);
				}
			}else{
				video.setStatus(Video.FAILED);
			}
			
			//更新文件状态
			videoService.update(video);
			
			//更新资源状态
			resourceService.update(resource);
			
			//删除临时文件
			sourceFile.delete();
			convertedFile.delete();
			
			//如果转换未成功，且转换次数不超过3次，则再次投入转换队列
			if(!result && tryTimes <= 3){
				content.put("convertTimes", tryTimes+1);
				jmsSender.sendMessage(jmsMsg);
				
				logger.info("++++++++++转换失败，剩余尝试次数： "+(3-tryTimes));
			}
		}
	}


}
