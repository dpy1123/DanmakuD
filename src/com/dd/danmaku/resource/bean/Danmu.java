package com.dd.danmaku.resource.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 弹幕实体类.
 * 成员参考bilibili网站弹幕及下载的弹幕文件的内容设置.
 * @author DD
 * @version v1.0,2013-03-12
 */
@Document(collection = "danmu")
public class Danmu {
	
	// Fields
	private String id;//标识
	private String videoId;//所属视频id号
	private String userId;//所属用户id号
	private String start;//弹幕开始时间，00:00:00.60
	private String end;//结束时间，00:00:04.60
	private String style;//弹幕类型，Scroll或Static
	private String level;//弹幕特权等级
	private String effect;//move:270,42,0,42;color:#FFFFFF或pos:270,42;color:rgb(0,255,0)
	private String text;//弹幕文字内容
	private String sendTime;//弹幕发送的时间
	
	
	/** default constructor  */
	public Danmu() {

	}
	
	/** full constructor */
	public Danmu(String id, String videoId, String userId, String start,
			String end, String style, String level, String effect, String text,
			String sendTime) {
		this.id = id;
		this.videoId = videoId;
		this.userId = userId;
		this.start = start;
		this.end = end;
		this.style = style;
		this.level = level;
		this.effect = effect;
		this.text = text;
		this.sendTime = sendTime;
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
	
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
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
}
