<%-- 
    Document   : navbar.jsp
    Created on : Feb 23, 2023, 8:16:46 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String go = (String) request.getParameter("go");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row border-top px-xl-5">
                <jsp:include page="categoriesTab.jsp"></jsp:include>
                    <div class="col-lg-9">
                        <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 py-lg-0 px-0">
                            <a href="" class="text-decoration-none d-block d-lg-none">
                                <h1 class="m-0 display-5 font-weight-semi-bold"><span class="text-primary font-weight-bold border px-3 mr-1">E</span>Shopper</h1>
                            </a>
                            <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                                <span class="navbar-toggler-icon"></span>
                            </button>
                        <jsp:include page="navigationLogin.jsp">
                            <jsp:param name="current" value="<%=go%>"></jsp:param>
                        </jsp:include>
                    </nav>
                </div>
            </div>
        </div>
    </body>
</html>
