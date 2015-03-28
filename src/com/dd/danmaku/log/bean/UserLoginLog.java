package com.dd.danmaku.log.bean;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userLoginLog")
public class UserLoginLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
}
