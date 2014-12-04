package com.dd.danmaku;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dd.danmaku.utils.DateUtils;

@Controller
public class VideoController {
	
	/**
     * MIME multipart separation string
     */
    protected static final String mimeSeparation = "DD_MIME_BOUNDARY";
    /**
     * The MIME mappings for this web application, keyed by extension.
     */
    private static final HashMap<String, String> mimeMappings = new HashMap<String, String>();
    
    static{
    	mimeMappings.put("mp4", "video/mp4");
    	mimeMappings.put("exe", "application/octet-stream");
    }
    
    private static int BUFFER_SIZE = 2048;//缓存2k
    
	@Resource
	GridFsTemplate gridFsTemplate;
	
	
	
	
	@RequestMapping("getVideo.do")
	public ResponseEntity<byte[]> downloadStream(String filename, HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("visit "+filename);
		
		HttpHeaders headers = new HttpHeaders();
		byte[] body = null;
		HttpStatus status = HttpStatus.OK;
		
		GridFsResource resource = gridFsTemplate.getResource(filename);
		
		if(!resource.exists()){// 如果请求的静态资源不存在
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		
		headers.set("Content-Type", getMimeType(filename));
		headers.set("Content-Disposition", "attachment; filename=\"" + filename+"\"");

		// Parse range specifier
		Vector<Range> ranges = null;
		ranges = parseRange(request, response, resource);
		// ETag header
		headers.setETag(getETag(resource));
		headers.setLastModified(resource.lastModified());
		
		if ( ( ranges == null || ranges.isEmpty() ) && request.getHeader("Range") == null ) {
			//如果没有分段请求，直接返回整个内容
			headers.setContentLength(resource.contentLength());
			body = getBytes(resource.getInputStream(), 0, resource.contentLength());
		}
		else {
			// Partial content response.
			status = HttpStatus.PARTIAL_CONTENT;

			if (ranges.size() == 1) {
				Range range = (Range) ranges.elementAt(0);
				headers.set("Content-Range", "bytes " + range.start
						+ "-" + range.end + "/" + range.length);
				headers.setContentLength(range.end - range.start + 1);
				//response.sendHeaders();
				body = getBytes(resource.getInputStream(), range.start, (range.end - range.start + 1));
				
			}
		}
			
		return new ResponseEntity<byte[]>(body, headers, status);
	}
	
	
	private byte[] getBytes(InputStream in, long offset, long size) throws IOException {
		//移动到请求下载的部分
		try {
			in.skip(offset);
		} catch (IOException e) {
			System.out.println("InputStream seek失败！"+e.getStackTrace());
		}
		byte[] buffer = new byte[BUFFER_SIZE];
		byte[] result = new byte[(int) size];
		int received = 0;
		int readed = 0;
		while ((readed = in.read(buffer)) != -1 && received < size)  {
			if (received > size - BUFFER_SIZE) {// last read
				readed = (int) (size - received);
			}
			System.arraycopy(buffer, 0, result, received, readed);
			received += readed;
		}
		return result;
	}





	
	public void getVideoStream(String filename,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("visit "+filename);
		
		GridFsResource resource = gridFsTemplate.getResource(filename);
		
		if(!resource.exists()){// 如果请求的静态资源不存在
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// Checking If headers
		if (!checkIfHeaders(request, response, resource)) {
			System.out.println("缓存 ");
			return;
		}
        
		InputStream ins = resource.getInputStream();
		OutputStream out = response.getOutputStream();
		
		//Find content type.
		String contentType = getMimeType(filename);
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setHeader("Connection", "Keep-Alive");
		// Parse range specifier
		Vector<Range> ranges = null;
		ranges = parseRange(request, response, resource);
		
		// ETag header
		response.setHeader("ETag", getETag(resource));

		response.setHeader("Last-Modified", getLastModifidDate(resource));
		
		
		
		if ( ( ranges == null || ranges.isEmpty() ) && request.getHeader("Range") == null ) {
			//如果没有分段请求，直接返回整个内容
			response.setContentLength((int) resource.contentLength());
			//response.sendHeaders();//header由ResponseStream在首次flushbuff的时候发送
			System.out.println("返回整个内容 ");
			if("GET".equals(request.getMethod()))
				downloadData(ins, out);
		}
		else {
			if ((ranges == null) || (ranges.isEmpty()))
				return;
			// Partial content response.
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			
			if (ranges.size() == 1) {
				System.out.println("返回part内容 ");
				Range range = (Range) ranges.elementAt(0);
				response.setHeader("Content-Range", "bytes " + range.start
						+ "-" + range.end + "/" + range.length);
				response.setContentLength((int) (range.end - range.start + 1));
				//response.sendHeaders();
				downloadData(ins, out, range.start, (range.end - range.start + 1));
				
			}else{
				response.setContentType("multipart/byteranges; boundary=" + mimeSeparation);
				PrintWriter writer = response.getWriter();
				while (((Enumeration<?>) ranges).hasMoreElements() ) {
		            Range currentRange = (Range) ((Enumeration<?>) ranges).nextElement();
		            
		            // Writing MIME header.
					writer.println("--" + mimeSeparation);
					if (contentType != null)
						writer.println("Content-Type: " + contentType);
					writer.println("Content-Range: bytes " + currentRange.start
							+ "-" + currentRange.end + "/"
							+ currentRange.length);
					writer.println();

		            // Printing content
		            downloadData(ins, out, currentRange.start, (currentRange.end - currentRange.start + 1));
		        }

				writer.print("--" + mimeSeparation + "--");
			}
			
//			ins.close();
//			out.close();
		}
	}
	
	
	/**
     * Check if the conditions specified in the optional If headers are
     * satisfied.
	 * @throws IOException 
     */
	private boolean checkIfHeaders(HttpServletRequest request, HttpServletResponse response,
			GridFsResource file) throws IOException {
		return checkIfMatch(request, response, file) 
	            && checkIfModifiedSince(request, response, file) 
	            && checkIfNoneMatch(request, response, file) 
	            && checkIfUnmodifiedSince(request, response, file);
	}


	private boolean checkIfUnmodifiedSince(HttpServletRequest request, HttpServletResponse response, GridFsResource file) {
		try {
			long lastModified = file.lastModified();
			long headerValue = -1;
			String header = request.getHeader("If-Unmodified-Since");
			if (header != null)
				headerValue = DateUtils.getDateFromString(header).getTime();
			if (headerValue != -1) {
				if (lastModified > headerValue) {
					// The entity has not been modified since the date
					// specified by the client. This is not an error case.
					response.sendError(
							HttpServletResponse.SC_PRECONDITION_FAILED,
							"PRECONDITION_FAILED");
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean checkIfNoneMatch(HttpServletRequest request, HttpServletResponse response, GridFsResource file) throws IOException {
		String eTag = getETag(file);
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {

			boolean conditionSatisfied = false;

			if (!headerValue.equals("*")) {

				StringTokenizer commaTokenizer = new StringTokenizer(
						headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(eTag))
						conditionSatisfied = true;
				}

			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {

				// For GET and HEAD, we should respond with
				// 304 Not Modified.
				// For every other method, 412 Precondition Failed is sent
				// back.
				if (("GET".equals(request.getMethod()))
						|| ("HEAD".equals(request.getMethod()))) {
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return false;
				} else {
					response.sendError(
							HttpServletResponse.SC_PRECONDITION_FAILED,
							"PRECONDITION_FAILED");
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, GridFsResource file) {
		try {
            long headerValue = -1;
            String header = request.getHeader("If-Modified-Since");
            if(header != null)
            	headerValue = DateUtils.getDateFromString(header).getTime();
            long lastModified = file.lastModified();
            if (headerValue != -1) {
    
                // If an If-None-Match header has been specified, if modified since
                // is ignored.
                if ((request.getHeader("If-None-Match") == null) 
                    && (lastModified <= headerValue + 1000)) {
                    // The entity has not been modified since the date
                    // specified by the client. This is not an error case.
                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return false;
                }
            }
        } catch(Exception e) {
            return false;
        }
        return true;
	}

	private boolean checkIfMatch(HttpServletRequest request, HttpServletResponse response,
			GridFsResource file) throws IOException {
		String eTag = getETag(file);
		String headerValue = request.getHeader("If-Match");
		if (headerValue != null) {
			if (headerValue.indexOf('*') == -1) {

				StringTokenizer commaTokenizer = new StringTokenizer(
						headerValue, ",");
				boolean conditionSatisfied = false;

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(eTag))
						conditionSatisfied = true;
				}

				// If none of the given ETags match, 412 Precodition failed is
				// sent back
				if (!conditionSatisfied) {
					response.sendError(
							HttpServletResponse.SC_PRECONDITION_FAILED,
							"PRECONDITION_FAILED");
					return false;
				}

			}
		}
		return true;
	}

	/**
	 * Parse the range header.
	 * @param request
	 * @param response
	 * @param file 请求文件
	 * @throws IOException
	 */
	private Vector<Range> parseRange(HttpServletRequest request, HttpServletResponse response, GridFsResource file) throws IOException {
		// Checking If-Range
		//IF-Range头部需配合Range，如果没有Range参数，则If-Range会被视为无效。
		//如果If-Range匹配上，那么客户端已经存在的部分是有效的，服务器将返回缺失部分，也就是Range里指定的，然后返回206（Partial content)
		//否则证明客户端的部分已无效（可能已经更改），那么服务器将整个实体内容全部返回给客户端，同时返回200OK
        String headerValue = request.getHeader("If-Range");
        if (headerValue != null) {

            String eTag = getETag(file);
            long lastModified = file.lastModified();

            Date date = null;

            // Parsing the HTTP Date
			try {
				date = DateUtils.getDateFromString(headerValue);
			} catch (Exception e) {
				;
			}

            if (date == null) {

                // If the ETag the client gave does not match the entity
                // etag, then the entire entity is returned.
                if (!eTag.equals(headerValue.trim())){
                	return null;
                }

            } else {

                // If the timestamp of the entity the client got is older than
                // the last modification date of the entity, the entire entity
                // is returned.
                if (lastModified > (date.getTime() + 1000))
                    return null;

            }

        }
        
		long fileLength = file.contentLength();
        if (fileLength == 0)
            return null;
        
        // Retrieving the range header 
        String rangeHeader = request.getHeader("Range");
        if (rangeHeader == null)
            return null;
        
        //如果range字段不符合“Range：bytes=”的形式
        if (!rangeHeader.startsWith("bytes")) {
            response.addHeader("Content-Range", "bytes */" + fileLength);
            response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
            return null;
        }
        
        rangeHeader = rangeHeader.substring(6);
        
        Vector<Range> result = new Vector<Range>();
        StringTokenizer commaTokenizer = new StringTokenizer(rangeHeader, ",");
        // Parsing the range list.
        // Range可能有很多段请求，如Range：bytes=0-500,1000-1500
        while (commaTokenizer.hasMoreTokens()) {
        	String range = commaTokenizer.nextToken();
			Range currentRange = new Range();
			currentRange.length = fileLength;
			
			int dashPos = range.indexOf('-');
			if (dashPos == -1) { //如果range字段不包含“Range：bytes=x-x”
                response.addHeader("Content-Range", "bytes */" + fileLength);
                response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return null;
            }
			try {
				if (dashPos == 0) {//表示最后500个字节：bytes=-500
                    long offset = Long.parseLong(range);
                    currentRange.start = fileLength + offset;
                    currentRange.end = fileLength - 1;
	            }
				currentRange.start = Long.parseLong(range.substring(0, dashPos));
                if (dashPos < range.length() - 1)//表示第二个500字节：bytes=500-999
                    currentRange.end = Long.parseLong(range.substring(dashPos + 1, range.length()));
                else//表示500字节以后的范围：bytes=500-
                    currentRange.end = fileLength - 1;
			} catch (NumberFormatException e) {
				response.addHeader("Content-Range", "bytes */" + fileLength);
				response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
				return null;
			}
			
			if (!currentRange.validate()) {
                response.addHeader("Content-Range", "bytes */" + fileLength);
                response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return null;
            }
			
			result.addElement(currentRange);
        }
        
		return result;
	}

    /**
     * Get the ETag associated with a file.
     * @param File object
     * @throws IOException 
     */
	private String getETag(GridFsResource file) throws IOException {
		return "W/\"" + file.contentLength() + "-" + file.lastModified() + "\"";
	}
    
	@SuppressWarnings("deprecation")
	private String getLastModifidDate(GridFsResource file) throws IOException {
		String date = null;
		long times = file.lastModified();
		if(times == 0L){
			date = DateUtils.getCurrentDate().toGMTString();
		}else{
			date = new Date(times).toGMTString();
		}
		return date;
	}
	
    /**
     * Return the MIME type of the specified file, or <code>null</code> if
     * the MIME type cannot be determined.
     *
     * @param file Filename for which to identify a MIME type
     */
    public String getMimeType(String file) {
        if (file == null)
            return (null);
        int period = file.lastIndexOf(".");
        if (period < 0)
            return (null);
        String extension = file.substring(period + 1);
        if (extension.length() < 1)
            return (null);
        return (mimeMappings.get(extension));
    }
	
    
    /**
	 * 
	 * @param in 待下载的数据流
	 * @param out 输出到的数据流
	 * @param offset
	 * @param size
     * @throws IOException 
	 */
	public void downloadData(InputStream in, OutputStream out, long offset, long size) throws IOException {
		//移动到请求下载的部分
		try {
			in.skip(offset);
		} catch (IOException e) {
			System.out.println("InputStream seek失败！"+e.getStackTrace());
		}
		//保存文件
//		int readSize = 0;
//		long downloadSize = 0;
		
		long bytesToRead = size;
		byte[] buffer = new byte[BUFFER_SIZE];
		int len = buffer.length;
		while ((bytesToRead > 0) && (len >= buffer.length)) {
			try {
				len = in.read(buffer);
				if (bytesToRead >= len) {
					out.write(buffer, 0, len);
					bytesToRead -= len;
				} else {
					out.write(buffer, 0, (int) bytesToRead);
					bytesToRead = 0;
				}
			} catch (IOException e) {
//				e.printStackTrace();//忽略应客户端取消导致的Software caused connection abort: socket write error 的错误 
				len = -1;
			}
			if (len < buffer.length)
				break;
			out.flush();
		}
	}
    
    public void downloadData(InputStream in, OutputStream out) throws IOException {
		byte buffer[] = new byte[BUFFER_SIZE];
		int len = buffer.length;
		while (true) {
			try {
				len = in.read(buffer);
				if (len == -1)
					break;
				out.write(buffer, 0, len);
			} catch (IOException e) {
//				e.printStackTrace();
				len = -1;
				break;
			}
			out.flush();
		}
	}
    
    
	// ------------------------------------------------------ Range Inner Class
	private class Range {

		public long start;
		public long end;
		public long length;

		/**
		 * Validate range.
		 */
		public boolean validate() {
			if (end >= length)
				end = length - 1;
			return ((start >= 0) && (end >= 0) && (start <= end) && (length > 0));
		}

	}
}
