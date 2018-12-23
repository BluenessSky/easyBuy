<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="common/pre/header.jsp" %>
    <script type="text/javascript" src="${ctx}/statics/js/common/n_nav.js"></script>
    <script src="${ctx}/statics/js/cart/cart.js"></script>
    <title>易买网</title>
    <style>
    	.contentCart{
		    width: 1200px;
		    overflow: hidden;
    		display:block;
    		position: relative;
    	}
    	.succ-icon{
      		position: absolute;
		    top: 0;
		    left: 0;
		    display: block;
		    width: 25px;
		    height: 25px;
		    overflow: hidden;
		    background: url(${ctx}/statics/images/addtocart-icons.png) 0 0 no-repeat;
    	}
    	.ftx-02 {
    		color: #71b247;
    		margin-left: 30px;
    		float: left;
		}
		.btn-tobback {
			position: relative;
		    padding: 0 30px;
		    margin-right: 10px;
		    background: #fff;
		    color: #e4393c;
		    border: 1px solid #fff;
		    float: right;
		}
		.btn-addtocart {
		    position: relative;
		    width: 136px;
		    padding-left: 30px;
		    background: #e4393c;
		    border: 1px solid #e4393c;
		    color: #fff;
		    display: inline-block;
		    height: 34px;
		    line-height: 36px;
		    font-size: 16px;
		    vertical-align: middle;
		    float: right;
		}
    </style>
    <script type="text/javascript" src="${ctx}/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="${ctx}/statics/js/easybuy.js"></script>
</head>
<body>
<!--Begin Header Begin-->
<div id="searchBar">
    <%@ include file="common/pre/searchBar.jsp" %>
</div>
<!--Begin Header Begin-->
<!--End Header End-->
<!--Begin Menu Begin-->
<div class="menu_bg">
    <div class="menu">
        <!--Begin 商品分类详情 Begin-->
        <%@ include file="common/pre/categoryBar.jsp" %>
        <!--End 商品分类详情 End-->
    </div>
</div>
<!--End Menu End-->
<div class="i_bg">
    <div class="postion">
    </div>
    <div class="contentCart">
    	<b class="succ-icon"></b><h3 class="ftx-02">商品已成功加入购物车！</h3>
    	<a class="btn-tobback" href="javaScript:window.history.go(-1)" target="_blank">查看商品详情</a>
    	<a class="btn-addtocart" href="#" id="GotoShoppingCart"><b></b>去购物车结算</a>
    </div>
    <script>
        favoriteList();
    </script>
    <!--End 弹出层-收藏成功 End-->
    <%@ include file="common/pre/footer.jsp" %>
    <!--Begin 弹出层-加入购物车 Begin-->
</div>
<script>
refreshCart();
</script>
<script>
$(function(){
	$("#GotoShoppingCart").click(function(){
		window.location.href="/cart/showcart";
	});
})
</script>
</body>
</html>
