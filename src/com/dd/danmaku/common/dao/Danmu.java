package com.dd.danmaku.common.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="danmu",catalog="RsQgBkDkaxryZXpAXeYY")
public class Danmu
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id;
  private String videoId;
  private String userId;
  private String start;
  private String end;
  private String style;
  private String level;
  private String color;
  private String font;
  private String text;
  private String sendTime;
  private String clazz;
  private String param;

  public Danmu()
  {
  }

  public Danmu(String id, String videoId, String userId, String start, String end, String style, String level, String color, String font, String text, String sendTime, String clazz, String param) {
    this.id = id;
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
  @Column(name = "id", nullable = false, length = 50)
  public String getId() { return this.id; }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getVideoId() {
    return this.videoId;
  }

  public void setVideoId(String videoId) {
    this.videoId = videoId;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getStart() {
    return this.start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return this.end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public String getStyle() {
    return this.style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public String getLevel() {
    return this.level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getColor() {
    return this.color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getFont() {
    return this.font;
  }

  public void setFont(String font) {
    this.font = font;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getSendTime() {
    return this.sendTime;
  }

  public void setSendTime(String sendTime) {
    this.sendTime = sendTime;
  }

  @Column(name = "clazz", nullable = true, columnDefinition = "text", length = 16777216)
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