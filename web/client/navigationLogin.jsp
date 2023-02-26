<%-- 
    Document   : navigationLogin.jsp
    Created on : Feb 23, 2023, 8:09:34 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String go = (String) request.getParameter("go");
    System.out.println(go);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
            <div class="navbar-nav mr-auto py-0">
                <a href="ClientController?go=home" class="nav-item nav-link <%if (go.equals("home")){%>active<%}%>">Home</a>
                <a href="ClientController?go=listShop" class="nav-item nav-link <%if (go.equals("listShop")){%>active<%}%>">Shop</a>
                <a href="ClientController?go=detail" class="nav-item nav-link <%if (go.equals("detail")){%>active<%}%>">Shop Detail</a>
                <div class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Pages</a>
                    <div class="dropdown-menu rounded-0 m-0">
                        <a href="ClientController?go=cart" class="dropdown-item <%if (go.equals("cart")){%>active<%}%>">Shopping Cart</a>
                        <a href="ClientController?go=checkout" class="dropdown-item <%if (go.equals("checkout")){%>active<%}%>">Checkout</a>
                    </div>
                </div>
                <a href="ClientController?go=contact" class="nav-item nav-link <%if (go.equals("contact")){%>active<%}%>">Contact</a>
            </div>
            
            <!-- Login -->
            <div class="navbar-nav ml-auto py-0">
                <a href="" class="nav-item nav-link">Login</a>
                <a href="" class="nav-item nav-link">Register</a>
            </div>
        </div>
    </body>
</html>
