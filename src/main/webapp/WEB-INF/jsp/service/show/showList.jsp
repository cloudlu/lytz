<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>演唱会资讯</title>
</head>

<body>
    <shiro:guest>
        <div class="container  col-md-12 lytz_spacer">
    </shiro:guest>



    <div class="panel panel-info">
        <div class="panel-heading clearfix">
            <h4 class="panel-title pull-left">演唱会</h4><shiro:hasRole name="ROLE_ADMIN">
                <a href="${ctx}/admin/show/new" title="添加"><span
                            class="glyphicon glyphicon-plus" ></span></a> 
                       </shiro:hasRole>
            <c:if test="${not empty message}">
                <div id="message" class="alert alert-success">
                    <button data-dismiss="alert" class="close">×</button>${message}</div>
            </c:if>
            <shiro:hasRole name="ROLE_ADMIN">
                <div class="btn-group pull-right">
                    <form class="form-inline pull-right" action="${ctx}/show">
                      <div class="form-group">
                        <label class="sr-only" for="searchText">关键字</label>
                        <input type="text" class="form-control" id="searchText" name="keyword" placeholder="关键字" value="${showQuery.keyword}"/>
                      </div>
                      <div class="form-group">
                        <label class="sr-only" for="status">显示数目</label>
                        <input type="text" class="form-control" id="querySize" name="querySize" placeholder="显示数目" value="${showQuery.querySize}"/>
                      </div>
                      <div class="form-group">
                        <label for="status">状态</label>
                        <select class="form-control" id="status" name="status">
                            <c:choose>
                                <c:when test="${showQuery.status eq 'COMPLETED'}">
                                    <option value="">全部</option>
                                    <option value ="DRAFT">草稿</option>
                                    <option value ="COMPLETED" selected="selected">已发布</option>
                                </c:when>
                                <c:when test="${showQuery.status eq 'DRAFT'}">
                                    <option value="">全部</option>
                                    <option value ="DRAFT" selected="selected">草稿</option>
                                    <option value ="COMPLETED">已发布</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="" selected="selected">全部</option>
                                    <option value ="DRAFT">草稿</option>
                                    <option value ="COMPLETED">已发布</option>
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
                <c:forEach items="${shows}" var="show">
                    <li class="list-group-item"><shiro:hasRole
                            name="ROLE_ADMIN">
                                <a class="badge"
                                    href="${ctx}/admin/show/delete/${show.id}"><span
                                    class="glyphicon glyphicon-minus">删除</span></a>
                                <a class="badge"
                                    href="${ctx}/admin/show/update/${show.id}"><span
                                    class="glyphicon glyphicon-edit">修改</span></a>
                        </shiro:hasRole> <a href="${ctx}/show/view/${show.id}">${show.title}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="panel-footer">
            <p class="pull-left">第 ${showPager.currentPage } 页 共 ${showPager.totalPages } 页</p>
            <ul class="pagination pull-right" style="margin: -2px">
                <%--<c:choose>
                    <c:when test="${showPager.previousExists}">
                        <li class="disabled"
                    </c:when>
                </c:choose> --%>
                <li <c:if test="${not showPager.previousExists}"> class="disabled"</c:if>><a href="${ctx}/show/list?pageNum=${showPager.firstPage }" aria-label="First"> <span
                        aria-hidden="true">&laquo;</span>
                </a></li>
                <c:if test="${showPager.previousMoreThanOffset}"><li><a href="#">...</a></li></c:if>
                <c:forEach var="displayPageNum" items="${showPager.displayPages}">
                    <c:choose>
                        <c:when test="${displayPageNum eq showPager.currentPage }">
                            <li class="active"><a href="${ctx}/show/list?pageNum=${showPager.currentPage }">${showPager.currentPage }<span class="sr-only">(current)</span></a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${ctx}/show/list?pageNum=${displayPageNum}">${displayPageNum}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${showPager.nextMoreThanOffset}"><li><a href="#">...</a></li></c:if>
                <li <c:if test="${not showPager.nextExists}"> class="disabled"</c:if>><a href="${ctx}/show/list?pageNum=${showPager.lastPage }" aria-label="Last"> <span
                        aria-hidden="true">&raquo;</span>
                </a></li>
            </ul>
            <div class="clearfix"></div>
        </div>
    </div>




    <shiro:guest>
        </div>
    </shiro:guest>

    <%--<shiro:hasRole name="ROLE_ADMIN">
    
    
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead><tr><th>演唱会标题</th><th>创建时间</th><th>管理</th></tr></thead>
        <tbody>
        <c:forEach items="${shows}" var="show">
            <tr>
                <td><a href="${ctx}/show/view/${show.id}">${show.title}</a></td>
                <td>
                    <fmt:formatDate value="${show.createdTime}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
                </td>
                <td><a href="${ctx}/admin/show/update/${show.id}">更新</a>
                <a href="${ctx}/admin/show/delete/${show.id}">删除</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </shiro:hasRole> --%>

</body>
</html>