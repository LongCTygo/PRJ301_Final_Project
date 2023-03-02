<%-- 
    Document   : viewCustomer
    Created on : Mar 1, 2023, 9:53:46 AM
    Author     : ADMIN
--%>

<%@page import="utils.ServletUtil"%>
<%@page import="entity.Customer"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vector<Customer> list = (Vector<Customer>) request.getAttribute("list");
    String query = (String) request.getAttribute("query");
    Integer s = (Integer) request.getAttribute("query_status");
    if (s == null) {
        s = -1;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Customer</title>
        <link rel="stylesheet" href="css/style.css"/>
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="adminnavbar.jsp"></jsp:include>
            <div class="row mt-5">
                <div class="col-9">
                <%
                    ServletUtil.printErrorMessages(request, out);
                    ServletUtil.printSuccessMessages(request, out);
                %>
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
                            </td>
                            <td><%= cus.getPassword()%></td>
                            <td><%= cus.getAddress()%></td>
                            <td><%= cus.getPhone()%></td>
                            <td><%= cus.getStatus() == 1 ? "Enabled" : "Disabled"%></td>
                            <td class="text-center"><a href="CustomerController?go=update&id=<%= cus.getCid()%>" type="button" class="btn btn-info">Update</a></td>
                            <td class="text-center"><a href="CustomerController?go=delete&id=<%= cus.getCid()%>" type="button" class="btn btn-danger">Remove</a></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <div class="col-3"> 
                <form action="CustomerController">
                    <!-- Search -->
                    <div class="input-group mb-3">

                        <input type="hidden" name="go" value="view">
                        <input name="query" type="text" value="<%=query%>" class="form-control" placeholder="Name or Username" aria-label="Name or Username" aria-describedby="button-addon2">
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary btn-success" type="submit" id="button-addon2">Search</button>
                        </div>
                    </div>
                    <!-- Radio -->
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" value="-1" id="All" <%= s == -1 ? "checked" : ""%>>
                        <label class="form-check-label" for="All">
                            All
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" value="0" id="Enabled" <%= s == 0 ? "checked" : ""%>>
                        <label class="form-check-label" for="Enabled">
                            Enabled
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" value="1" id="Disabled" <%= s == 1 ? "checked" : ""%>>
                        <label class="form-check-label" for="Disabled">
                            Disabled
                        </label>
                    </div>
                </form>
                <a href="CustomerController?go=add" type="button" class="btn btn-primary mt-2">Add New Customer</a>

            </div>
        </div>
        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Contact Javascript File -->
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>
</html>
