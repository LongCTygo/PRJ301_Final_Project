<%-- 
    Document   : formCategory
    Created on : Mar 1, 2023, 4:44:21 PM
    Author     : ADMIN
--%>

<%@page import="entity.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String action = (String) request.getAttribute("action");
    Category cus = (Category) request.getAttribute("data");
    if (action == null) {
        action = "add";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category</title>
        <link rel="stylesheet" href="css/style.css"/>
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="adminnavbar.jsp"></jsp:include>
            <div class="container mt-2">
                <h3 class="text-center text-capitalize"><%=action%> Category</h3>
            <form action="CategoryController" method="post">
                <input type="hidden" name="go" value="<%= action%>">
                <input type="hidden" name="cateid" value="<%= cus != null ? cus.getCateId() : "" %>">
                <div class="form-group row">
                    <label for="cname" class="col-4 col-form-label">Name</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-check"></i>
                                </div>
                            </div> 
                            <input value="<%= cus != null ? cus.getCateName() : ""%>" id="catename" name="catename" placeholder="Enter cname..." type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-4">Status</label> 
                    <div class="col-8">
                        <div class="custom-control custom-radio custom-control-inline">
                            <input name="status" id="status_0" type="radio" required="required" class="custom-control-input" value="1" <%= (cus != null && cus.getStatus() == 1) ? "checked" : ""%>> 
                            <label for="status_0" class="custom-control-label">Enabled</label>
                        </div>
                        <div class="custom-control custom-radio custom-control-inline">
                            <input name="status" id="status_1" type="radio" required="required" class="custom-control-input" value="0" <%= (cus != null && cus.getStatus() == 0) ? "checked" : ""%>> 
                            <label for="status_1" class="custom-control-label">Disabled</label>
                        </div>
                    </div>
                </div> 
                <div class="form-group row">
                    <div class="offset-4 col-8">
                        <button name="submit" type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </div>
            </form>
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
