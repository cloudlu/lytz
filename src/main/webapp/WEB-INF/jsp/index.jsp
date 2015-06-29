<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ page
    import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title><fmt:message key="webapp.name"/></title>
</head>
<body>

    <div class="container  col-md-12 lytz_spacer">
        <!-- Jumbotron Header -->
        <header class="jumbotron">
            <h1>A Warm Welcome!</h1>
            <p>LYTZ, your choice to success for your future.</p>
            <shiro:user>
                <shiro:principal />, how are you today?
            <shiro:authenticated>
                    <shiro:hasRole name="admin">
                        <a href="${ctx}/simon-console/index.html">monitor
                            the system</a>
                    </shiro:hasRole>
                </shiro:authenticated>
                <a href="${ctx}/logout">click here to logout</a>
            </shiro:user>

        </header>

        <!-- /.row -->

        <!-- Page Features -->
        <div class="row text-center">

            <div class="col-md-9 col-sm-9">
                <div class="thumbnail" >
                        <h3>市场新闻</h3>
                    <div class="caption" >
                        
                            <ul>
                                <c:forEach var="item" items="${news}"> 
                                    <c:if test="${not empty item}">
                                        <c:choose>
                                            <c:when test="${not empty item.link}">
                                                <li><a href='<c:out value="${item.link}"/>'><c:out value="${item.title}"/></a></li>
                                            </c:when>
                                            <c:otherwise>
                                               <li><c:out value="${item.title}"/></li>
                                            </c:otherwise>
                                       </c:choose>
                                    </c:if>
                                </c:forEach>
                            </ul> 
                    </div>
                </div>
            </div>
        <!-- </div>
        <div class="row text-center"> -->
            <div class="col-md-3 col-sm-3">
                <div class="thumbnail">
                    <div class="caption">
                        <h3>本站公告</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur
                            adipisicing elit.</p>
                        <p>
                            <a href="#" class="btn btn-primary">Buy
                                Now!</a> <a href="#" class="btn btn-default">More
                                Info</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>