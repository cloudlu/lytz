<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>演唱会资讯</title>
</head>

<body>
<shiro:guest>
    <div class="container  col-md-12 lytz_spacer">
<ul>
    <c:forEach items="${shows}" var="show">
           <li> <a href="${ctx}/show/view/${show.id}">${show.title}</a></td>
            </li>
        </c:forEach>
        </ul>
    </div>
</shiro:guest>
<c:if test="${not empty message}">
        <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
    </c:if>
<shiro:hasAnyRoles name="ROLE_VIP,ROLE_USER,ROLE_ADMIN">
<ul>
    <c:forEach items="${shows}" var="show">
           <li> <a href="${ctx}/show/view/${show.id}">${show.title}</a></td>
            </li>
        </c:forEach>
        </ul>
</shiro:hasAnyRoles>

<%--<shiro:hasRole name="ROLE_ADMIN">
    
    
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead><tr><th>演唱会标题</th><th>创建时间</th><th>管理</th></tr></thead>
        <tbody>
        <c:forEach items="${shows}" var="show">
            <tr>
                <td><a href="${ctx}/show/view/${show.id}">${show.title}</a></td>
                <td>
                    <fmt:formatDate value="${show.createdTime}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
                </td>
                <td><a href="${ctx}/admin/show/update/${show.id}">更新</a>
                <a href="${ctx}/admin/show/delete/${show.id}">删除</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </shiro:hasRole> --%>

</body>
</html>