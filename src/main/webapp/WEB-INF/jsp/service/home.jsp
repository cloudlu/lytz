<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>welcome</title>
<script src="${ctx}/js/tinymce/tinymce.min.js"></script>
<script  src="${ctx}/js/tinymce/jquery.tinymce.min.js"></script>
<script>
tinymce.init({
    selector: "textarea"
 });
</script>  
</head>

<body>
   <%--<textarea class="pageText" id="text1"></textarea>  
  <textarea class="ddddpageText" id="text2">111111</textarea>  
  <input type="button" value="adfasdfsadf" id="aa"/>  --%> 
  <form method="post">
    <textarea></textarea>
</form>
</body>

</html>