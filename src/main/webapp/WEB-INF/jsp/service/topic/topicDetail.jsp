<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>资讯详细信息</title>
</head>

<body>

<div class="panel panel-info">
        <div class="panel-heading clearfix">
            <h4 class="panel-title pull-left">${topic.title}</h4>
            <hr><h6><fmt:formatDate value="${topic.lastUpdatedTime}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></h6>
        </div>
        <div class="panel-body">
            ${topic.content}
        </div>
        <div class="panel-footer">
            <a href="${ctx}/topic/list?pageNum=${topicPager.currentPage}" id="cancel_btn" class="btn btn-info" role="button">返回</a>
            <a href="${ctx}/topic/update/${topic.id}" class="btn btn-info" role="button">更新</a>
            <a href="${ctx}/topic/delete/${topic.id}" class="btn btn-info" role="button">删除</a>
        </div>
    </div>    
</body>
</html>