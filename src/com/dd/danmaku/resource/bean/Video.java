package com.dd.danmaku.resource.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "video")
public class Video {
	@Id
	private String id;
	private String name;
	private long size;
	private long duration;
	private String status;
	
	public Video(String name) {
		super();
		this.name = name;
	}
	
	
}
