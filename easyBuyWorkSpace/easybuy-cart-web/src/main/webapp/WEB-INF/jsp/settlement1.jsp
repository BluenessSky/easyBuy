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
    <img src="${ctx}/statics/images/img1.jpg"/>
</div>
<!--Begin 第一步：查看购物车 Begin -->
<div class="content mar_20" >
    <table border="0" class="car_tab" style="width:1200px; margin-bottom:50px;" cellspacing="0" cellpadding="0">
        <tr>
            <td class="car_th" width="200">商品名称</td>
            <td class="car_th" width="150">单价</td>
            <td class="car_th" width="150">购买数量</td>
            <td class="car_th" width="130">小计</td>
            <td class="car_th" width="150">操作</td>
        </tr>
        <c:forEach items="${cart.items}" var="temp">
            <tr>
                <td>
                    <div class="c_s_img">
                        <a href="${ctx}/Product?action=queryProductDeatil&id=${temp.id}"><img src="${temp.filename}" width="73" height="73"/></a>
                    </div>
                        ${temp.name}
                </td>
                <td align="center" style="color:#ff4e00;">￥<span>${temp.price}</span></td>
                <td align="center">
                    <div class="c_num">
                        <input type="button" value="" onclick="subQuantity(this,'${temp.id}');" class="car_btn_1"/>
                        <input type="text" value="${temp.stock}" name="" class="car_ipt"/>
                        <input type="button" value="" onclick="addQuantity(this,'${temp.id}');" class="car_btn_2"/>
                    </div>
                </td>
                <td align="center" style="color:#ff4e00;">￥${temp.price * temp.stock}</td>
                <td align="center"><a href="javascript:void(0);" onclick="removeCart(this,'${temp.id}');" >删除</a>&nbsp; &nbsp;</td>
            </tr>
        </c:forEach>
        <tr height="70">
            <td colspan="6" style="font-family:'Microsoft YaHei'; border-bottom:0;">
                <span class="">商品总价：<b style="font-size:22px; color:#ff4e00;float: right;" id="sum">￥${cart.sum}</b></span>
            </td>
        </tr>
        <tr valign="top" height="150">
            <td colspan="6" align="right">
                <a href="${ctx}/Home?action=index"><img src="${ctx}/statics/images/buy1.gif" /></a>&nbsp;&nbsp;
                <c:if test="${cart!=null && cart.sum>0}">
                    <a href="javascript:void(0);" onclick="javaScript:window.location.href='http://localhost:8085/order/settlement2'"><img src="${ctx}/statics/images/buy2.gif" /></a>
                </c:if>
            </td>
        </tr>
    </table>
</div>
    <%@ include file="common/pre/footer.jsp" %>
</div>
</body>
</html>
