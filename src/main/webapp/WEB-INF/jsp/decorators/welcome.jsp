<%@ include file="/WEB-INF/jsp/includes/common.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/jsp/includes/header.jspf"%>
<script>
<shiro:guest>
$(document).ready(function(){
	
	$("#signup_link").click(function(){
        $("#signup_img").attr("src", '${ctx}/kaptcha.jpg?signup=' + Math.random());
        $("#signupModal").modal();
    });
    
    $("#signin_link").click(function(){
        $("#signin_img").attr("src", '${ctx}/kaptcha.jpg?signin=' + Math.random());
        $("#signinModal").modal();
    });
   
});
</shiro:guest>
</script>
<decorator:head />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/includes/nav.jspf"%>
    <!-- Page Content -->
    
        <decorator:body />
    
<%@ include file="/WEB-INF/jsp/includes/footer.jspf"%>

</body>
</html>