<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		$("#showStatus").val('COMPLETED');
		$("#showForm").submit();
	};

   function saveFormData() {
	    $("#showStatus").val('DRAFT');
        $("#showForm").submit();
    };
	
	function uploadFileData() {
		//   $('#result').html('');
        alert("123");
		var oMyForm = new FormData();
		oMyForm.append("file", file.files[0]);

		$.ajax({
			url : '${ctx}/admin/file/upload',
			data : oMyForm,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'POST',
			success : function(data) {
				var li = "<li><span>" + data + "</span><a href='#' onclick='javascript:insertImage(this);return false;'>[插入]</a></li>";
				$('#ol_img').append(li);
			},
			error : function(data) {
				alert("上传失败，请重试");
			}
		});
	}
</script>

</head>

<body>
    <%--<textarea class="pageText" id="text1"></textarea>  
  <textarea class="ddddpageText" id="text2">111111</textarea>  
  <input type="button" value="adfasdfsadf" id="aa"/>  --%>
    <form method="post" action="${ctx}/admin/show/save"
        accept-charset="UTF-8" role="form" id="showForm">
        <fieldset>
            <legend>发表演唱会信息</legend>
            <div class="control-group">
                <label for="title" class="control-label">标题</label>
                <div class="controls">
                    <input type="text" style="width: 100%" id="title"
                        name="title" value="${show.title}"
                        class="input-large required" />
                </div>
            </div>
            <div class="control-group">
                <label for="content" class="control-label">内容</label>
                <div class="controls">
                    <textarea id="content" name="content"
                        style="width: 100%; height: 550px">${show.content}</textarea>
                </div>
            </div>
            <input type="hidden" name="id" value="${show.id}" />
            <input type="hidden" name="status" value="${show.status}" />
        </fieldset>
    </form>

    <fieldset style="margin-top: 10px; padding: 4px 10px 10px 10px;">
        <legend>上传图片</legend>
        <table border="0" cellpadding="0" cellspacing="0"
            style="width: 100%;">
            <tr>
                <td valign="top">
                    <ol id="ol_img"></ol>
                    <p id="p_err" style="color: Red;"></p>
                </td>
                <td rowspan="2" valign="top" style="width: 240px;">
                    <div
                        style="border: solid 1px #999; background-color: #f0f0f0; font-size: 11px; padding-left: 10px;">
                        <p>
                            1、图片大小不能超过<b>2M</b>
                        </p>
                        <p>2、支持格式：.jpg .gif .png .bmp</p>
                    </div>
                </td>
            </tr>
            <tr>
                <td valign="bottom"><input type="file" name="file" id="file" />
                    <input type="button" value="上传"
                    style="padding: 2px 4px;"
                    onclick="uploadFileData();" /></td>
            </tr>
        </table>
    </fieldset>

    <div class="form-actions">
        <input id="submit_btn" class="btn btn-primary" type="button"
            onclick="publishFormData();" value="提交" />&nbsp;
        <input id="submit_btn" class="btn" type="button" value="保存" onclick="saveFormData();"/>
    </div>

</body>

</html>