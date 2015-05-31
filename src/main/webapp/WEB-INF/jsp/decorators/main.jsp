<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
    prefix="decorator"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- jQuery -->
<script src="js/jquery-1.11.3.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<decorator:head />
</head>
<body>
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
                   <li class="dropdown">
                        <a class="dropdown-toggle" href="#" data-toggle="dropdown"><span
                            class="glyphicon glyphicon-user"></span>Sign Up <strong class="caret"></strong></a>
                        <div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
                             <form method="post"
                                action="${ctx}/signup" role="form"  accept-charset="UTF-8">
                                <input style="margin-bottom: 15px;" type="text" placeholder="Username" id="username" name="username" required>
                                <input style="margin-bottom: 15px;" type="password" placeholder="Password" id="password" name="password" required>
                                <input type="password" id="confirmPassword" style="margin-bottom: 15px;"
                                        name="confirmPassword" placeholder="Confirm Password" required>
                                <img src="${ctx}/kaptcha.jpg" class="img-thumbnail"
                                        alt="refresh if u can't see the picture" /> <input
                                        type="text" name="captcha" style="margin-bottom: 15px;"
                                        placeholder="Captcha" name="captcha" required>
                               
                                <button class="btn btn-primary btn-block"
                                    type="submit">Sign in</button>
                                <label style="text-align:center;margin-top:5px">or</label>
                                <input class="btn btn-primary btn-block" type="button" id="sign-in-google" value="Sign In with QQ">
                                <input class="btn btn-primary btn-block" type="button" id="sign-in-twitter" value="Sign In with WeChat">    
                            </form>
                        </div>
                      </li>
                            <shiro:guest>
                  
                      <li class="dropdown">
                        <a class="dropdown-toggle" href="#" data-toggle="dropdown"><span
                            class="glyphicon glyphicon-log-in"></span>Sign In <strong class="caret"></strong></a>
                        <div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
                             <form method="post"
                                action="${ctx}/login" role="form"  accept-charset="UTF-8">
                                <input style="margin-bottom: 15px;" type="text" placeholder="Username" id="username" name="username" required>
                                <input style="margin-bottom: 15px;" type="password" placeholder="Password" id="password" name="password" required>
                                <img src="${ctx}/kaptcha.jpg" class="img-thumbnail"
                                        alt="refresh if u can't see the picture" /> <input
                                        type="text" name="captcha" style="margin-bottom: 15px;"
                                        placeholder="Captcha" name="captcha" required>
                                <label class="string optional" for="user_remember_me">  Remember me
                                    </label>
                                    <input type="checkbox" id="user_remember_me" style="float: left; margin-right: 10px;"
                                        name="rememberMe" value="true">
                                <button class="btn btn-primary btn-block"
                                    type="submit">Sign in</button>
                                <label style="text-align:center;margin-top:5px">or</label>
                                <input class="btn btn-primary btn-block" type="button" id="sign-in-google" value="Sign In with QQ">
                                <input class="btn btn-primary btn-block" type="button" id="sign-in-twitter" value="Sign In with WeChat">    
                            </form>
                        </div>
                      </li>
                      </shiro:guest>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
    
    <!-- Page Content -->
    <div class="container">
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
    </div>
</body>
</html>