<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>
<link href="<%=path%>/views/css/index.css" rel="stylesheet" type="text/css">

<footer class="footer">
	<div class="container">
		<p class="text-muted">© 2013-2015 <span>DD</span> present.</p>
	</div>

	<!-- 左侧弹出pannel -->
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
						<ul class="dyn_list">
							<li class="d-data">
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
							<div class="r-l">
								<a class="read-more" href="">查看全部</a>
								<div class="num" id="dynamic_num"></div>
								<div class="check-all no-select"></div>
							</div>
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
					});
					$('.quickpannel .overlay').click(function(){
	  					$('#quickpannel').toggleClass('open');
					});
	 			})
			</script>  
		</div>
	</div>
</footer>

