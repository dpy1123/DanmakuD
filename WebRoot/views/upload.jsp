<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>投稿</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- 公共 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%=path%>/views/css/bootstrap.min.css" />
<script type="text/javascript" src="<%=path%>/views/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/views/js/bootstrap.min.js"></script>
<!-- 独特 -->
<link rel="stylesheet" type="text/css"
	href="<%=path%>/views/resource/css/upload1.css" />
<!-- 公共样式，因为要覆盖前面的，所以最后导入 -->
<link rel="stylesheet" href="<%=path%>/views/css/common.css" type="text/css" />
<!-- 上传 -->
<!-- blueimp Gallery styles -->
<link rel="stylesheet" href="<%=path%>/views/css/jQuery-File-Upload/blueimp-gallery.min.css">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="<%=path%>/views/css/jQuery-File-Upload/jquery.fileupload.css">
<link rel="stylesheet" href="<%=path%>/views/css/jQuery-File-Upload/jquery.fileupload-ui.css">
</head>

<body>
	<div class="gridContainer clearfix">
		<div id="header">
			<%-- 			<jsp:include page="${contextPath}/common/header.jsp" flush="true"></jsp:include> --%>
		</div>

		<div class="upload_container container">

			<div class="row">
				<div id="title">
					<span>//上传新视频</span>
				</div>
			</div>

			<div class="row">
				<div class="content-page-topsider">
					<i class="icon-notice" style="margin-right: 5px;"></i>请正确填写投稿相关信息。
				</div>
			</div>


			<!-- 	<form action="onUpload.do" method="post"> -->

			<!-- The file upload form used as target for the file upload widget -->
			<form id="fileupload" action="<%=path%>/uploadResource.do" method="POST"
				enctype="multipart/form-data">

				<div class="row item">
					<div class="col-xs-12 col-sm-5 left">
						<b>标题</b><span>*</span>
					</div>
					<div class="col-xs-12 col-sm-7 right">
						<input type="text" name="title" class="input">
					</div>
				</div>
				<div class="row item">
					<div class="col-xs-12 col-sm-5 left">
						<b>标签TAG</b><span>*</span><br />
						<span class="tips">视频关键字，多个请用逗号分开</span>
					</div>
					<div class="col-xs-12 col-sm-7 right">
						<input type="text" name="tag" class="input">
					</div>
				</div>
				<div class="row item">
					<div class="col-xs-12 col-sm-5 left">
						<b>隶属栏目</b><span>*</span>
					</div>
					<div class="col-xs-12 col-sm-7 right">
						<select name="category">
							<option value="0" selected disabled="disabled">请选择栏目...</option>
							<c:forEach items="${requestScope.categories}" var="category"
								varStatus="i">
								<optgroup label=${category.key.name }>
									<c:forEach items="${category.value}" var="subcategory"
										varStatus="j">
										<option value=${subcategory.name }>${subcategory.name}</option>
									</c:forEach>
								</optgroup>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row item">
					<div class="col-xs-12 col-sm-5 left">
						<b>投稿类型</b><span>*</span>
					</div>
					<div class="col-xs-12 col-sm-7 right">
						<input type="radio" name="type" value="copy" checked="checked">
						<label>转载</label> <input type="radio" name="type" value="original">
						<label>原创</label>
					</div>
				</div>
				<div class="row item">
					<div class="col-xs-12 col-sm-5 left">
						<b>简介</b><span>*</span>
					</div>
					<div class="col-xs-12 col-sm-7 right">
						<textarea name="description" style="width: 380px; height: 70px"
							class="input"></textarea>
					</div>
				</div>
				<div class="row item">
					<div class="col-xs-12 col-sm-5 left">
						<b>出处</b><span>*</span>
					</div>
					<div class="col-xs-12 col-sm-7 right">
						<input type="text" name="source" class="input">
					</div>
				</div>
				<div class="row item">
					<div class="col-xs-12 col-sm-5 left">
						<b>封面图</b><span>*</span>
					</div>
					<div class="col-xs-12 col-sm-7 right">
						<input type="button" value="上传文件" ></input>
					</div>
				</div>

				<div class="row item">
					<div class="col-xs-12 col-sm-5 left">
						<b>视频</b><span>*</span>
					</div>
					<div class="col-xs-12 col-sm-7 right">
						<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
						<div class="row fileupload-buttonbar">
							<div class="col-lg-7">
								<!-- The fileinput-button span is used to style the file input field as button -->
								<span class="btn btn-success fileinput-button"> <i
									class="glyphicon glyphicon-plus"></i> <span>Add files...</span>
									<input type="file" name="files[]" multiple>
								</span>

								<!-- The global file processing state -->
								<span class="fileupload-process"></span>
							</div>
							<!-- The global progress state -->
							<div class="col-lg-5 fileupload-progress fade">
								<!-- The global progress bar -->
								<div class="progress progress-striped active" role="progressbar"
									aria-valuemin="0" aria-valuemax="100">
									<div class="progress-bar progress-bar-success"
										style="width: 0%;"></div>
								</div>
								<!-- The extended global progress state -->
								<div class="progress-extended">&nbsp;</div>
							</div>
						</div>
						<!-- The table listing the files available for upload/download -->
						<table role="presentation" class="table table-striped">
							<tbody class="files"></tbody>
						</table>

						<div id="uploadedFiles"></div>
					</div>
				</div>

				<div class="row item">
					<span>一些说明</span>
				</div>
				<div class="row">
					<button id="next_step" type="submit" class="button">完成</button>
					<button type="reset" class="button">重置</button>
				</div>
			</form>

		</div>


		<div id="footer">
			<%-- 		<jsp:include page="${contextPath}/common/footer.jsp" flush="true"></jsp:include> --%>
		</div>
	</div>
	
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <!--<input type="checkbox" name="delete" value="1" class="toggle">-->
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<script src="<%=path%>/views/js/jquery-1.11.1.min.js"></script>
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="<%=path%>/views/js/jQuery-File-Upload/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="<%=path%>/views/js/jQuery-File-Upload/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="<%=path%>/views/js/jQuery-File-Upload/load-image.all.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="<%=path%>/views/js/jQuery-File-Upload/canvas-to-blob.min.js"></script>
<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
<script src="<%=path%>/views/js/bootstrap.min.js"></script>
<!-- blueimp Gallery script -->
<script src="<%=path%>/views/js/jQuery-File-Upload/jquery.blueimp-gallery.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="<%=path%>/views/js/jQuery-File-Upload/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="<%=path%>/views/js/jQuery-File-Upload/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="<%=path%>/views/js/jQuery-File-Upload/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="<%=path%>/views/js/jQuery-File-Upload/jquery.fileupload-image.js"></script>
<!-- The File Upload audio preview plugin -->
<script src="<%=path%>/views/js/jQuery-File-Upload/jquery.fileupload-audio.js"></script>
<!-- The File Upload video preview plugin -->
<script src="<%=path%>/views/js/jQuery-File-Upload/jquery.fileupload-video.js"></script>
<!-- The File Upload validation plugin -->
<script src="<%=path%>/views/js/jQuery-File-Upload/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script src="<%=path%>/views/js/jQuery-File-Upload/jquery.fileupload-ui.js"></script>
<!-- The main application script -->
<script >
	$('#fileupload')
	.fileupload({
		url : '<%=path%>/uploadVideo.do',
		//sequentialUploads: true
	}).bind('fileuploaddone', function(e, data) {
		//data.result中保存的是上传成功后服务端返回的json
		$(data.result.files).each(
			function(index, item){
				$('#uploadedFiles').append('<input type="hidden" name="videoId" value="'+ item.id +'">');
			}		
		);
	});
	
	
	$('#fileupload').submit(function() {
		if($('input[name=videoId]').length>0)//如果有上传文件才提交form
			return true;
		else
			return false;
	})
</script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="js/cors/jquery.xdr-transport.js"></script>
<![endif]-->	
</body>
</html>
