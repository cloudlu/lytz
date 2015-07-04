<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>welcome</title>
 
<script src="${ctx}/js/tinymce/tinymce.min.js"></script>
<script>
tinymce.init({
    selector: "textarea",
    theme: "modern",
    plugins: [
        "advlist autolink lists link image charmap print preview hr anchor pagebreak",
        "searchreplace wordcount visualblocks visualchars code fullscreen",
        "insertdatetime media nonbreaking save table contextmenu directionality",
        "emoticons template paste textcolor colorpicker textpattern imagetools"
    ],
    toolbar1: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
    toolbar2: "print preview media | forecolor backcolor emoticons",
    image_advtab: true,
    templates: [
        {title: 'Test template 1', content: 'Test 1'},
        {title: 'Test template 2', content: 'Test 2'}
    ]
});
</script> 

</head>

<body>
   <%--<textarea class="pageText" id="text1"></textarea>  
  <textarea class="ddddpageText" id="text2">111111</textarea>  
  <input type="button" value="adfasdfsadf" id="aa"/>  --%> 
  <form method="post" action="${ctx}/admin/show/update">
    <input name="title" type="text"></input>
    <textarea name="content" style="width:100%"></textarea>
    
</form>

   <form id="form2" method="post" action="/spring-mvc-file-upload/rest/cont/upload" enctype="multipart/form-data">
  <!-- File input -->    
  <input name="file2" id="file2" type="file" /><br/>
</form>
</body>

</html>