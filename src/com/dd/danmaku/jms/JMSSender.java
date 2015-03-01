package com.dd.danmaku.jms;

import javax.annotation.Resource;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JMSSender {
	
	@Resource
    protected JmsTemplate jmsTemplate;  
      
	/**
	 * 发送异步消息到消息队列
	 * @param jmsMsg 消息内容
	 */
    public void sendMessage(JMSMessage jmsMsg){  
        jmsTemplate.convertAndSend(jmsMsg);  
    }  
    
}
