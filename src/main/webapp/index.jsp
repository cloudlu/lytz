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
    margin-top: 50px;
}

.lytz-form-signin {
    max-width: 200px;
    padding: 15px;
    margin: 0 auto;
    position: absolute;
    right: 0px;
}

.lytz-form-signin .lytz-form-signin-heading,.lytz-form-signin .checkbox
    {
    margin-bottom: 10px;
}

.lytz-form-signin .checkbox {
    font-weight: normal;
}

.lytz-form-signin .form-control {
    position: relative;
    height: auto;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    padding: 10px;
    font-size: 16px;
}

.lytz-form-signin .form-control:focus {
    z-index: 2;
}

.lytz-form-signin input[type="email"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
}

.lytz-form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
}
</style>
<body>

    <shiro:guest>
        <div class="lytz-spacer">
            <form class="lytz-form-signin" method="post"
                action="${ctx}/login" role="form">
                <h2 class="lytz-form-signin-heading">Please sign in</h2>
                <div class="form-group">
                    <label for="username">Username</label> <input
                        type="text" id="username" name="username"
                        class="form-control" placeholder="Username"
                        required autofocus>
                </div>
                <div class="form-group">
                    <label for="password">Password</label> <input
                        type="password" id="password" name="password"
                        class="form-control" placeholder="Password"
                        required>
                </div>
                <div class="form-group">
                    <img src="${ctx}/kaptcha.jpg" class="img-thumbnail"
                        alt="refresh if u can't see the picture" /> <input
                        type="text" name="captcha" class="form-control"
                        placeholder="Captcha" name="captcha" required>
                </div>
                <div class="checkbox">
                    <label> <input type="checkbox"
                        value="rememberMe"> Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block"
                    type="submit">Sign in</button>
            </form>
        </div>
    </shiro:guest>

    <!-- Jumbotron Header -->
    <header class="jumbotron hero-spacer">
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

    <hr>

    <!-- Title -->
    <div class="row">
        <div class="col-lg-12">
            <h3>Latest Features</h3>
        </div>
    </div>
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