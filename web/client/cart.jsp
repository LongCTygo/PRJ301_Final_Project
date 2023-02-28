<%-- 
    Document   : cart
    Created on : Feb 28, 2023, 11:39:41 AM
    Author     : ADMIN
--%>

<%@page import="java.util.Vector"%>
<%@page import="entity.Product"%>
<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vector<Product> vector = (Vector<Product>) request.getAttribute("cartList");
    double subtotal = 0;
    Vector<String> successes = (Vector<String>) request.getAttribute("success");
    Vector<String> errors = (Vector<String>) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>EShopper - Bootstrap Shop Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free HTML Templates" name="keywords">
        <meta content="Free HTML Templates" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet"> 

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>

    <body>
        <!-- Topbar Start -->
        <jsp:include page="topbar.jsp"></jsp:include>
            <!-- Topbar End -->


            <!-- Navbar Start -->
        <jsp:include page="navbar.jsp">
            <jsp:param name="go" value='<%=(String) (request.getAttribute("go"))%>'></jsp:param>
        </jsp:include>
        <!-- Navbar End -->


        <!-- Page Header Start -->
        <div class="container-fluid bg-secondary mb-5">
            <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
                <h1 class="font-weight-semi-bold text-uppercase mb-3">Shopping Cart</h1>
                <div class="d-inline-flex">
                    <p class="m-0"><a href="ClientController">Home</a></p>
                    <p class="m-0 px-2">-</p>
                    <p class="m-0">Shopping Cart</p>
                </div>
            </div>
        </div>
        <!-- Page Header End -->


        <!-- Cart Start -->
        <div class="container-fluid pt-5">
            <div class="row px-xl-5">
                <div class="col-lg-8 table-responsive mb-5">
                    <div>
                        <!-- Success -->
                        <%
                            if (successes != null) {
                                for (String msg : successes) {
                        %>
                        <div class="alert alert-success alert-dismissible fade show"" role="alert">
                            <strong><%=msg%></strong>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <%}
                            }%>
                        <!-- Error -->
                        <%
                            if (errors != null) {
                                for (String msg : errors) {
                        %>
                        <div class="alert alert-danger alert-dismissible fade show"" role="alert">
                            <strong><%=msg%></strong>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <%}
                            }%>
                    </div>
                    <form action="ClientController" method="post">
                        <input type="hidden" name="go" value="updateCart">
                        <table class="table table-bordered text-center mb-0">
                            <thead class="bg-secondary text-dark">
                                <tr>
                                    <th>Products</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Total</th>
                                    <th>Remove</th>
                                </tr>
                            </thead>
                            <tbody class="align-middle">
                                <% for (Product pro : vector) {
                                        double p = pro.getPrice() * pro.getQuantity();
                                        subtotal += p;
                                %>
                                <tr>
                                    <td class="align-middle"><img src="img/<%= pro.getImage()%>" alt="" style="width: 50px;"> <%= pro.getPname()%></td>
                                    <td class="align-middle"><%= pro.getPriceFormat()%></td>
                                    <td class="align-middle">
                                        <div class="input-group quantity mx-auto" style="width: 100px;">
                                            <div class="input-group-btn">
                                                <button type="button" class="btn btn-sm btn-primary btn-minus" >
                                                    <i class="fa fa-minus"></i>
                                                </button>
                                            </div>
                                            <input type="text" name="<%= pro.getPid()%>" class="form-control form-control-sm bg-secondary text-center" value="<%= pro.getQuantity()%>">
                                            <div class="input-group-btn">
                                                <button type="button" class="btn btn-sm btn-primary btn-plus">
                                                    <i class="fa fa-plus"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="align-middle">$<%= String.format("%.2f", p)%></td>
                                    <td class="align-middle"><button name="remove" value="<%= pro.getPid()%>" class="btn btn-sm btn-primary"><i class="fa fa-times"></i></button></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                        <div class="input-group-append">
                            <button class="btn btn-primary">Update Your Cart</button>
                        </div>
                    </form>
                </div>
                <div class="col-lg-4">
                    <%
                        String price = String.format("%.2f", subtotal);
                    %>
                    <div class="card border-secondary mb-5">
                        <div class="card-header bg-secondary border-0">
                            <h4 class="font-weight-semi-bold m-0">Cart Summary</h4>
                        </div>
                        <div class="card-body">
                            <div class="d-flex justify-content-between mb-3 pt-1">
                                <h6 class="font-weight-medium">Subtotal</h6>
                                <h6 class="font-weight-medium">$<%=price%></h6>
                            </div>
                            <div class="d-flex justify-content-between">
                                <h6 class="font-weight-medium">Shipping</h6>
                                <h6 class="font-weight-medium">$0.00</h6>
                            </div>
                        </div>
                        <div class="card-footer border-secondary bg-transparent">
                            <div class="d-flex justify-content-between mt-2">
                                <h5 class="font-weight-bold">Total</h5>
                                <h5 class="font-weight-bold">$<%=price%></h5>
                            </div>
                            <form action="ClientController">
                                <input type="hidden" name="go" value="checkout">
                                <button class="btn btn-block btn-primary my-3 py-3">Proceed To Checkout</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Cart End -->


        <!-- Footer Start -->
        <jsp:include page="footer.jsp"></jsp:include>
        <!-- Footer End -->


        <!-- Back to Top -->
        <a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>


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
