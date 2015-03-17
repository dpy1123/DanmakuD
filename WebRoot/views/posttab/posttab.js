jQuery(document).ready(function() {
 	var $ = jQuery;
    var screenRes = $(window).width(),
        screenHeight = $(window).height(),
        html = $('html');

// IE<8 Warning
    if (html.hasClass("ie7")) {
        $("body").empty().html('Please, Update your Browser to at least IE8');
    }

// Disable Empty Links
    $("[href=#]").click(function(event){
        event.preventDefault();
    });

// Body Wrap
    $(".body-wrap").css("min-height", screenHeight);
    $(window).resize(function() {
        screenHeight = $(window).height();
        $(".body-wrap").css("min-height", screenHeight);
    });

// Remove outline in IE
	$("a, input, textarea").attr("hideFocus", "true").css("outline", "none");
// Post Tabs
    var postTabsLenght = $('.post-tabs').length,
        tabHeight;

    function tabsHeight() {
        if(postTabsLenght > 0) {
            for(var i = 1; i <= postTabsLenght; i++) {
                $('.post-tabs').eq(i-1).attr('id', 'post-tabs'+i);
                tabHeight = $('#post-tabs'+i+' .post-item.active').height();
                $('#post-tabs'+i+' .post-tabs-bookmarks ul').css('height', tabHeight);
            }
        }
    }

    $(window).load(function() {
        tabsHeight()
    });
    $(window).resize(function() {
        tabsHeight()
    });

    $('.post-tabs-bookmarks li').click(function() {
        var $this = $(this),
            index = $this.index();
        $this.parents().children().removeClass('active');
        $this.addClass('active');
        $this.parents(".post-tabs").children(".post-tabs-content").children().removeClass('active');
        $this.parents(".post-tabs").children(".post-tabs-content").children().eq(index).addClass('active');

        tabHeight = $this.parents(".post-tabs").children(".post-tabs-content").children(".post-item.active").height();
        $this.parents('.post-tabs-bookmarks ul').css('height', tabHeight);
    });

});