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
    <form action="https://www.yeepay.com/app-merchant-proxy/node" method="post">
    	<input type="hidden" name="p0_Cmd" value="${p0_Cmd }"/>
    	<input type="hidden" name="p1_MerId" value="${p1_MerId }"/>
    	<input type="hidden" name="p2_Order" value="${p2_Order }"/>
    	<input type="hidden" name="p3_Amt" value="${p3_Amt }"/>
    	<input type="hidden" name="p4_Cur" value="${p4_Cur }"/>
    	<input type="hidden" name="p5_Pid" value="${p5_Pid }"/>
    	<input type="hidden" name="p6_Pcat" value="${p6_Pcat }"/>
    	<input type="hidden" name="p7_Pdesc" value="${p7_Pdesc }"/>
    	<input type="hidden" name="p8_Url" value="${p8_Url }"/>
    	<input type="hidden" name="p9_SAF" value="${p9_SAF }"/>
    	<input type="hidden" name="pa_MP" value="${pa_MP }"/>
    	<input type="hidden" name="pd_FrpId" value="${pd_FrpId }"/>
    	<input type="hidden" name="pr_NeedResponse" value="${pr_NeedResponse }"/>
    	<input type="hidden" name="hmac" value="${hmac }"/>
    	<p><input type="submit" value="确认支付"/></p>
    </form>
</div>
    <%@ include file="common/pre/footer.jsp" %>
</div>
</body>
</html>
