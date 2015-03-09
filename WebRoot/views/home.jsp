<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>

	<title>DD.tv</title>
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
	<link href="<%=path%>/views/css/bannerAD.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<%=path%>/views/js/jquery.easing-1.3.js"></script>
	<script type="text/javascript" src="<%=path%>/views/js/jquery.iosslider.min.js"></script>
	<script type="text/javascript" src="<%=path%>/views/js/bannerAD.js"></script>
	<script type="text/javascript" src="<%=path%>/views/js/index.js"></script>
	
	<script type="text/javascript" src="<%=path%>/views/js/common.js"></script>
	<!-- 公共样式，因为要覆盖前面的，所以最后导入 -->
	<link rel="stylesheet" href="<%=path%>/views/css/common.css" type="text/css" /> 
	<link rel="stylesheet" href="<%=path%>/views/css/animate.css" type="text/css" />


</head>

<body>

	<jsp:include page="${contextPath}/views/header.jsp" flush="true"></jsp:include>


	<div class="gridContainer clearfix">
		<div id="bannerAD">
			<div class='sliderContainer container'>
				<!-- slider container -->
				<div class='iosSlider'>
					<!-- slider -->
					<div class='slider'>
						<!-- to be filled dynamically in bannerAD.js -->
					</div>
					<div class='slideSelectors'>
						<div class='item selected'></div>
						<div class='item'></div>
						<div class='item'></div>
						<div class='item'></div>
						<div class='item'></div>
					</div>
					<div class='scrollbarContainer'></div>
				</div>
			</div>
		</div>

		<div id="title" class="container">
			<span>//推荐视频</span>
		</div>
		

		<div class="container">
		<div class="row">

		<div id="categories" class="col-xs-12 col-sm-9">
			<c:forEach items="${resources}" var="resource">
				<div id="categoryName" class="panel" >
					<div id="caption" class="panel_head">
						<div class="panel_head_text">
							<a>${resource.key.name }</a>
						</div>
						<div class="panel_head_more">
							<a href="<%=path%>/channel.do?categoryName=${resource.key.name }" target="_blank">查看更多</a>
						</div>
					</div>
					<div id="items" class="panel_content">
						<c:forEach items="${resource.value}" var="item" >
							<div id="item" class="he-wrap panel_content_item" >
								<a href="<%=path%>/view.do?resourceId=${item.id }" target="_blank"> 
									<img src="<%=path%>/getFsFile.do?filename=${item.previewImg }" />
									<div class="panel_content_item_hud info">
										<b id="duration">${item.duration }</b> 
										<i id="score">${item.score }</i>
									</div>
									<div class="panel_content_item_title">${item.title }</div>
								</a>
								<div class="ext_info">
									<b id="playTimes" class="c1">${item.clickCount }点击</b> 
									<b id="danmuCount" class="c2">${item.danmuCount }弹幕</b>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
			<script>
				//更新每个视频的duration的显示方式
				$('b[id=duration]').each(function(index, domElement) {
					var sec = $(domElement).text();
					$(this).text(parseTimeCode(sec));
				});
			</script>
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
						<!--<c:forEach items="${requestScope.hotResources }" var="item">-->
							<li>
								<a href="/view.do?videoId=${item.videoId }" title="标题 播放:点击数 视频长度:时长" target="_blank"> 
									<img src="${item.previewImgPath }" />
									<div class="title">标题</div> 
								</a>
								<div class="info">
									<b class="c1">?点击数</b> <b class="c2">?弹幕数</b>
								</div>
							</li>
						<!--</c:forEach>-->
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
					<!--<c:forEach items="${requestScope.newResources }" var="item">-->
						<li>
							<a href="/view.do?videoId=${item.videoId }" title="标题" target="_blank">
								<div class="t">标题</div> 
							</a> 
							<i>${item.uploadDTM }</i>
						</li>
					<!--</c:forEach>-->
				</ul>
			</div>
		</div>
		</div><!-- end row -->
		</div><!-- end container -->
		<div id="footer_push"><!-- not put anything here --></div>
	</div>
	
	
	<jsp:include page="${contextPath}/views/footer.jsp" flush="true"></jsp:include>


	<div id="go-to-top" title="Go to Top">
		<span class="glyphicon glyphicon-chevron-up"></span>
	</div>
	<script type="text/javascript">
		$(window).scroll(function(){
			//如果滚轮向下移动了，则显示“回到顶部”按钮
			if(document.body.scrollTop > 0)
				$("#go-to-top").css("bottom","20px");
			else	
				$("#go-to-top").css("bottom","-100px");
			//当滚动到每个panel_content的时候，显示动画
			$(".panel_content").each(function(){
				if(document.body.scrollTop + window.innerHeight - $(this).offset().top > 0) {
					$(this).find(".panel_content_item").each(function(i){
						$(this).addClass(" animated "+(i%2==1?"fadeInLeft":"fadeInRight"))
					});
				}
			});
		});
		//点击“回到顶部”按钮的处理
		$("#go-to-top").click(function(){
			$('body').animate({scrollTop: '0px'}, 500,'easeOutQuad');
			//document.body.scrollTop = 0;
		});
		
	</script>
</body>
</html>
