<%-- 
    Document   : viewCustomer
    Created on : Mar 1, 2023, 9:53:46 AM
    Author     : ADMIN
--%>

<%@page import="entity.Review"%>
<%@page import="utils.ServletUtil"%>
<%@page import="display.ProductDisplay"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vector<Review> list = (Vector<Review>) request.getAttribute("list");
    String query = (String) request.getParameter("query");
    if (query == null){
    query = "";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Product</title>
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
                            <th scope="col">Cid</th>
                            <th scope="col">Pid</th>
                            <th scope="col">Date</th>
                            <th scope="col">Score</th>
                            <th scope="col">Comment</th>
                            <th scope="col">Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Review cus : list) {%>
                        <tr>
                            <td><%= cus.getCid()%></td>
                            <td><%= cus.getPid()%></td>
                            <td><%= cus.getDate() %></td>
                            <td><%= cus.getScore() %></td>
                            <td><%= cus.getReview() %></td>
                            <td class="text-center"><a href="ReviewController?go=delete&pid=<%=cus.getPid()%>&cid=<%=cus.getCid()%>" type="button" class="btn btn-danger">Remove</a></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <div class="col-3"> 
                <form action="ReviewController">
                    <!-- Search -->
                    <div class="input-group mb-3">

                        <input type="hidden" name="go" value="view">
                        <input name="query" type="text" value="<%=query%>" class="form-control" placeholder="Name" aria-label="Name" aria-describedby="button-addon2">
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary btn-success" type="submit" id="button-addon2">Search</button>
                        </div>
                    </div>
                </form>

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
