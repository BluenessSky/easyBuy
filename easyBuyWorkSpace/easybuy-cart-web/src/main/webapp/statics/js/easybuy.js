var easybuy = EASYBUY = {
	checkLogin : function(){
		//1.从cookie中获取到值
		var _ticket = $.cookie("EASYBUY_COOKIE");
		if(!_ticket){
			return ;
		}
		var url = window.location.href;//获取到当前的url
		$.ajax({
			url : "http://localhost:8083/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				//alert(JSON.stringify(data));
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到易买网！<a href=\"http://localhost:8083/user/logout/"+_ticket+"\" class=\"link-logout\">[退出]</a>";
					$(".fr").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	easybuy.checkLogin();
});