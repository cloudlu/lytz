<%@ page language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title><fmt:message key="429.title"/></title>
    <meta name="heading" content="<fmt:message key='429.title'/>"/>
</head>
<body>
<p>
    <fmt:message key="429.message">
        <fmt:param><c:url value="/"/></fmt:param>
    </fmt:message>
</p>
</body>
</html>