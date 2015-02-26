<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>分类页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
	<link href="<%=path%>/views/resource/css/list.css" rel="stylesheet" type="text/css">
	<!-- 公共样式，因为要覆盖前面的，所以最后导入 -->
	<link href="<%=path%>/views/css/common.css" rel="stylesheet" type="text/css" />
	<!-- 分页 -->
	

	<script type="text/javascript">
		function toggleLayout(){
			var li = document.getElementsByName('items');
			for ( var i = 0; i < li.length; i++) {
				var oldStyle = li[i].getAttribute('class');
				var newAttr = document.createAttribute('class');
				newAttr.nodeValue = oldStyle == 'list_style1'?'list_style2':'list_style1';
				li[i].setAttributeNode(newAttr);
			}
		}
	</script>
</head>

<body>

	<jsp:include page="${contextPath}/views/header.jsp" flush="true"></jsp:include>


	<div class="gridContainer clearfix">
		<div id="header">
	
		</div>
		
		<div id="title" class="container">
			<span>//${categoryName }</span>
		</div>
		

		<div class="container">
			<div class="row">

			<div id="list" class="col-xs-12 col-sm-9">
				<div id="main_list" class="panel">
					<div class="panel_head">
						<div class="panel_head_text"><a>${categoryName }</a></div>
						<div class="panel_head_more">
							<a id="layout" onclick="toggleLayout()">〓显示方式</a>
						</div>
					</div>
					<div class="panel_content">
						<ul class="video_list">
							<c:forEach items="${resources }" var="resource">  
								<li name="items" class="list_style1">
									<a href="<%=path%>/view.do?resourceId=${resource.id }" target="_blank" class="preview">
										<img src="<%=path%>/getFsFile.do?filename=${resource.previewImg }">
									</a>
									<a target="_blank" class="title">${resource.title }</a>
									<div class="info">
										<a class="gk">播放:<b>${resource.clickCount }</b></a>
										<a class="sc">收藏:<b>${resource.favorCount }</b></a>
										<a class="pl">评分:<b>${resource.score }</b></a>
										<a class="dm">弹幕:<b>${resource.danmuCount }</b></a>
									</div>
									<div class="description">
										${resource.description }
									</div>
									<div class="upper">
										<a href="#" target="_blank"> ${resource.uploaderId }</a>
										UP: ${resource.createDTM }
									</div>
								</li>
							</c:forEach>
						</ul>
						
						<div id="pagination" class="pagination"></div>
						
					</div>
				</div>
			</div>

			<div id="ranking" class="col-xs-6 col-sm-3">
				<div class="ranking_view">
					<div id="caption" class="panel_head">
						<div class="panel_head_text">
							<a>热门视频</a>
						</div>
					</div>
					<div id="center" class="ranking_panel">
						<ul class="ranking_list">
							<c:forEach items="${requestScope.hotResources }" var="item">
								<li>
									<a href="/view.do?videoId=${item.videoId }" title="${item.title } 播放:${item.clickCount } 视频长度:70:40" target="_blank"> 
										<img src="${item.previewImgPath }" />
										<div class="title">${item.title }</div> 
									</a>
									<div class="info">
										<b class="c1">▶${item.clickCount }</b> <b class="c2">▤${item.danmuCount }</b>
									</div>
								</li>
							</c:forEach>
						</ul>

						<div class="more">
							<span>本周播放数最高的视频</span> <a
								href="/list/rank-music.html#hot,1,2013-03-29,2013-04-05"
								target="_blank">更多</a>
						</div>
					</div>
				</div>
			</div>

			</div><!-- end row -->
		</div><!-- end container -->


	</div>
	
	<jsp:include page="${contextPath}/views/footer.jsp" flush="true"></jsp:include>
	
</body>
</html>
