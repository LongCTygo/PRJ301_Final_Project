<%-- 
    Document   : navigationLogin.jsp
    Created on : Feb 23, 2023, 8:09:34 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String current = (String) request.getParameter("current");
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
                <a href="index.html" class="nav-item nav-link <%if (current.equals("home")){%>active<%}%>">Home</a>
                <a href="shop.html" class="nav-item nav-link <%if (current.equals("shop")){%>active<%}%>">Shop</a>
                <a href="detail.html" class="nav-item nav-link <%if (current.equals("detail")){%>active<%}%>">Shop Detail</a>
                <div class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Pages</a>
                    <div class="dropdown-menu rounded-0 m-0">
                        <a href="cart.html" class="dropdown-item <%if (current.equals("cart")){%>active<%}%>">Shopping Cart</a>
                        <a href="checkout.html" class="dropdown-item <%if (current.equals("checkout")){%>active<%}%>">Checkout</a>
                    </div>
                </div>
                <a href="contact.html" class="nav-item nav-link <%if (current.equals("contact")){%>active<%}%>">Contact</a>
            </div>
            
            <!-- Login -->
            <div class="navbar-nav ml-auto py-0">
                <a href="" class="nav-item nav-link">Login</a>
                <a href="" class="nav-item nav-link">Register</a>
            </div>
        </div>
    </body>
</html>
