package com.dd.danmaku.jms;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;

/**
 * jms消息体
 * @author dd
 *
 */
public class JMSMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 消息类型:视频转换
	 */
	@Transient
	public static final String ACTION_VIDEO_CONVERT = "VIDEO_CONVERT";
	
	private String action;
	private Object content;
	
	/**
	 * jms消息体
	 * @param action 消息类型标识
	 * @param content 消息内容
	 */
	public JMSMessage(String action, Object content) {
		this.action = action;
		this.content = content;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
}
