<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">   
	<title>秒杀详情页面</title>
	<!-- bootStrap的样式 -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- jQuery-->
    <script src="script/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件(模态框来自于该js) -->
    <script src="script/bootstrap.min.js"></script>
    <%--jQuery Cookie操作插件--%>
    <script src="script/jquery.cookie.min.js"></script>
    <%--jQuery countDown倒计时插件--%>
    <script src="script/jquery.countdown.min.js"></script>
    <script>
        //验证手机号
        function checkPhone(phone) {
        	//应该要通过正则表达式进行手机号验证
            if(phone && phone.length == 11 && isNaN(phone) == false){
                return true;
            } else {
                return false;
            }
        }
        
        //开始秒杀(******)
        function handlerSeckill(seckillId, node) {
            //获取秒杀地址,控制显示器,执行秒杀
            node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
            //暴漏秒杀接口地址
            $.post('/' + seckillId + '/exposer',{},function (result) {
                //在回调函数中执行交互过程(执行秒杀方法被调用后返回的结果:success,exposer[md5])
                if(result['exporsed']){
                    var exposer = result;
                    //表示暴漏秒杀接口成功
                    //开启秒杀,并且获取到秒杀地址
                    var md5 = exposer.md5;
                    var killUrl = '/' + seckillId + '/' + md5 + '/execution';
                    $("#killBtn").click(function () {
                        //1.点击后将该按钮禁用
                        $(this).attr("disabled","disabled");
                        //2.发送秒杀请求，执行秒杀
                        $.post(killUrl,{},function (result) {
                        	//这里也可以通过state是200还是400进行判断
                            var stateInfo = result['msg'];
                            //显示秒杀结果
                            node.html('<span class="label label-success">' + stateInfo + '</span>');
                        });
                    });
                    node.show();
                }
            });
        }
        //时间判断，记时交互
        function countDown(seckillId,nowTime,startTime,endTime) {
            if(nowTime > endTime){
                //秒杀结束
                $("#seckill-box").html("秒杀结束!");
            } else if(nowTime < startTime){
                //秒杀未开始,计时事件绑定
                var killTime = new Date(startTime + 1000);//todo 防止时间偏移
                $('#seckill-box').countdown(killTime, function (event) {
                    //时间格式
                    var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒 ');
                    $('#seckill-box').html(format);
                });
            } else {
                //开始秒杀
                handlerSeckill(seckillId,$('#seckill-box'));
            }
        }

		//检查秒杀的商品是否在秒杀的时间范围内
        function checkTime() {
			//需要传递过来一个Seckill对象(开始时间，结束时间，秒杀商品的id，)
            var startTime = ${seckill.startTime.time};           //秒杀开始时间
            var endTime = ${seckill.endTime.time};               //秒杀结束时间
            var seckillId = ${seckill.seckillId};           //秒杀商品编号
           	//去服务器取当前系统的时间
            $.get("/time/now",{},function (result) {
                var nowTime = result["data"];   //获取到服务器时间
                //时间判断，计时交互
                countDown(seckillId,nowTime,startTime,endTime);
            });

        }
		//我们也页面加载的时候
        $(function(){
           //从cookie中查找手机号
           var killPhone = $.cookie("killPhone");
           //如果没有取到cookie中的秒杀电话，或者是秒杀电话不正确
            if(checkPhone(killPhone) == false){
                $("#killPhoneModal").modal({
                    show:true,   //显示弹出层
                    backdrop:'static',  //禁止位置关闭
                    keyboard:false      //关闭键盘事件
                });
            }
           //表示cookie有秒杀的电话
            checkTime();
        });



        $(function () {
           $("#killPhoneBtn").click(function () {
               var inputPhone = $("#killPhoneKey").val();        //获取到输入的秒杀电话号码
               if(checkPhone(inputPhone)){
                    //电话写入cookie，7天过期
                   $.cookie('killPhone',inputPhone,{expires:7, path:'/'});
                   //验证通过，刷新页面
                   window.location.reload();
               } else {
                   $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
               }
           });
        });


    </script>
</head>
<body>

<div class="container">
    <div class="panel panel-default text-center">
        <div class="pannel-heading">
            <h1>${seckill.name}</h1>
        </div>

        <div class="panel-body">
            <h2 class="text-danger">
                <%--显示time图标--%>
                <span class="glyphicon glyphicon-time"></span>
                <%--展示倒计时--%>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
</div>


<%--登录弹出层 输入电话--%>
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"> </span>秒杀电话:
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="填写手机号^o^" class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <%--验证信息--%>
                <span id="killPhoneMessage" class="glyphicon"> </span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>

        </div>
    </div>

</div>
</html>