//jqueryValidate自定义验证
jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;	//获取到输入的值得长度
        var mobile = /^1[34578]\d{9}$/;/*/^1(3|4|5|7|8)\d{9}$/*/;	//使用正则表达式
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");

$(function(){
	$("#register").validate({
		rules:{
			loginname:{
				required:true,
				minlength:3,
				remote:{
					type:"POST",
					url:"user/checkdata",
					data:{
							param:function(){return $("input[name='loginname']").val();},
							type:1
						} 
				}
			},
			password:{
				required:true,
				minlength:3
			},
			confirmPassword:{
				required:true,
				equalTo:"#password"
			},
			email:{
				required:true,
				email:true,
				remote:{
					type:"POST",
					url:"user/checkdata",
					data:{
							param:function(){return $("input[name='email']").val();},
							type:2
						} 
				}
			},
			mobile:{
				required:true,
				isMobile:true,
				remote:{
					type:"POST",
					url:"user/checkdata",
					data:{
							param:function(){return $("input[name='mobile']").val();},
							type:3
						} 
				}
			},
			mobileCode:{
				required:true,
				remote:{
					type:"POST",
					url:"user/checkCode",
					data:{
							code:function(){return $("input[name='mobileCode']").val();},
							phone:function(){return $("input[name='mobile']").val();}
						} 
				}
			}
		},
		messages:{
			loginname:{
				required:"姓名必须填写",
				minlength:"姓名长度不能小于3位",
				remote:"用户名已经被注册"
			},
			password:{
				required:"密码必须填写",
				minlength:"长度不能小于3"
			},
			confirmPassword:{
				required:"重复密码必须输入",
				equalTo:"两次输入的密码不一致"
			},
			email:{
				required:"邮箱必须填写",
				email:"邮箱格式不正确",
				remote:"邮箱已经被注册"
			},
			mobile:{
				required:"请输入手机号",
				isMobile:"请正确填写您的手机号码",
				remote:"手机号已经被注册"
			},
			mobileCode:{
				required:"请输入验证码",
				remote:"验证码输入错误"
			}
		}
	});
});
var time = 60;
var clear;
function changeTime(){
	time--;
	if(time == 0){
		window.clearInterval(clear);
		$("#sendMessageBtn").removeAttr("disabled");
		$("#sendMessageBtn").attr("value","发送验证码");
	} else {
		$("#sendMessageBtn").attr("value",time+"s后可以重新发送短信");
	}
}

$(function(){
	$("#sendMessageBtn").click(function(){
		time = 160;
		//点击或将按钮变为灰色
		$(this).attr("disabled","disabled");
		clear = window.setInterval("changeTime()", 1000);
		//发送验证码
		$.ajax({
			url:"user/sendCode",
			type:"post",
			data:{"phone":$("input[name='mobile']").val()},
			dataType:"json",
			success:function(result){
				
			}
		});
	});
})