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
            <h4 class="panel-title pull-left">演唱会</h4>
            <c:if test="${not empty message}">
                <div id="message" class="alert alert-success">
                    <button data-dismiss="alert" class="close">×</button>${message}</div>
            </c:if>
            <shiro:hasRole name="ROLE_ADMIN">
                <div class="btn-group pull-right">
                    <form class="form-inline pull-right" action="${ctx}/show">
                      <div class="form-group">
                        <label class="sr-only" for="searchText">关键字</label>
                        <input type="text" class="form-control" id="searchText" name="keyword" placeholder="关键字">
                      </div>
                      <div class="form-group">
                        <label class="sr-only" for="status">状态</label>
                        <input type="text" class="form-control" id="status" name="status" placeholder="状态">
                      </div>
                      <button type="submit" class="btn btn-default">查询</button>
                    </form>
                </div>
            </shiro:hasRole>
        </div>
        <div class="panel-body">
            <shiro:hasRole name="ROLE_ADMIN">
                <a href="${ctx}/admin/show/new"><span
                            class="glyphicon glyphicon-plus" >添加</span></a> 
                       </shiro:hasRole>
              <%--  <p><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>添加</button></p> --%>
              
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
            <ul class="pagination pull-right" style="margin: -2px">
                <li><a href="#" aria-label="Previous"> <span
                        aria-hidden="true">&laquo;</span>
                </a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#" aria-label="Next"> <span
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