<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>演唱会资讯</title>
</head>

<body>
<shiro:guest>
<div class="container  col-md-12 lytz_spacer">
</shiro:guest>

    <fieldset>
       <legend>演唱会标题</legend>
       ${show.title}
               
    </fieldset>
    <fieldset>
       <legend>更新时间</legend>
       <fmt:formatDate value="${show.lastUpdatedTime}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
               
    </fieldset>
   <fieldset>
       <legend>演唱会内容</legend>
       ${show.content}
       
    </fieldset> 
    <button id="cancel_btn" class="btn" type="button" onclick="history.back()">返回</button>

    <shiro:hasRole name="ROLE_ADMIN">
        <a href="${ctx}/admin/show/update/${show.id}" class="btn btn-info" role="button">更新</a>
         <a href="${ctx}/admin/show/delete/${show.id}" class="btn btn-info" role="button">删除</a>
    </shiro:hasRole>
    
<shiro:guest>
</div>
</shiro:guest>
</body>
</html>