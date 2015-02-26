<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
	<title>频道页面</title>
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
	<link href="<%=path%>/views/css/index.css" rel="stylesheet" type="text/css">
	<!-- 公共样式，因为要覆盖前面的，所以最后导入 -->
	<link rel="stylesheet" href="<%=path%>/views/css/common.css" type="text/css" />
</head>

<body>

<jsp:include page="${contextPath}/views/header.jsp" flush="true"></jsp:include>

<div class="gridContainer clearfix">
		
	<div id="title" class="container">
		<span>//${categoryName }</span>
	</div>
		
 	<div class="container">
		<div class="row">

		<div id="categories" class="col-xs-12 col-sm-9">
			<c:forEach items="${resources}" var="subCategory">
				<div id="categoryName" class="panel">
					<div id="caption" class="panel_head">
						<div class="panel_head_text">
							<a>${subCategory.key.name }</a>
							<span class="sub_addnew">24小时新增投稿：
								<!-- <c:forEach items="${requestScope.dailyNew }" var="dailyNew">-->
									<!-- <c:if test="${dailyNew.key == subCategory.key.displayName }">-->
										<!-- ${dailyNew.value } -->
									<!-- </c:if>-->
								<!-- </c:forEach>-->
							</span>
						</div>
						<div class="panel_head_more">
							<a href="<%=path%>/list.do?categoryName=${subCategory.key.name }" target="_blank">查看更多</a>
						</div>
					</div>
					<div id="items" class="panel_content">
						<c:forEach items="${subCategory.value}" var="item" >
							<div id="item" class="panel_content_item">
								<a href="<%=path%>/view.do?resourceId=${item.id }" target="_blank"> 
									<img src="<%=path%>/getFsFile.do?filename=${item.previewImg }" />
									<div class="panel_content_item_hud info">
										<b id="duration">${item.duration }</b> 
										<i id="score">${item.score }</i>
									</div>
									<div class="panel_content_item_title">${item.title }</div>
								</a>
								<div class="ext_info">
									<b id="playTimes" class="c1">▶${item.clickCount }</b> 
									<b id="danmuCount" class="c2">▤${item.danmuCount }</b>
								</div>
							</div>

						</c:forEach>
					</div>
				</div>
			</c:forEach>
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
						<!-- <c:forEach items="${requestScope.hotResources }" var="item">-->
							<li>
								<a href="view.do?videoId=${item.videoId }" title="${item.title } 播放:${item.clickCount } 视频长度:${item.duration }" target="_blank"> 
									<img src="${item.previewImgPath }" />
									<div class="title">${item.title }</div> 
								</a>
								<div class="info">
									<b class="c1">▶${item.clickCount }</b> <b class="c2">▤${item.danmuCount }</b>
								</div>
							</li>
						<!-- </c:forEach>-->
					</ul>

					<div class="more">
						<span>本周播放数最高的视频</span> <a
							href="/list/rank-music.html#hot,1,2013-03-29,2013-04-05"
							target="_blank">更多</a>
					</div>
				</div>
			</div>
		</div>

		<div id="recent" class="col-xs-6 col-sm-3">
			<div id="recentUpload" class="recent_list">
				<div id="caption" class="panel_head">
					<div class="panel_head_text">
						<a>最新投稿</a>
					</div>
				</div>
				<ul>
					<!-- <c:forEach items="${requestScope.newResources }" var="item">-->
						<li>
							<a href="view.do?videoId=${item.videoId }" title="${item.title }" target="_blank">
								<div class="t">${item.title }</div> 
							</a> 
							<i>${item.uploadDTM }</i>
						</li>
					<!-- </c:forEach>-->
				</ul>
			</div>
		</div>
		
		</div><!-- end row -->
	</div><!-- end container -->
</div>

<jsp:include page="${contextPath}/views/footer.jsp" flush="true"></jsp:include>

</body>
</html>
