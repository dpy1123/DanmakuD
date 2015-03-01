package com.dd.danmaku.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class JMSReceiver implements MessageListener {
	
	
	public void onMessage(Message message) {
		ObjectMessage msg = (ObjectMessage) message;
		try {
			processMessage((JMSMessage) msg.getObject());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private void processMessage(JMSMessage jmsMsg) {
		//根据收到消息的不同action，调用不同的系统的方法
		if(JMSMessage.ACTION_VIDEO_CONVERT.equals(jmsMsg.getAction())){
			System.out.println("..........processMessage "+ jmsMsg.getContent());
			
			//TODO 调用监控系统，如果本机cpu使用率不高，则开始视频转换
			
			boolean result = false;
			//得到转换次数参数
//			int tryTimes = (Integer) jmsMsg.get("convertTimes");
//			String source = (String) jmsMsg.get("source");
//			String dest = (String) jmsMsg.get("destination");
//			String previewImg = (String) jmsMsg.get("previewImgPath");
//			Map<String,String> commands = (Map<String,String>) jmsMsg.get("commands");
//			String videoId = (String) jmsMsg.get("videoId");
//			String resourceId = (String) jmsMsg.get("resourceId");
//			String convertorIP = (String) jmsMsg.get("convertorIP");
			
			//更新文件状态
//			videoService = getVideoService();
//			videoService.updateVideoStatus(videoId, "转换中");
			
			//转换
//			convertService = getConvertService(convertorIP);
//			result = convertService.convert(source, dest, commands);
//			result = convertService.generatePreviewPicture(dest, previewImg);
//			long length = convertService.getVideoLength(source);
					
			//更新文件状态
//			videoService.updateVideoDuration(videoId, length);
//			videoService.updateVideoStatus(videoId, result?"转换成功":"转换失败");
			
			//更新资源状态
//			resourceService = getResourceFacade();
//			resourceService.updateDuration(resourceId, parseTimecode(length));
			
			
			//如果转换未成功，且转换次数不超过3次，则再次投入转换队列
//			if(!result && tryTimes <= 2){
//				jmsMsg.put("convertTimes", tryTimes+1);
//				JMSSender.sendMessage(jmsMsg);
//				
//				System.out.println("++++++++++转换失败，剩余尝试次数： "+(3-tryTimes));
//			}
		}
	}


}
