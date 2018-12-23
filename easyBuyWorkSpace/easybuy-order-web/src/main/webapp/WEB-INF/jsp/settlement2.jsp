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
    <div class="two_bg">
        <div class="two_t">
            <span class="fr"><a href="javascript:void(0);" onclick="settlement1();">修改</a></span>商品列表
        </div>
        <table border="0" class="car_tab" style="width:1110px;" cellspacing="0" cellpadding="0">
            <tr>
                <td class="car_th" width="550">商品名称</td>
                <td class="car_th" width="150">购买数量</td>
                <td class="car_th" width="130">小计</td>
            </tr>
            <c:forEach items="${items}" var="temp">
                <tr>
                    <td>
                        <div class="c_s_img">
                            <img src="${temp.filename}" width="73" height="73"/>
                        </div>
                            ${temp.name}
                    </td>
                    <td align="center">${temp.stock}</td>
                    <td align="center" style="color:#ff4e00;">￥${temp.stock * temp.price}</td>
                </tr>
            </c:forEach>
        </table>

        <div class="two_t">
            <span class="fr"></span>收货人信息
        </div>
        <table border="0" class="peo_tab" style="width:1110px;" cellspacing="0" cellpadding="0">
            <tr>
                <td class="p_td" width="160">用户名称</td>
                <td width="395">${loginUser.username}</td>
                <td class="p_td">登录名称</td>
                <td>${loginUser.loginname}</td>
            </tr>
            <tr>
                <td class="p_td">手机</td>
                <td>${loginUser.mobile}</td>
                <td class="p_td" width="160">电子邮件</td>
                <td width="395">${loginUser.email}</td>
            </tr>
        </table>
        <div class="two_t">
            <span class="fr"></span>选择地址
        </div>
        <table border="0" class="peo_tab" style="width:1110px;" cellspacing="0" cellpadding="0">
            <c:forEach items="${userAddressList}" var="temp" varStatus="status">
                <tr>
                    <td class="p_td" width="160">
                        <c:choose>
                            <c:when test="${empty temp.remark}">
                                地址${status.index+1}
                            </c:when>
                            <c:otherwise>
                                ${temp.remark}
                            </c:otherwise>
                        </c:choose>
                        <input type="radio" name="selectAddress" value="${temp.id}" ${temp.isdefault == 1?"checked":"" }>
                    </td>
                    <td>
                            ${temp.address}
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td class="p_td" width="160">
                    新地址<input type="radio" name="selectAddress" value="-1">
                </td>
                <td>
                    地址&nbsp;<input type="text" value="" name="address" class="add_ipt">&nbsp;
                    备注&nbsp;<input type="text" value="" name="remark" class="add_ipt">
                </td>
            </tr>
        </table>
        <form id="orderform" method="post">
        <table border="0" style="width:900px; margin-top:20px;" cellspacing="0" cellpadding="0">
            <tr height="70">
                <td align="right">
                    <b style="font-size:14px;">应付款金额：<span
                            style="font-size:22px; color:#ff4e00;">￥${sum}</span></b>
                    <input type="hidden" name="addressId" id="addressId"/>
                    <input type="hidden" name="newAddress" id="newAddress"/>
                    <input type="hidden" name="newRemark" id="newRemark"/>
                    <input type="hidden" name="userid" value="${loginUser.id }"/>
                    <input type="hidden" name="loginname" value="${loginUser.loginname }"/>
                    <input type="hidden" name="sum" value="${sum }"/>
                </td>
            </tr>
            <tr height="70">
                <td align="right"><a href="javascript:void(0);" onclick="settlement3();"><img
                        src="${ctx}/statics/images/btn_sure.gif"/></a></td>
            </tr>
        </table>
        </form>
    </div>
</div>
    <%@ include file="common/pre/footer.jsp" %>
</div>
</body>
</html>
