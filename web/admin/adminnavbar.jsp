<%-- 
    Document   : adminnavbar
    Created on : Mar 1, 2023, 9:55:50 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String admin = (String) session.getAttribute("admin");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <nav class="navbar navbar-light navbar-expand-lg" style="background-color: #e3f2fd;">
            <a class="navbar-brand" href="#">Admin</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-item nav-link" href="AdminViewController">Home</a>
                    <a class="nav-item nav-link" href="ClientController">To Client Page</a>
                    <a class="nav-item nav-link" href="BillController?go=view">Bill</a>
<!--                    <a class="nav-item nav-link" href="BillDetailController?go=view">Bill Detail</a>-->
                    <a class="nav-item nav-link" href="CategoryController?go=view">Category</a>
                    <a class="nav-item nav-link" href="CustomerController?go=view">Customer</a>
                    <a class="nav-item nav-link" href="ProductController?go=view">Product</a>
                    <a class="nav-item nav-link" href="ReviewController?go=view">Review</a>
                </div>
            </div>
            <div class="text-right">
                <% if (admin != null){ %>
                <p>Hello, <%=admin%></p>
                <a href="AdminViewController?go=logout" class="btn-danger"> Logout</a>
                <%}%>
            </div>
        </nav>
    </body>
</html>
