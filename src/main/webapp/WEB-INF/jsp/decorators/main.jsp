<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
    prefix="decorator"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="lytz system">
<meta name="author" content="88993531@qq.com">
<title><decorator:title default="Welcome to lytz system" /></title>

<!-- Bootstrap Core CSS -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">

<!-- jQuery -->
<script src="${ctx}/js/jquery-1.11.3.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${ctx}/js/bootstrap.min.js"></script>

<script>
$(document).ready(function(){
    $("#signup_link").click(function(){
        $("#signup_img").attr("src", '${ctx}/kaptcha.jpg?signup=' + Math.random());
        $("#signupModal").modal();
    });
    <shiro:guest>
    $("#signin_link").click(function(){
    	$("#signin_img").attr("src", '${ctx}/kaptcha.jpg?signin=' + Math.random());
        $("#signinModal").modal();
    });
    </shiro:guest>
});
</script>
<style>
.lytz_spacer {
    margin-top: 50px;
}
</style>
<decorator:head />
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/signup.jsp"%>

                   <shiro:guest>
                   <%@ include file="/WEB-INF/jsp/includes/signin.jsp"%>
                      
                      </shiro:guest>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top"
        role="navigation">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle"
                    data-toggle="collapse" data-target="#lytz-navbar">
                    <span class="sr-only">Toggle navigation</span> <span
                        class="icon-bar"></span> <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">LYTZ</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="lytz-navbar">
                <ul class="nav navbar-nav">
                    <li><a href="#">About</a></li>
                    <li><a href="#">Services</a></li>
                    <li><a href="#">Contact</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                   <li><a id="signup_link" href="#"><fmt:message key="signup.title"/></a></li>
                   <shiro:guest>
                   <li><a id="signin_link" href="#"><fmt:message key="login.title"/></a></li>
                   </shiro:guest>
                   <shiro:user>
                   <li><a href="${ctx}/logout"><fmt:message key="user.logout"/></a></li>
                   </shiro:user>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
    
    <!-- Page Content -->
    
        <decorator:body />
    
        <hr>
        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; LYTZ Website 2015</p>
                </div>
            </div>
        </footer>

</body>
</html>