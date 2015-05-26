<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>welcome</title>
</head>

<body>

<shiro:guest>
    Hi there!  Please <a href="${ctx}/login">Login</a> or <a href="${ctx}/signup">Signup</a> today!
</shiro:guest>

<shiro:authenticated>
    <a href="${ctx}/logout">click here to logout</a>
</shiro:authenticated>

<shiro:hasRole name="admin">
    <a href="${ctx}/simon-console/index.html">monitor the system</a>
</shiro:hasRole>


</body>

</html>