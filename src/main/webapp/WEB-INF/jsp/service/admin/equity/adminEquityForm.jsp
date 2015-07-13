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
        tinymce.get("content").execCommand('mceInsertContent', false, str);
    };

    function publishFormData() {
        $("#equityStatus").val('COMPLETED');
        $("#equityForm").submit();
    };

   function saveFormData() {
        $("#equityStatus").val('DRAFT');
        $("#equityForm").submit();
    };
    
    function uploadFileData() {
        var oMyForm = new FormData();
        var file = $('#file')[0].files[0];
        name = file.name;
        length = file.size;
        type = file.type;
        if(file.name.length < 1) {
        }
        else if(file.size > 8388608) {
            alert("文件太大");
        }
        else if(file.type != 'image/png' && file.type != 'image/bmp' && file.type != 'image/jpg' && file.type != 'image/gif' && file.type != 'image/jpeg' ) {
            alert("文件类型不符合bmp, png, jpg or gif");
        }
        else {
            oMyForm.append("file", file);
            oMyForm.append("name", name);
            oMyForm.append("length", length);
            oMyForm.append("type", type);
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
        };
    }
</script>

</head>

<body>
    <div class="panel panel-info">
        <div class="panel-heading clearfix">
            <h4 class="panel-title pull-left">发表股票内参信息</h4>
            <c:if test="${not empty message}">
                <div id="message" class="alert alert-success">
                    <button data-dismiss="alert" class="close">×</button>${message}</div>
            </c:if>
        </div>
        <div class="panel-body">
            <form:form method="post" action="${ctx}/admin/equity/save"
                accept-charset="UTF-8" role="form" id="equityForm" modelAttribute="equity">
                    <div class="control-group">
                        <label for="title" class="control-label">标题</label><form:errors path="title" cssStyle="color: #ff0000;"/>
                        <div class="controls">
                            <input type="text" style="width: 100%" id="title"
                                name="title" value="${equity.title}"
                                class="input-large required" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="content" class="control-label">内容</label><form:errors path="content" cssStyle="color: #ff0000;"/>
                        <div class="controls">
                            <textarea id="content" name="content"
                                style="width: 100%; height: 550px">${equity.content}</textarea>
                        </div>
                    </div>
                    <input type="hidden" name="id" value="${equity.id}" />
                    <input type="hidden" name="status" id="equityStatus" value="${equity.status}" />
                    <input type="hidden" name="version" id="version" value="${equity.version}" />
                    <input type="hidden" name="createdTime" id="createdTime" value="${equity.createdTime}" />
                    <input type="hidden" name="lastUpdatedTime" id="lastUpdatedTime" value="${equity.lastUpdatedTime}" />
            </form:form>
            <fieldset style="margin-top: 10px; padding: 4px 10px 10px 10px;">
                <legend>上传图片</legend>
                <table
                    style="width: 100%;">
                    <tr>
                        <td valign="top">
                            <ol id="ol_img"></ol>
                            <p id="p_err" style="color: Red;"></p>
                        </td>
                        <td rowspan="2" valign="bottom" style="width: 240px;">
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
                            <a class="pull-right btn btn-info btn-default"
                            onclick="uploadFileData();" ><span class="glyphicon glyphicon-save">上传</span></a></td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <div class="panel-footer" style="text-align: center">
         <a class="btn btn-info btn-default" id="submit_btn" onclick="publishFormData();"><span class="glyphicon glyphicon-save">提交</span></a>
                <a class="btn btn-info btn-default" id="submit_btn" onclick="saveFormData();"><span class="glyphicon glyphicon-save">保存</span></a>
             <a class="btn btn-info btn-default" id="cancel_btn" href="${ctx}/equity/list?pageNum=${equityPager.currentPage}"><span class="glyphicon glyphicon-arrow-left">返回</span></a>
               
            <div class="clearfix"></div>
        </div>
    </div>
</body>

</html>