<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
    var contextPath = "${ctx}";
</script>
<div class="top">
    <div class="logo">
        <a href="http://localhost:8080"><img src="${ctx}/statics/images/logo.png"></a>
    </div>
    <div class="search">
        <form action="http://localhost:8081/item/search" method="post">
            <input type="text" value="${keyWord}" name="query" class="s_ipt">
            <input type="submit" value="搜索" class="s_btn">
        </form>
        <!--推荐最热商品-->
    </div>
    <div class="i_car">
        <div class="car_t">
            购物车 [<span>空</span>]
        </div>
        <div class="car_bg">
            <!--Begin 购物车未登录 Begin-->
            <c:if test="${loginUser==null}">
                <div class="un_login">还未登录！<a href="${ctx}/Login?action=toLogin" style="color:#ff4e00;">马上登录</a></div>
            </c:if>
            <c:if test="${loginUser!=null}">
                <div class="un_login">我的购物车</div>
            </c:if>
            <!--End 购物车未登录 End-->
            <!--Begin 购物车已登录 Begin-->
            <ul class="cars">
            	<!-- 购物车中的商品列表 -->
            	<li  id="mycars"></li>
            </ul>
            <div class="price_sum">共计&nbsp;<font color="#ff4e00">￥</font><span>${cart.sum}</span></div>
            <c:if test="${loginUser==null}">
                <div class="price_a"><a href="${ctx}/Login?action=toLogin">去登录</a></div>
            </c:if>
            <c:if test="${loginUser!=null}">
                <div class="price_a"><a href="${ctx}/Cart?action=toSettlement">去结算</a></div>
            </c:if>
            <!--End 购物车已登录 End-->
        </div>
    </div>
</div>
