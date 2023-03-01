<%-- 
    Document   : viewCustomer
    Created on : Mar 1, 2023, 9:53:46 AM
    Author     : ADMIN
--%>

<%@page import="entity.Customer"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vector<Customer> list = (Vector<Customer>) request.getAttribute("list");
    String query = (String) request.getAttribute("query");
    Integer s = (Integer) request.getAttribute("status");
    if (s==null){
        s = -1;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Customer</title>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <jsp:include page="adminnavbar.jsp"></jsp:include>
            <div class="row mt-5">
                <div class="col-9">
                    <table class="table table-hover table-sm table-striped">
                        <thead class="thead-dark ">
                            <tr>
                                <th scope="col">CID</th>
                                <th scope="col">Name</th>
                                <th scope="col">Username</th>
                                <th scope="col">Password</th>
                                <th scope="col">Address</th>
                                <th scope="col">Phone</th>
                                <th scope="col">Status</th>
                                <th scope="col" colspan="2" class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        <% for (Customer cus : list) {%>
                        <tr>
                            <td scope="row"><%= cus.getCid()%></td>
                            <td><%= cus.getCname()%></td>
                            <td><%= cus.getUsername()%>  
                                <% if (cus.isAdmin()) { %>
                                <span class="badge badge-pill badge-danger">Admin</span>
                                <%}%>
                            </td>
                            <td><%= cus.getPassword()%></td>
                            <td><%= cus.getAddress()%></td>
                            <td><%= cus.getPhone()%></td>
                            <td><%= cus.getStatus() == 1 ? "Enabled" : "Disabled"%></td>
                            <td class="text-center"><a href="" type="button" class="btn btn-info">Update</a></td>
                            <td class="text-center"><a href="" type="button" class="btn btn-danger">Remove</a></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <div class="col-3"> 
                <form action="AdminController">
                    <!-- Search -->
                    <div class="input-group mb-3">

                        <input type="hidden" name="go" value="viewCustomer">
                        <input name="query" type="text" value="<%=query%>" class="form-control" placeholder="Name or Username" aria-label="Name or Username" aria-describedby="button-addon2">
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary btn-success" type="submit" id="button-addon2">Search</button>
                        </div>
                    </div>
                    <!-- Radio -->
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" value="-1" id="All" <%= s==-1?"checked":"" %>>
                        <label class="form-check-label" for="All">
                            All
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" value="0" id="Enabled" <%= s==0?"checked":"" %>>
                        <label class="form-check-label" for="Enabled">
                            Enabled
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" value="1" id="Disabled" <%= s==1?"checked":"" %>>
                        <label class="form-check-label" for="Disabled">
                            Disabled
                        </label>
                    </div>
                </form>
                <a type="button" class="btn btn-primary mt-2">Add New Customer</a>
            </div>
        </div>
        <!-- Table -->

    </body>
</html>
