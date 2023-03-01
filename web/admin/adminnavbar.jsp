<%-- 
    Document   : adminnavbar
    Created on : Mar 1, 2023, 9:55:50 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <nav class="navbar navbar-light navbar-expand-lg" style="background-color: #e3f2fd;">
            <a class="navbar-brand" href="#">Navbar w/ text</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-item nav-link" href="AdminController">Home</a>
                    <a class="nav-item nav-link" href="ClientController">To Client Page</a>
                    <a class="nav-item nav-link" href="#">Bill</a>
                    <a class="nav-item nav-link" href="#">Bill Detail</a>
                    <a class="nav-item nav-link" href="#">Category</a>
                    <a class="nav-item nav-link" href="CustomerController?go=view">Customer</a>
                    <a class="nav-item nav-link" href="#">Product</a>
                    <a class="nav-item nav-link" href="#">Review</a>
                </div>
            </div>

        </nav>
    </body>
</html>
