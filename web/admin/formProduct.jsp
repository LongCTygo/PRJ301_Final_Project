<%-- 
    Document   : formProduct
    Created on : Mar 1, 2023, 4:44:21 PM
    Author     : ADMIN
--%>

<%@page import="entity.Category"%>
<%@page import="java.util.Vector"%>
<%@page import="dao.DAOCategory"%>
<%@page import="entity.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String action = (String) request.getAttribute("action");
    Product pro = (Product) request.getAttribute("data");
    if (action == null) {
        action = "add";
    }
    //Categories
    DAOCategory dao = new DAOCategory();
    Vector<Category> vector = dao.getAll();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product</title>
        <link rel="stylesheet" href="css/style.css"/>
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="adminnavbar.jsp"></jsp:include>
            <div class="container mt-2">
                <h3 class="text-center text-capitalize"><%=action%> Product</h3>
            <form action="ProductController" method="post">
                <input type="hidden" name="go" value="<%= action%>">
                <div class="form-group row">
                    <label for="pid" class="col-4 col-form-label">Product ID</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-cube"></i>
                                </div>
                            </div> 
                            <input value="<%= pro != null ? pro.getPid() : "" %>" id="pid" name="pid" type="text" class="form-control" required="required" <%= pro != null ? "readonly" : ""%>>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="pname" class="col-4 col-form-label">Product Name</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-cube"></i>
                                </div>
                            </div> 
                            <input value="<%= pro != null ? pro.getPname() : "" %>" id="pname" name="pname" type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="quantity" class="col-4 col-form-label">Quantity</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-cube"></i>
                                </div>
                            </div> 
                            <input value="<%= pro != null ? pro.getQuantity(): "" %>" id="quantity" name="quantity" type="text" required="required" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="price" class="col-4 col-form-label">Price</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-cube"></i>
                                </div>
                            </div> 
                            <input value="<%= pro != null ? pro.getPrice(): "" %>" id="price" name="price" type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="image" class="col-4 col-form-label">Image</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-cube"></i>
                                </div>
                            </div> 
                            <input value="<%= pro != null ? pro.getImage() : "" %>" id="image" name="image" type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div> 
                <div class="form-group row">
                    <label for="description" class="col-4 col-form-label">Description</label> 
                    <div class="col-8">
                        <textarea id="description" name="description" cols="40" rows="3" class="form-control"><%= pro != null ? pro.getDescription(): "" %></textarea>
                    </div>
                </div> 
                <div class="form-group row">
                    <label class="col-4">Status</label> 
                    <div class="col-8">
                        <div class="custom-control custom-radio custom-control-inline">
                            <input name="status" id="status_0" type="radio" required="required" class="custom-control-input" value="1" <%= (pro != null && pro.getStatus() == 1) ? "checked" : ""%>> 
                            <label for="status_0" class="custom-control-label">Enabled</label>
                        </div>
                        <div class="custom-control custom-radio custom-control-inline">
                            <input name="status" id="status_1" type="radio" required="required" class="custom-control-input" value="0" <%= (pro != null && pro.getStatus() == 0) ? "checked" : ""%>> 
                            <label for="status_1" class="custom-control-label">Disabled</label>
                        </div>
                    </div>
                </div> 
                <div class="form-group row">
                    <label for="cateid" class="col-4 col-form-label">Categogry</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-cube"></i>
                                </div>
                            </div> 
                            <select class="custom-select" id="cateid" name="cateid" required>
                                <option <%= pro == null ? "selected" : "" %>>Choose...</option>
                                <% for (Category cat : vector) {
                                    int cateId = cat.getCateId();%>
                                <option value="<%=cateId%>" <%= pro != null && pro.getCateID() == cateId ? "selected" : "" %>><%= cat.getCateName()%></option>
                                <%}%>
                            </select>
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
