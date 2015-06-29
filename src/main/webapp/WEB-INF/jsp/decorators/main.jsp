<%@ include file="/WEB-INF/jsp/includes/common.jspf"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>

<%@ include file="/WEB-INF/jsp/includes/header.jspf"%>
<decorator:head />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/includes/nav.jspf"%>
    <!-- Page Content -->
    <%-- 后台数据页面生成 --%>
        <div class="container col-md-12 lytz_spacer">
        <!-- Sidebar -->
        <div id="sidebar-wrapper" class="col-md-1" >
            <ul class="nav">
                <shiro:hasRole name="ROLE_ADMIN">
                <li>
                    <a href="${ctx}/admin/user">用户管理</a>
                </li>
                <li>
                    <a href="${ctx}/metrics">系统监控</a>
                </li>
                <li>
                    <a href="${ctx}/simon-console">系统性能</a>
                </li>
                </shiro:hasRole>
                <shiro:hasAnyRoles name="ROLE_ADMIN,ROLE_VIP">
                <li>
                    <a href="#">Events</a>
                </li>
                </shiro:hasAnyRoles>
                
                <shiro:hasAnyRoles name="ROLE_ADMIN,ROLE_VIP,ROLE_USER">
                <li class="nav-header">股票</li>
                <li class="active"><a href="#">内参数据</a></li>
                <li class="active"><a href="#">配资</a></li>
                <li class="nav-header">投融资</li>
                <li class="active"><a href="#">信息公告</a></li>
                <li class="nav-header">演唱会</li>
                <li class="active"><a href="#">信息公告</a></li>
                </shiro:hasAnyRoles>
                
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper" class="col-md-11">
            
            <decorator:body />
                
        </div>
        <!-- /#page-content-wrapper -->

</div>
    
<%@ include file="/WEB-INF/jsp/includes/footer.jspf"%>

</body>
</html>