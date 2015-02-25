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
	<!-- 公共样式，因为要覆盖前面的，所以最后导入 -->
	<link rel="stylesheet" href="<%=path%>/views/css/common.css" type="text/css" /> 
	<link rel="stylesheet" href="<%=path%>/views/css/animate.css" type="text/css" />

</head>

<body>





<header> 
	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<div class="container">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header ">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#menu">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">DD.TV</a>
				</div>
	
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse " id="menu">
	
<!-- 					<ul class="nav navbar-nav"> -->
<!-- 						<li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li> -->
<!-- 						<li><a href="#">动画<span class="badge">54</span></a></li> -->
<!-- 						<li class="dropdown"><a href="#" class="dropdown-toggle" -->
<!-- 							data-toggle="dropdown" role="button" aria-expanded="false">音乐<span -->
<!-- 								class="badge">4</span><span class="caret"></span></a> -->
<!-- 							<ul class="customs-menu dropdown-menu" role="menu"> -->
<!-- 								<li><a href="#">1</a></li> -->
<!-- 								<li><a href="#">2 action</a></li> -->
<!-- 								<li><a href="#">3 else here</a></li> -->
<!-- 								<li class="divider"></li> -->
<!-- 								<li><a href="#">11 link</a></li> -->
<!-- 								<li class="divider"></li> -->
<!-- 								<li><a href="#">22 more separated link</a></li> -->
<!-- 							</ul></li> -->
<!-- 					</ul> -->
	
	
					<ul class="nav navbar-nav navbar-left ">
						<c:forEach items="${categories}" var="category">
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
									${category.key.name }<span class="caret"></span></a>
								<ul class="customs-menu dropdown-menu" role="menu">
									<c:forEach items="${category.value}" var="subcategory" >
										<li><a href="#">${subcategory.name}</a></li>
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
					</ul>

					<ul class="nav navbar-nav navbar-right ">
						<li><a href="<%=path%>/preUpload.do"> <span id="upload_icon"
								class="glyphicon glyphicon-upload"></span> 投稿
						</a></li>
						<li>
							<form class="navbar-form  region-search" role="search">
								<span id="search_icon" class="glyphicon glyphicon-search"
									style="line-height: 2;"></span>
								<div id="search_panel" class="form-group" style="display: none;">
									<input type="text" class="form-control " placeholder="Search"
										style="display: inline; width: auto;">
									<button type="submit" class="btn btn-default ">Submit</button>
								</div>
							</form> 
							<script type="text/javascript">
								jQuery(document).ready(function($) {
									$('#search_icon').click(function() {
										$(this).toggle();
										$('#search_panel').toggle();
									})
	
								})
							</script>
						</li>
					</ul>
			</div><!-- /.navbar-collapse -->
			</div>
		</div><!-- /.container-fluid --> 
	</nav>
</header>



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
	
	<footer class="footer">
		<div class="container">
			<p class="text-muted">© 2013-2014 <span>DD</span> present.</p>
		</div>

		<div id="quickpannel" class="block quickpannel">
			  <div class="inner"></div>
			  <div class="overlay"></div>
			  <h2 class="title  no-subtitle">消息动态</h2>
				
			  <div class="content">
				<span class="quickpannel_toggle">
					<img class="icon_face" src="images/myface_s.jpg"/>
				</span>
				<div class="dexp_settings">
					
					<!-- Nav tabs -->
					<ul class="nav nav-tabs" role="tablist">
					  <li role="presentation" class="active"><a href="#home" role="tab" data-toggle="tab">动态</a></li>
					  <li role="presentation"><a href="#profile" role="tab" data-toggle="tab">历史</a></li>
					  <li role="presentation"><a href="#messages" role="tab" data-toggle="tab">收藏</a></li>
					  <li role="presentation"><a href="#settings" role="tab" data-toggle="tab">设置</a></li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
					  <div role="tabpanel" class="tab-pane active" id="home">
						<ul class="dyn_list" >
							<li class="d-data" >
								<div class="preview">
									<img src="images/myface_s.jpg">
								</div>
								<div class="r">
									<div class="title">
										<a href="" card="Zz白日梦zZ" target="_blank">Zz白日梦zZ</a><span>发表了评论</span>
									</div>
									<div class="info">
										<a href="" target="_blank" title="LoveLive 同人弦樂四重奏">求求求求求谱</a>
									</div>
								</div>
							</li>
							<li class="loading f">(´・ω・｀)没有更多信息</li>
						</ul>
						<div class="wnd_bottom">
							<div class="r-l"><a class="read-more" href="">查看全部</a><div class="num" id="dynamic_num"></div><div class="check-all no-select"></div></div>
						</div>
					  </div>

					  <div role="tabpanel" class="tab-pane" id="profile">
						<ul class="history">
							<li class="timeline top">
								<span class="dot"></span>
								<span class="d-line"></span>
								<span class="date">2014-11-23 23:34:03</span>
							</li>
							<li><a href="" target="_blank" title="【泪目向Flower Dance】鸠子：中二是什么？我不明白啊！">【泪目向Flower Dance】鸠子：中二是什么？我不明白啊！</a></li>
							<li><a href="" target="_blank" title="听说柯震东是你们的男神？">听说柯震东是你们的男神？</a></li>
						</ul>
					  </div>
					  <div role="tabpanel" class="tab-pane" id="messages">..3.</div>
					  <div role="tabpanel" class="tab-pane" id="settings">...4.</div>
					</div>

					<div class="clearfix"></div>
			  </div>
				<script type="text/javascript">
				  jQuery(document).ready(function($){
					$('.quickpannel_toggle').click(function(){
					  $('#quickpannel').toggleClass('open');
					})
					$('.quickpannel .overlay').click(function(){
					  $('#quickpannel').toggleClass('open');
					})
				  })
				</script>  
		</div>

	</footer>


	<div id="go-to-top" title="Go to Top">
		<span class="glyphicon glyphicon-chevron-up"></span>
	</div>
	<script type="text/javascript">
		
		  $(window).scroll(function(){
			  if(document.body.scrollTop > 0)
				$("#go-to-top").css("bottom","20px");
			  else	
				$("#go-to-top").css("bottom","-100px");

			$(".panel_content").each(function(){
				if(document.body.scrollTop + window.innerHeight - $(this).offset().top > 0) {
					$(this).find(".panel_content_item").each(function(i){
						$(this).addClass(" animated "+(i%2==1?"fadeInLeft":"fadeInRight"))
					});
				}
			
			});


			 /* if(document.body.scrollTop + window.innerHeight - $(".panel_content").offset().top > 0) {
				  $(".panel_content_item").each(function(i){
					//$(this).addClass("a"+i+" animated fadeInLeft")
					$(this).addClass("a"+i+" animated "+(i%2==1?"fadeInLeft":"fadeInRight"))
				});
			}*/
				/*if(document.body.scrollTop + window.innerHeight - $("#items").offset().top > 300){
					
					$(".panel_content_item").each(function(i){
						$(this).removeClass("fadeInLeft fadeInRight");
						});
				}*/
		  });
		  $("#go-to-top").click(function(){
			  $('body').animate({scrollTop: '0px'}, 500,'easeOutQuad');
			 //document.body.scrollTop = 0;
		  });
		
	</script>
</body>
</html>
