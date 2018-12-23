<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="common/pre/header.jsp" %>
    <script src="${ctx}/statics/js/backend/backend.js"></script>
    <script src="${ctx}/statics/js/jquery-1.6.4.js"></script>
    <script src="${ctx}/statics/js/backend/solr.js"></script>
</head>
<body>
<%@ include file="common/backend/searchBar.jsp" %>
<!--End Header End-->
<div class="i_bg bg_color">
    <!--Begin 用户中心 Begin -->
    <div class="m_content">
        <%@ include file="common/backend/leftBar.jsp"%>
        <div class="m_right" id="content">
            <div class="m_des">
               <button id="btnSolr">将所有商品信息添加到solr</button>
            </div>
        </div>	
    </div>
    <%@ include file="common/pre/footer.jsp" %>
</div>
</body>
</html>

















