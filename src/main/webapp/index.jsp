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
<title>welcome</title>
</head>
<style>
.lytz_spacer {
    margin-top: 100px;
}
</style>
<body>
    

    <!-- Jumbotron Header -->
    <header class="jumbotron lytz_spacer">
        <h1>A Warm Welcome!</h1>
        <p>LYTZ, your choice to success for your future.</p>
        <shiro:user>
            <shiro:principal />, how are you today?
            <shiro:authenticated>
                <shiro:hasRole name="admin">
                    <a href="${ctx}/simon-console/index.html">monitor the system</a>
                </shiro:hasRole>
            </shiro:authenticated>
            <a href="${ctx}/logout">click here to logout</a>
        </shiro:user>
        
    </header>
    
    <!-- /.row -->

    <!-- Page Features -->
    <div class="row text-center">

        <div class="col-md-6 col-sm-6 hero-feature">
            <div class="thumbnail">
                <img src="http://placehold.it/800x500" alt="">
                <div class="caption">
                    <h3>Feature Label</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur
                        adipisicing elit.</p>
                    <p>
                        <a href="#" class="btn btn-primary">Buy Now!</a>
                        <a href="#" class="btn btn-default">More
                            Info</a>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-md-6 col-sm-6 hero-feature">
            <div class="thumbnail">
                <img src="http://placehold.it/800x500" alt="">
                <div class="caption">
                    <h3>Feature Label</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur
                        adipisicing elit.</p>
                    <p>
                        <a href="#" class="btn btn-primary">Buy Now!</a>
                        <a href="#" class="btn btn-default">More
                            Info</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
    <div class="row text-center">
        <div class="col-md-6 col-sm-6 hero-feature">
            <div class="thumbnail">
                <img src="http://placehold.it/800x500" alt="">
                <div class="caption">
                    <h3>Feature Label</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur
                        adipisicing elit.</p>
                    <p>
                        <a href="#" class="btn btn-primary">Buy Now!</a>
                        <a href="#" class="btn btn-default">More
                            Info</a>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-md-6 col-sm-6 hero-feature">
            <div class="thumbnail">
                <img src="http://placehold.it/800x500" alt="">
                <div class="caption">
                    <h3>Feature Label</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur
                        adipisicing elit.</p>
                    <p>
                        <a href="#" class="btn btn-primary">Buy Now!</a>
                        <a href="#" class="btn btn-default">More
                            Info</a>
                    </p>
                </div>
            </div>
        </div>

    </div>

</body>

</html>