<%@ include file="/WEB-INF/jsp/includes/common.jspf"%>
<!DOCTYPE HTML>
<html>
<head>

<%@ include file="/WEB-INF/jsp/includes/header.jspf"%>
<decorator:head />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/includes/nav.jspf"%>
    <!-- Page Content -->
    
        <div class="container col-md-12 lytz_spacer">
        <!-- Sidebar -->
        <div id="sidebar-wrapper" class="col-md-1" >
            <ul class="nav">
                <shiro:hasRole name="ROLE_ADMIN">
                <li>
                    <a href="${ctx}/admin/user">User Management</a>
                </li>
                <li>
                    <a href="${ctx}/metrics">System Metrics</a>
                </li>
                <li>
                    <a href="${ctx}/simon-console">System Monitor</a>
                </li>
                </shiro:hasRole>
                <shiro:hasAnyRoles name="ROLE_ADMIN,ROLE_VIP">
                <li>
                    <a href="#">Services</a>
                </li>
                <li>
                    <a href="#">Events</a>
                </li>
                </shiro:hasAnyRoles>
                
                <shiro:hasAnyRoles name="ROLE_ADMIN,ROLE_VIP,ROLE_USER">
                <li>
                    <a href="#">Services</a>
                </li>
                </shiro:hasAnyRoles>
                
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper" class="col-md-11">
            
                        <h1>Simple Sidebar</h1>
                        <p>This template has a responsive menu toggling system. The menu will appear collapsed on smaller screens, and will appear non-collapsed on larger screens. When toggled using the button below, the menu will appear/disappear. On small screens, the page content will be pushed off canvas.</p>
                        <p>Make sure to keep all page content within the <code>#page-content-wrapper</code>.</p>
                        <a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a>
                
        </div>
        <!-- /#page-content-wrapper -->

</div>
    
<%@ include file="/WEB-INF/jsp/includes/footer.jspf"%>

</body>
</html>