<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="common/pre/header.jsp" %>
    <script src="${ctx}/statics/js/cart/cart.js"></script>
    <title>易买网</title>
    <script type="text/javascript" src="${ctx}/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/statics/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="${ctx}/statics/js/easybuy.js"></script>
	<style>
		.select-pay{
			font-size: 16px;
			padding: 1px;
		}
		.selected{
			background-color: red;
			color: white;
		}
	</style>
	<script>
		$(function() {
			var orderId='${currentOrder.id}';
			$("#chonseCard").hide();
			//选择银行
			$("input[name='pdFrpId']").click(function() {
				//支付通道编码
				var pdFrpId=$(this).val();
				
				window.location.href="http://localhost:8086/pay/yibao?orderId=" + orderId+"&pdFrpId="+pdFrpId;
			});
			
		})
		function xuanze(obj,num,orderId){
			$("span[name=payBtn]").removeClass("selected");
			$(obj).addClass("selected");
			if(num == 1){
				alert("您选择了货到付款");
			}else if(num == 2){
				window.location.href="http://localhost:8087/pay/wx?orderId=" + orderId;
			}else if(num == 3){
				//显示可选的银行
				$("#chonseCard").show();
				
			}
		}
	</script>
</head>
<body>
<!--Begin Header Begin-->
<div id="searchBar">
    <%@ include file="common/pre/searchBar.jsp" %>
</div>
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
    <div class="content mar_20">
    <img src="${ctx}/statics/images/img3.jpg"/>
</div>
<div class="content mar_20">
    <!--Begin 银行卡支付 Begin -->
    <div class="warning">
        <table border="0" style="width:1000px; text-align:center;" cellspacing="0" cellpadding="0">
            <tr height="35">
                <td style="font-size:18px;">
                    感谢您在本店购物！您的订单已提交成功，请记住您的订单号: <font color="#ff4e00">${currentOrder.id}</font>
                </td>
            </tr>
            <tr>
                <td style="font-size:14px; font-family:'宋体'; padding:10px 0 20px 0; border-bottom:1px solid #b6b6b6;">
                    &nbsp; &nbsp;您的应付款金额为: <font color="#ff4e00">￥${currentOrder.cost}</font>
                </td>
            </tr>
            <tr>
            	<td>
            		 <span name="payBtn" class="select-pay" onclick="xuanze(this,1,'${currentOrder.id}')">货到付款</span>
            		 <span name="payBtn" class="select-pay" onclick="xuanze(this,2,'${currentOrder.id}')">微信扫码支付</span>
            		 <span name="payBtn" class="select-pay" onclick="xuanze(this,3,'${currentOrder.id}')">微信扫码支付</span>
            	</td>
            </tr>
            <tr id="chonseCard">
            	<td style="padding:20px 0 30px 0; font-family:'宋体';border-bottom:1px solid #b6b6b6;">
                   <input type="radio" name="pdFrpId" value="ICBC-NET-B2C"/>工商银行&nbsp;&nbsp;
                   <input type="radio" name="pdFrpId" value="CMBCHINA-NET-B2C"/>招商银行&nbsp;&nbsp;
                   <input type="radio" name="pdFrpId" value="ABC-NET-B2C"/>中国农业银行&nbsp;&nbsp;
                   <input type="radio" name="pdFrpId" value="CCB-NET-B2C"/>建设银行&nbsp;&nbsp;   
                </td>
            </tr>
            <tr>
                <td style="padding:20px 0 30px 0; font-family:'宋体';border-bottom:1px solid #b6b6b6;">
                    收款人信息：全称 ${loginUser.username} ；地址 ${currentOrder.useraddress} ； <br/>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="${ctx}/Home?action=index">首页</a> &nbsp; &nbsp; <a href="${ctx}/Home?action=index">用户中心</a>
                </td>
            </tr>
        </table>
    </div>
</div>
    <%@ include file="common/pre/footer.jsp" %>
</div>
</body>
</html>
