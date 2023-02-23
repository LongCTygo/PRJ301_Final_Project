<%-- 
    Document   : categoriesTab
    Created on : Feb 22, 2023, 5:35:32 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.DAOProduct,entity.Product,java.util.Vector,dao.DAOCategory,entity.Category"%>
<%
    DAOCategory daoCat = new DAOCategory();
    Vector<Category> vecCat = daoCat.getAll();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <div class="col-lg-3 d-none d-lg-block">
        <a class="btn shadow-none d-flex align-items-center justify-content-between bg-primary text-white w-100" data-toggle="collapse" href="#navbar-vertical" style="height: 65px; margin-top: -1px; padding: 0 30px;">
            <h6 class="m-0">Categories</h6>
            <i class="fa fa-angle-down text-dark"></i>
        </a>
        <nav class="collapse position-absolute navbar navbar-vertical navbar-light align-items-start p-0 border border-top-0 border-bottom-0 bg-light" id="navbar-vertical" style="width: calc(100% - 30px); z-index: 1;">
            <div class="navbar-nav w-100 overflow-hidden" style="height: <%=vecCat.size()*40%>px">
                <%
                    for (Category cat: vecCat){
                %>
                <a href="ClientController?go=listShop&cateid=<%=cat.getCateId()%>" class="nav-item nav-link"><%=cat.getCateName()%></a>
                <%}%>
            </div>
        </nav>
    </div>
</html>
