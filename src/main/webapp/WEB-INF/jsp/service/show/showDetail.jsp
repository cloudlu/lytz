<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>演唱会详细信息</title>
</head>

<body>
<shiro:guest>
<div class="container  col-md-12 lytz_spacer">
</shiro:guest>

<div class="panel panel-info">
        <div class="panel-heading clearfix">
            <h4 class="panel-title pull-left">${show.title}</h4>
            <hr><fmt:formatDate value="${show.lastUpdatedTime}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
        </div>
        <div class="panel-body">
            ${show.content}
        </div>
        <div class="panel-footer">
            <button id="cancel_btn" class="btn" type="button" onclick="history.back()">返回</button>
            <shiro:hasRole name="ROLE_ADMIN">
                <a href="${ctx}/admin/show/update/${show.id}" class="btn btn-info" role="button">更新</a>
                 <a href="${ctx}/admin/show/delete/${show.id}" class="btn btn-info" role="button">删除</a>
            </shiro:hasRole>
        </div>
    </div>    
<shiro:guest>
</div>
</shiro:guest>
</body>
</html>