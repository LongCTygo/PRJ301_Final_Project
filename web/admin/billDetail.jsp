<%-- 
    Document   : index
    Created on : Feb 28, 2023, 10:21:18 PM
    Author     : ADMIN
--%>

<%@page import="entity.Bill"%>
<%@page import="display.BillDetailDisplay"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vector<BillDetailDisplay> list = (Vector<BillDetailDisplay>) request.getAttribute("list");
    Bill bill = (Bill) request.getAttribute("bill");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bill Detail</title>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <jsp:include page="adminnavbar.jsp"></jsp:include>
            <div class="container">
                <div class="row">
                    <div class="col-8">
                        <table class="table table-hover table-sm table-striped">
                            <thead class="thead-dark ">
                                <tr>
                                    <th scope="col">Product ID</th>
                                    <th scope="col">Product Name</th>
                                    <th scope="col">Image</th>
                                    <th scope="col">Buy Quantity</th>
                                    <th scope="col">Buy Price</th>
                                    <th scope="col">Subtotal</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for (BillDetailDisplay b : list) {%>
                            <tr>
                                <td scope="row"><%= b.getPid()%></td>
                                <td><%= b.getPname()%>  </td>
                                <td><img src="img/<%= b.getImage()%>" class="img-fluid" width="100px"/></td>
                                <td><%= b.getBuyQuantity()%></td>
                                <td><%= b.getBuyPrice()%></td>
                                <td><%= b.getSubtotal()%></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>
                <div class="col-4">
                    admin
                </div>
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
