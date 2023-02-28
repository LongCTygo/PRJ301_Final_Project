<%-- 
    Document   : index
    Created on : Feb 28, 2023, 10:21:18 PM
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
        <h1>Hello Admin! You are <%= session.getAttribute("cid") %>.</h1>
    </body>
</html>
