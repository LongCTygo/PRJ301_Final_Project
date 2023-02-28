<%-- 
    Document   : error
    Created on : Feb 28, 2023, 3:11:13 PM
    Author     : ADMIN
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vector<String> errors = (Vector<String>) request.getAttribute("error");
    Vector<String> successes = (Vector<String>) request.getAttribute("success");
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
        <jsp:include page="../client/topbar.jsp"></jsp:include>
            <!-- Topbar End -->


            <!-- Navbar Start -->
        <jsp:include page="../client/navbar.jsp">
            <jsp:param name="go" value='<%=(String) (request.getAttribute("go"))%>'></jsp:param>
        </jsp:include>
        <!-- Navbar End -->


        <!-- Login Start -->
        <section class="vh-150">
            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                        <div class="card shadow-2-strong" style="border-radius: 1rem;" >
                            <form action="ClientController" method="post">
                                <input type="hidden" name="go" value="register">
                                <div class="card-body p-5 text-center" style="background-color: #ffdaee;">
                                    <% if (errors != null) {
                                            for (String msg : errors) {
                                    %>
                                    <div class="alert alert-danger"" role="alert">
                                        <strong><%=msg%></strong>
                                    </div>
                                    <%}
                                        }%>
                                    <% if (successes != null) {
                                            for (String msg : successes) {
                                    %>
                                    <div class="alert alert-success"" role="alert">
                                        <strong><%=msg%></strong>
                                    </div>
                                    <%}
                                        }%>
                                    <h3 class="mb-5">Register</h3>
                                    <div class="form-outline mb-4">
                                        <input type="text" name="username" id="typeEmailX-2" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="typeEmailX-2">Username</label>
                                    </div>
                                    <div class="form-outline mb-4">
                                        <input type="password" name="password" id="typePasswordX-2" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="typePasswordX-2">Password</label>
                                    </div>
                                    <div class="form-outline mb-4">
                                        <input type="text" name="name" id="typeNameX-2" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="typeNameX-2">Your Name</label>
                                    </div>
                                    <div class="form-outline mb-4">
                                        <input type="text" name="address" id="typeAddressX-2" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="typeAddressX-2">Address</label>
                                    </div>
                                    <div class="form-outline mb-4">
                                        <input type="text" name="phone" id="typePhoneX-2" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="typePhoneX-2">Phone</label>
                                    </div>
                                    <h6 class="mb-5"><a href="ClientController?go=login">Login</a></h6>
                                    <button name="submit" class="btn btn-primary btn-lg btn-block" type="submit">Register</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Login End -->


        <!-- Footer Start -->
        <jsp:include page="../client/footer.jsp"></jsp:include>
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