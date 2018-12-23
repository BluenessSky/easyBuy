<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    var contextPath = "${ctx}";
</script>
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
    <img src="${ctx}/statics/images/img2.jpg"/>
</div>
<div class="content mar_20">
    <h1>支付成功</h1>
    <p>订单号是:${r6_Order }</p>
    <p>支付金额:${r3_Amt }</p>
</div>
    <%@ include file="common/pre/footer.jsp" %>
</div>
</body>
</html>
