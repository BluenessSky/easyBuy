<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="m_left">
    <div class="left_n">管理中心</div>
    <div class="left_m">
        <div class="left_m_t t_bg1">订单中心</div>
        <ul>
            <li><a href="${ctx}/admin/order?action=index&userId=${sessionScope.loginUser.id}" <c:if test="${menu==1}"> class="now" </c:if>>我的订单</a></li>
        	<c:if test="${sessionScope.loginUser.type==1}">
        		<li><a href="${ctx}/admin/order?action=queryAllOrder" <c:if test="${menu==9}"> class="now" </c:if>>全部订单</a></li>
        	</c:if>
        </ul>
    </div>
    <div class="left_m">
        <div class="left_m_t t_bg2">会员中心</div>
        <ul>
            <li><a href="${ctx}/admin/user?action=index"  <c:if test="${menu==2}"> class="now" </c:if>>用户信息</a></li>
            <c:if test="${sessionScope.loginUser.type==1}">
            	<li><a href="${ctx}/admin/user?action=queryUserList"  <c:if test="${menu==8}"> class="now" </c:if>>用户列表</a></li>
            </c:if>
        </ul>
    </div>
    <div class="left_m">
        <div class="left_m_t t_bg2">商品管理</div>
        <ul>
            <li><a href="${ctx}/categorylist" <c:if test="${menu==4}"> class="now" </c:if>>分类管理</a></li>
            <li><a href="${ctx}/product/list"  <c:if test="${menu==5}"> class="now" </c:if>>商品管理</a></li>
            <li><a href="${ctx}/product/toadd" <c:if test="${menu==6}"> class="now" </c:if>>商品上架</a></li>
            <li><a href="${ctx}/solr" <c:if test="${menu==8}"> class="now" </c:if>>上传至solr</a></li>
        </ul>
    </div>
    <div class="left_m">
        <div class="left_m_t t_bg2">资讯中心</div>
        <ul>
            <li><a href="${ctx}/admin/news?action=queryNewsList"  <c:if test="${menu==7}"> class="now" </c:if>>资讯列表</a></li>
        </ul>
    </div>
    
    <div class="left_m">
        <div class="left_m_t t_bg2">内容管理系统(CMS)</div>
        <ul>
            <li><a href="${ctx}/admin/news?action=queryNewsList"  <c:if test="${menu==9}"> class="now" </c:if>>大广告列表</a></li>
            <li><a href="${ctx}/content/toadd"  <c:if test="${menu==10}"> class="now" </c:if>>大广告上架</a></li>
        </ul>
    </div>
</div>