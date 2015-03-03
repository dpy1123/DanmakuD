package com.dd.danmaku.resource.bean;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 弹幕实体类.
 * 成员参考bilibili网站弹幕及下载的弹幕文件的内容设置.
 * @author DD
 * @version v1.0,2013-03-12
 * @version v2.0,2014 修改为bae用的新版本
 */
@Document(collection = "danmu")
public class Danmu implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String id;// 标识
	private String videoId;// 所属视频id号
	private String userId;// 所属用户id号
	private String start;// 弹幕开始时间，00:00:00.60
	private String end;// 结束时间，00:00:04.60
	private String style;// 弹幕类型，Scroll或Static
	private String level;// 弹幕特权等级
	private String color;
	private String font;
	private String text;// 弹幕文字内容
	private String sendTime;// 弹幕发送的时间
	private String clazz;
	private String param;
	
	
	/** default constructor  */
	public Danmu() {

	}
	
	/** full constructor */
	public Danmu(String videoId, String userId, String start, String end, String style, String level, String color, String font, String text, String sendTime, String clazz, String param) {
	    this.videoId = videoId;
	    this.userId = userId;
	    this.start = start;
	    this.end = end;
	    this.style = style;
	    this.level = level;
	    this.color = color;
	    this.font = font;
	    this.text = text;
	    this.sendTime = sendTime;
	    this.clazz = clazz;
	    this.param = param;
	  }


	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getSendTime() {
		return sendTime; 
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
