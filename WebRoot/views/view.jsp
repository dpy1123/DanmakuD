<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0.1 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<title>view page</title>
	
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
	<link href="<%=path%>/views/resource/css/view.css" rel="stylesheet" type="text/css">
	<!-- 公共样式，因为要覆盖前面的，所以最后导入 -->
	<link rel="stylesheet" href="<%=path%>/views/css/common.css" type="text/css" />
	<!-- 弹幕 -->
	<script type="text/javascript" src="<%=path%>/views/ddplayer/ddplayer.min.js"></script>
	<link rel="stylesheet" href="<%=path%>/views/ddplayer/Playr-master/playr.css" type="text/css">
	<script type="text/javascript" src="<%=path%>/views/ddplayer/Playr-master/playr.js"></script>
	<!-- colorpicker -->
	<link rel="stylesheet" href="<%=path%>/views/colorpicker/css/colorpicker.css" type="text/css" />
    <link rel="stylesheet" href="<%=path%>/views/colorpicker/css/layout.css" type="text/css" />
	<script type="text/javascript" src="<%=path%>/views/colorpicker/js/colorpicker.js"></script>
	
	<script type="text/javascript">
		window.addEventListener('DOMContentLoaded',function(){
			//因为chrome和firefox对于video加载和load方法调用的时机不同，在此做fix。
			load();	
		});
	
		var player;
		function load() {
			var v = document.querySelector("video");
			new Playr("dd", v)
			player = new DD.Player("dd", v);
			var url = "<%=path%>/getDanmaku.do?vid=${videoId }";
			player.init("canvas", url);	
		
			$('#colorSelector2').ColorPicker({
				color: '#ffffff',
				onShow: function (colpkr) {
					$(colpkr).fadeIn(500);
					return false;
				},
				onHide: function (colpkr) {
					$(colpkr).fadeOut(500);
					return false;
				},
				onChange: function (hsb, hex, rgb) {
					$('#colorSelector2 div').css('backgroundColor', '#' + hex);
					$('#color').val('#' + hex);
				},
				onSubmit: function(hsb, hex, rgb, el) {
					$('#color').val('#' + hex);
				}
			});
			
			$("#send").click(function(){
				var color = $("#color").val();
				var style = $("input[name='style']:checked").val();
				var text = $("#text").val();
				if(text.length <= 255)
					player.sendDanmus("<%=path%>/sendDanmaku.do","${videoId }","system",text,style,color);
				$("#text").val("");
			});
			

		}

	</script>
</head>

<body>

	<jsp:include page="${contextPath}/views/header.jsp" flush="true"></jsp:include>


	<div class="gridContainer clearfix">
		<div class="container">
			<div class="row">
				<div id="title" class="col-xs-12 col-sm-6 col-md-8">
					<div class="title_panel">
						<h2>${resource.title }</h2>
				        <div class="navi_info">
				            <a href="<%=path%>/views">主页</a>
				            &gt;
				            <span><a href="channel.do?categoryName=${mainCategory }">${mainCategoryDisplayName }</a></span> 
				            &gt; 
				            <span><a href="list.do?categoryName=${subCategory }">${subCategoryDisplayName }</a></span> 
				            &gt; 
				            <time><i>${resource.createDTM }</i></time>
				        </div>
				        <div class="play_info">
						            播放:<i id="dianji">${resource.clickCount }</i>
						            收藏:<i id="stow_count">${resource.favorCount }</i>
						            评分:<i><span id="v_ctimes" title="硬币数量">${resource.score }</span></i>
				        </div>
					</div>
				</div>
				<div id="upper" class="col-xs-12 col-sm-6 col-md-4">
					<div class="upper_panel media">
						<div class="face media-left">
							<img src="<%=path%>/views/images/myface.jpg">
						</div>
						<div class="info  media-body">
							<div class="name media-heading" id="upper_name">
								<a target="_blank">dpy1123</a>
							</div>
							<div class="sign">这个人很懒，什么都没有写……简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长简介可以很长</div>
						</div>
					</div>
				</div>
			</div><!-- end row -->
			
			<div id="video">
				<div class="video_panel">
					<video class="playr_video" >
						<source src="${videoUrl }" type="video/mp4"></source> 
					</video>
				</div>
				<div id="sendPanel" class="row">
					<div class="col-xs-1 col-md-offset-2 col-lg-1 col-lg-offset-3">
						<div id="colorSelector2"><div></div></div>
						<input id="color" type="hidden" value="#ffffff">
					</div>
					<div class="col-xs-6 col-sm-4 col-lg-2" style="font-size: 14px;margin-top: 6px;">
						<label>样式：</label>
						<input type="radio" name="style" checked value="Scroll"/><label>Scroll</label>
						<input type="radio" name="style" value="Static"/><label>Static</label>
					</div>
					<div class="col-xs-8 col-sm-4 col-lg-3" style="font-size: 14px;margin-top: 4px;">
						<input id="text" type="text">
						<button id="send">Send</button>
					</div>
				</div>	
			</div>
			
			<div id="info">
				<div class="info_panel">
					<div class="tag">
						<b>TAG:</b>
						<ul>
							<c:forEach items="${resource.tags }" var="tag">
								<li>
									<a href="search.do?keyWords=${tag.name }&type=tag&categoryName=&order=">
										<c:if test="${tag.isSpecialEdition }"><span class="sp">专</span></c:if>${tag.name }
									</a>
								</li>
							</c:forEach>
						</ul>
						<span class="new_tag">
							<a href="javascript:;" onclick="">增加新TAG</a>
						</span>
					</div>
	
					<div class="description">
						${resource.description }
					</div>
				</div>
			</div>
				
		</div><!-- end container -->
	</div>	
	
	<jsp:include page="${contextPath}/views/footer.jsp" flush="true"></jsp:include>
	
</body>
</html>
