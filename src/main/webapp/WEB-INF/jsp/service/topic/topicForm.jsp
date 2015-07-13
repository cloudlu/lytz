<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>welcome</title>

<script src="${ctx}/js/tinymce/tinymce.min.js"></script>
<script>
	tinymce
			.init({
				selector : "textarea",
				theme : "modern",
				plugins : [
						"advlist autolink lists link image charmap print preview hr anchor pagebreak",
						"searchreplace wordcount visualblocks visualchars code fullscreen",
						"insertdatetime media nonbreaking save table contextmenu directionality",
						"emoticons template paste textcolor colorpicker textpattern imagetools" ],
				toolbar1 : "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
				toolbar2 : "print preview media | forecolor backcolor emoticons",
				image_advtab : true,
				relative_urls: false,
				language : 'zh_CN',
				templates : [ {
					title : 'Test template 1',
					content : 'Test 1'
				}, {
					title : 'Test template 2',
					content : 'Test 2'
				} ]
			});
</script>

<script>
    function insertImage(e) {
        var url = e.previousSibling.innerHTML;
        var str = "<img src='" + url + "' />";
        alert(str);
        tinymce.get("content").execCommand('mceInsertContent', false, str);
    };

	function publishFormData() {
		$("#topicStatus").val('SUBMITTED');
		$("#topicForm").submit();
	};

   function saveFormData() {
	    $("#topicStatus").val('DRAFT');
        $("#topicForm").submit();
    };
	
</script>

</head>

<body>
    <div class="panel panel-info">
        <div class="panel-heading clearfix">
            <h4 class="panel-title pull-left">发表资讯信息</h4>
            <c:if test="${not empty message}">
                <div id="message" class="alert alert-success">
                    <button data-dismiss="alert" class="close">×</button>${message}</div>
            </c:if>
        </div>
        <div class="panel-body">
            <form:form method="post" action="${ctx}/topic/save"
                accept-charset="UTF-8" role="form" id="topicForm" modelAttribute="topic">
                    <div class="control-group">
                        <label for="title" class="control-label">标题</label><form:errors path="title" cssStyle="color: #ff0000;"/>
                        <div class="controls">
                            <input type="text" style="width: 100%" id="title"
                                name="title" value="${topic.title}"
                                class="input-large required" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="content" class="control-label">内容</label><form:errors path="content" cssStyle="color: #ff0000;"/>
                        <div class="controls">
                            <textarea id="content" name="content"
                                style="width: 100%; height: 550px">${topic.content}</textarea>
                        </div>
                    </div>
                    <input type="hidden" name="id" value="${topic.id}" />
                    <input type="hidden" name="status" id="topicStatus" value="${topic.status}" />
                    <input type="hidden" name="version" id="version" value="${topic.version}" />
                    <input type="hidden" name="createdTime" id="createdTime" value="${topic.createdTime}" />
                    <input type="hidden" name="lastUpdatedTime" id="lastUpdatedTime" value="${topic.lastUpdatedTime}" />
            </form:form>
        </div>
        <div class="panel-footer" style="text-align: center">
         <a class="btn btn-info btn-default" id="submit_btn" onclick="publishFormData();"><span class="glyphicon glyphicon-save">提交</span></a>
                <a class="btn btn-info btn-default" id="submit_btn" onclick="saveFormData();"><span class="glyphicon glyphicon-save">保存</span></a>
             <a class="btn btn-info btn-default" id="cancel_btn" href="${ctx}/topic/list?pageNum=${topicPager.currentPage}"><span class="glyphicon glyphicon-arrow-left">返回</span></a>
               
            <div class="clearfix"></div>
        </div>
    </div>
</body>

</html>