<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>资讯详细信息</title>
</head>

<body>
    <div class="panel panel-info">
        <div class="panel-heading clearfix">
            <h4 class="panel-title pull-left">${topic.title}</h4>
            <hr><h6><fmt:formatDate value="${topic.lastUpdatedTime}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></h6>
        </div>
        <div class="panel-body">
            ${topic.content}
        </div>
        <div class="panel-footer">
            <a href="${ctx}/topic/list?pageNum=${topicPager.currentPage}" id="cancel_btn" class="btn btn-info" role="button">返回</a>
            <a href="${ctx}/topic/update/${topic.id}" class="btn btn-info" role="button">更新</a>
            <a href="${ctx}/topic/delete/${topic.id}" class="btn btn-info" role="button">删除</a>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-heading clearfix">
            <h4 class="panel-title pull-left">注释</h4>
        </div>
        <div class="panel-body">
            <c:forEach var="comment" items="${topic.comments }">
                <p>
                    ${comment.lastUpdateTime } ${comment.content }
                </p>
            </c:forEach>
            <hr/>
            <form:form method="post" action="${ctx}/topic/addComment"
                accept-charset="UTF-8" role="form" id="commentForm" modelAttribute="comment">
                    <div class="control-group">
                        <label for="content" class="control-label">内容</label><form:errors path="content" cssStyle="color: #ff0000;"/>
                        <div class="controls">
                            <textarea style="width: 100%, height: 50px" id="content"
                                name="content"
                                class="input-large required"></textarea>
                        </div>
                    </div>
                    <input type="hidden" name="id" value="${comment.id}" />
                    <input type="hidden" name="topic.id" id="topic.id" value="${topic.id}" />
                    <input type="hidden" name="version" id="version" value="${comment.version}" />
                    <input type="hidden" name="createdTime" id="createdTime" value="${comment.createdTime}" />
                    <input type="hidden" name="lastUpdatedTime" id="lastUpdatedTime" value="${comment.lastUpdatedTime}" />
                    <button type="submit">增加注释</button>
            </form:form>
        </div>
        
    </div>    
</body>
</html>