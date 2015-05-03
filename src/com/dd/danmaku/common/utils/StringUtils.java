package com.dd.danmaku.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


/**
 * 字符串处理工具类
 * @author chenlei, dd
 *
 */
public class StringUtils {
	
	public static final String PREFIX = "dd.tv";
	
	private static long code;
	
	public static final String PASSWORD_DIC = "0123456789abcdefghijklmnopqrstuvwxyz";
	
	/**
	 * Convert seconds to MM:SS
	 * @param second
	 * @return
	 */
	public static String parseTimecode(double second){
		String time = null;
		second = Math.floor(second);
		int nb_min = 0;
//		while(second - 60  > 0){
//			second = second - 60;
//			nb_min++;
//		}
		nb_min = (int) (second / 60);
		second = second % 60;
		
		int nb_sec = (int) second;
		String sec = String.valueOf(nb_sec);
		if(nb_sec < 10){
			sec = "0" + sec;
		}
		String min = String.valueOf(nb_min);
		if(nb_min < 10){
			min = "0" + min;
		}	
		time = min+":"+sec;
		return time;
	}
	
	/**
	 * 生成类标识
	 * @param prefix：标识前缀
	 * @return
	 */
	public static String generateID(String prefix) {
		return prefix + "-" + UUID.randomUUID();
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	 public static synchronized String generateOrderNo() {
        code++;
        String str = new SimpleDateFormat("yyyyMMdd").format(new Date());
        long m = Long.valueOf(str) * 1000000000;
        m += code;
        return PREFIX + m;
    }
	
	/**
	 * 字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return null == str;
	}
	
	/**
	 * 长整型是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(Long lon) {
		return null == lon;
	}
	
	/**
	 * 整型是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(Integer integer) {
		return null == integer;
	}
	
	/**
	 * 处理null值
	 * @param str
	 * @return
	 */
	public static String processNull(String str) {
		return isNull(str) ? "":str;
	}
	
	/**
	 * 处理null值
	 * @param l
	 * @return
	 */
	public static Long processNull(Long l) {
		return isNull(l) ? new Long(0) : l;
	}
	
	/**
	 * 处理null值
	 * @param i
	 * @return
	 */
	public static Integer processNull(Integer i) {
		return isNull(i) ? new Integer(0) : i;
	}
	
	/**
	 * 将字符串从iso-8859-1转换为utf-8编码
	 * @param str
	 * @return
	 */
	public static String convertToUTF8(String str) {
		if(isNull(str)) {
			return "";
		}
		
		try {
			return new String(str.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	/**
	 * 将字符串从gbk转换为iso-8859-1编码
	 * @param str
	 * @return
	 */
	public static String convertToISO8859(String str) {
		if(isNull(str)) {
			return "";
		}
		
		try {
			return new String(str.getBytes("gbk"),"iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	/**
	 * 判断是否为空字符串，如：null/''将返回true,' '返回false;
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (null == str || "".equals(str));
	}
	
	/**
	 * 按照指定长度去掉所有html元素
	 * @param input
	 * @param length
	 * @return
	 */
	public static String splitAndFilterString(String input, int length) {   
        if (isEmpty(input.trim())) {   
            return "";   
        }
        
        //去掉所有html元素 
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "").replaceAll("\\s", "").trim();
        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "...";
        }
        return str;
    }
	
	/**
	 * 转换字符串为HTML编码
	 * @param inStr
	 * @return
	 */
	public static String convertToHTMLCode(String inStr) {
		String outStr = "";
	    if(!isNull(inStr)) {
	      inStr = inStr.replaceAll("\n", "<br>");
	      char[] contents = inStr.toCharArray();
	      for (int i = 0; i < contents.length; i++) {
	        char ch = contents[i];
	        if ((ch != '"') && (ch != '<') && (ch != '>') && (ch != '&') && (ch != ' ')) {
	          outStr = String.valueOf(outStr) + ch;
	        } else {
	          if (ch == '"') {
	            outStr = String.valueOf(outStr) + "&quot;";
	          }
	          if (ch == '<')
	          {
	            outStr = String.valueOf(outStr) + "<";
	          }
	          if (ch == '>')
	          {
	            outStr = String.valueOf(outStr) + ">";
	          }
	          if (ch == '&') {
	            outStr = String.valueOf(outStr) + "&amp;";
	          }
	          if (ch == ' ') {
	            outStr = String.valueOf(outStr) + "&nbsp;";
	          }
	        }
	      }
	      contents = null;
	    }
	    inStr = null;

	    return outStr;
	}
	
	/**
	 * URL编码
	 * @param str
	 * @return
	 */
	public static String urlEncode(String str){
		if (isEmpty(str)){
	      return str;
	    }
		StringBuffer sb = new StringBuffer();
		int length = str.length();

		for (int i = 0; i < length; i++) {
			char ch = str.charAt(i);

			if (ch == ' '){
				sb.append('+');
			}else if (((ch >= '0') && (ch <= '9')) || ((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')) || (ch == '_')) {
				sb.append(ch);
			}else if (ch < '\020'){
				sb.append("%0" + Integer.toHexString(ch & 0xFF));
			}else {
				sb.append(String.valueOf('%') + Integer.toHexString(ch & 0xFF));
			}
		}

		str = null;

		return sb.toString();
	}
	
	/**
	 * str1 是否包含 str2
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isContainsStr(String str1,String str2) {
		if(isEmpty(str1) || isEmpty(str2)) {
			return false;
		}
		return str1.indexOf(str2) > 0;
	}
	
	/**
	 * 根据文件名获得后缀
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		if(isEmpty(fileName.trim()) || !isContainsStr(fileName, ".")) {
			return "";
		}
		
		return fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
	}
	
	/**
	* 生成随机密码
	* @param passLenth 生成的密码长度
	* @return 随机密码
	*/
	public static String getPass(int passLenth) {

	   StringBuffer buffer = new StringBuffer(PASSWORD_DIC);
	   StringBuffer sb = new StringBuffer();
	   Random r = new Random();
	   int range = buffer.length();
	   for (int i = 0; i < passLenth; i++) {
	    //生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
	    sb.append(buffer.charAt(r.nextInt(range)));
	   }
	   return sb.toString();
	}
	
	/**
	 * 找回密码时根据用户名和时间戳生成SID
	 * @param username 用户名
	 * @return
	 */
	public static String generateSID(String username,String password,Date date) {
		String url = "key=THREE31390username=" + username + "&time=" + date + "&password=" + password;
		return MD5.getMD5Str(url);
	}
}
