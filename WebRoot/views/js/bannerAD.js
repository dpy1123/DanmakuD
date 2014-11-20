$(document).ready(function(){
	
	//首页共有5个固定广告位，每个广告位有2块文字显示区域，请在下列参数修改广告文字
	
		//广告位1  
		var imgAd01 = "images/bannerAD01.jpg";   //对应图片images/homeAd01.jpg
		var homeAd01 = "娱乐";   //第一块文字显示区域
		var myAd01 = "这里有丰富的娱乐视频<br/>还有各种生活记录";	 //第二块  可用<br/>换行
		
		//广告位2 
		var imgAd02 = "images/bannerAD02.jpg";
		var homeAd02 = "游戏";
		var myAd02 = "高手视频、游戏解说、风格点评<br/>样样俱全";
		
		//广告位3 
		var imgAd03 = "images/bannerAD03.jpg";
		var homeAd03 = "动漫视频";
		var myAd03 = "动漫相关的新番吐槽、同人、MAD等<br/>都在这里";
		
		//广告位4
		var imgAd04 = "images/bannerAD04.jpg";
		var homeAd04 = "音乐";
		var myAd04 = "音乐视频、VOCALOID创作、翻唱<br/>释放你的才能！";
		
		//广告位5  对应图片images/homeAd05.jpg
		var homeAd05 = "文学鉴赏";
		var myAd05 = "千里寻他千百度<br/>暮然回首，那人却在灯火阑珊处。";
		
		
	
	//以下代码为广告样式，非专业人士请勿随便更改
		$("#bannerAD .slider").html(
				 '<div class="item item1">'
				+'	<div class="inner" style="background-image: url('+ imgAd01 +');">'
				+'		<div class="selectorShadow"></div>'
				+'		<div class="text1">'
				+'			<span>'+ homeAd01 +'</span>'
				+'		</div>'
				+'		<div class="text2">'
				+'			<span>'+ myAd01 +'</span>'
				+'		</div>'
				+'	</div>'
				+'</div>'
				+'<div class="item item2">'
				+'	<div class="inner" style="background-image: url('+ imgAd02 +');">'
				+'		<div class="selectorShadow"></div>'
				+'		<div class="text1">'
				+'			<span>'+ homeAd02 +'</span>'
				+'		</div>'
				+'		<div class="text2">'
				+'			<span>'+ myAd02 +'</span>'
				+'		</div>'
				+'	</div>'
				+'</div>'
				+'<div class="item item3">'
				+'	<div class="inner" style="background-image: url('+ imgAd03 +');">'
				+'		<div class="selectorShadow"></div>'
				+'		<div class="text1">'
				+'			<span>'+ homeAd03 +'</span>'
				+'		</div>'
				+'		<div class="text2">'
				+'			<span>'+ myAd03 +'</span>'
				+'		</div>'
				+'	</div>'
				+'</div>'
				+'<div class="item item4">'
				+'	<div class="inner" style="background-image: url('+ imgAd04 +');">'
				+'		<div class="selectorShadow"></div>'
				+'		<div class="text1">'
				+'			<span>'+ homeAd04 +'</span>'
				+'		</div>'
				+'		<div class="text2">'
				+'			<span>'+ myAd04 +'</span>'
				+'		</div>'
				+'	</div>'
				+'</div>'
				

		);
	});

