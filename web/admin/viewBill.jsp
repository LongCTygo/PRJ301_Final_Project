<%-- 
    Document   : viewBill
    Created on : Mar 1, 2023, 9:53:46 AM
    Author     : ADMIN
--%>

<%@page import="utils.ServletUtil"%>
<%@page import="display.BillDisplay"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vector<BillDisplay> list = (Vector<BillDisplay>) request.getAttribute("list");
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
        <title>View Bill</title>
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
                            <th scope="col">Bill ID</th>
                            <th scope="col">Customer Name</th>
                            <th scope="col">Date</th>
                            <th scope="col">Total</th>
                            <th scope="col">Status</th>
                            <th scope="col" colspan="2" class="text-center">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (BillDisplay cus : list) {%>
                        <tr>
                            <td scope="row"><%= cus.getBid()%></td>
                            <td><%= cus.getCname()%>  </td>
                            <td><%= cus.getDateCreate()%></td>
                            <td><%= cus.getTotalMoney()%></td>
                            <td class="text-center"><%= status(cus.getStatus()) %></td>
                            <td class="text-center"><a href="BillDetailController?go=viewDetail&id=<%= cus.getBid()%>" type="button" class="btn btn-primary">Detail</a></td>
                            <td class="text-center"><a href="BillController?go=delete&id=<%= cus.getBid()%>" type="button" class="btn btn-danger">Remove</a></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <div class="col-3"> 
                <form action="BillController">
                    <!-- Search -->
                    <div class="input-group mb-3">

                        <input type="hidden" name="go" value="view">
                        <input name="query" type="text" value="<%=query%>" class="form-control" placeholder="Name" aria-label="Name" aria-describedby="button-addon2">
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
                        <input class="form-check-input" type="radio" name="status" value="0" id="Wait" <%= s == 0 ? "checked" : ""%>>
                        <label class="form-check-label" for="Wait">
                            Wait
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" value="1" id="Process" <%= s == 1 ? "checked" : ""%>>
                        <label class="form-check-label" for="Process">
                            Process
                        </label>
                    </div>
                        <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" value="2" id="Finished" <%= s == 2 ? "checked" : ""%>>
                        <label class="form-check-label" for="Finished">
                            Finished
                        </label>
                    </div>
                </form>
<!--                <a href="BillController?go=add" type="button" class="btn btn-primary mt-2">Add New Product</a>-->

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

<%!
    private String status(int s) {
        if (s == 1) {
            return "<span class=\"badge badge-pill badge-info\">Process</span>";
        } else if (s==2){
            return "<span class=\"badge badge-pill badge-success\">Done</span>";
}
        return "<span class=\"badge badge-pill badge-warning\">Wait</span>";
    }
%>