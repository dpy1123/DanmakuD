<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>

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
					<a class="navbar-brand" href="<%=path%>/home.do">DD.TV</a>
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
										<li><a href="<%=path%>/list.do?categoryName=${subcategory.name }">${subcategory.name}</a></li>
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



