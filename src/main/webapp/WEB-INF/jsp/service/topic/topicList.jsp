<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>资讯信息</title>
</head>

<body>
    <div class="panel panel-info">
        <div class="panel-heading clearfix">
            <h4 class="panel-title pull-left">资讯</h4><shiro:hasRole name="ROLE_ADMIN">
                <a href="${ctx}/topic/new" title="添加"><span
                            class="glyphicon glyphicon-plus" ></span></a> 
                       </shiro:hasRole>
            <c:if test="${not empty message}">
                <div id="message" class="alert alert-success">
                    <button data-dismiss="alert" class="close">×</button>${message}</div>
            </c:if>
            <shiro:hasRole name="ROLE_ADMIN">
                <div class="btn-group pull-right">
                    <form class="form-inline pull-right" action="${ctx}/topic">
                      <div class="form-group">
                        <label class="sr-only" for="searchText">关键字</label>
                        <input type="text" class="form-control" id="searchText" name="keyword" placeholder="关键字" value="${topicQuery.keyword}"/>
                      </div>
                      <div class="form-group">
                        <label class="sr-only" for="status">显示数目</label>
                        <input type="text" class="form-control" id="querySize" name="querySize" placeholder="显示数目" value="${topicQuery.querySize}"/>
                      </div>
                      <div class="form-group">
                        <label for="status">状态</label>
                        <select class="form-control" id="status" name="status">
                            <c:choose>
                                <c:when test="${topicQuery.status eq 'SUBMITTED'}">
                                    <option value="">全部</option>
                                    <option value ="DRAFT">草稿</option>
                                    <option value ="SUBMITTED" selected="selected">已提交</option>
                                </c:when>
                                <c:when test="${topicQuery.status eq 'DRAFT'}">
                                    <option value="">全部</option>
                                    <option value ="DRAFT" selected="selected">草稿</option>
                                    <option value ="SUBMITTED">已发布</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="" selected="selected">全部</option>
                                    <option value ="DRAFT">草稿</option>
                                    <option value ="SUBMITTED">已发布</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                      </div>
                      <button type="submit" class="btn btn-default">查询</button>
                    </form>
                </div>
            </shiro:hasRole>
        </div>
        <div class="panel-body">
            <ul class="list-group">
                <c:forEach items="${topics}" var="topic">
                    <li class="list-group-item"><shiro:hasRole
                            name="ROLE_ADMIN">
                                <a class="badge"
                                    href="${ctx}/topic/delete/${topic.id}"><span
                                    class="glyphicon glyphicon-minus">删除</span></a>
                                <a class="badge"
                                    href="${ctx}/topic/update/${topic.id}"><span
                                    class="glyphicon glyphicon-edit">修改</span></a>
                        </shiro:hasRole> <a href="${ctx}/topic/view/${topic.id}">${topic.title}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="panel-footer">
            <p class="pull-left">第 ${topicPager.currentPage } 页 共 ${topicPager.totalPages } 页</p>
            <ul class="pagination pull-right" style="margin: -2px">
                <li <c:if test="${not topicPager.previousExists}"> class="disabled"</c:if>><a href="${ctx}/topic/list?pageNum=${topicPager.firstPage }" aria-label="First"> <span
                        aria-hidden="true">&laquo;</span>
                </a></li>
                <c:if test="${topicPager.previousMore}"><li><a href="#">...</a></li></c:if>
                <c:forEach var="displayPageNum" items="${topicPager.displayPages}">
                    <c:choose>
                        <c:when test="${displayPageNum eq topicPager.currentPage }">
                            <li class="active"><a href="${ctx}/topic/list?pageNum=${topicPager.currentPage }">${topicPager.currentPage }<span class="sr-only">(current)</span></a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${ctx}/topic/list?pageNum=${displayPageNum}">${displayPageNum}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${topicPager.nextMore}"><li><a href="#">...</a></li></c:if>
                <li <c:if test="${not topicPager.nextExists}"> class="disabled"</c:if>><a href="${ctx}/topic/list?pageNum=${topicPager.lastPage }" aria-label="Last"> <span
                        aria-hidden="true">&raquo;</span>
                </a></li>
            </ul>
            <div class="clearfix"></div>
        </div>
    </div>
</body>
</html>