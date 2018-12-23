/**
 * Created by bdqn on 2016/5/3.
 */
// 登录的方法
var redirectUrl = "${redirect}";
$(function() {
	$("#btnlogin").click(function() {
		var loginName = $("#loginName").val();
		var password = $("#password").val();
		$.ajax({
			url : "dologin",
			type : "post",
			data : {
				loginName : loginName,
				password : password
			},
			dataType : "json",
			success : function(result) {
				if (result.status == 200) {
					if (redirectUrl == "") {
						location.href = "http://localhost:8080";
					} else {
						location.href = redirectUrl;
					}

				} else {
					showMessage(result.msg)
				}
			}
		})
	});
})