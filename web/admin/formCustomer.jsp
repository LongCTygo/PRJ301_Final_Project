<%-- 
    Document   : formCustomer
    Created on : Mar 1, 2023, 4:44:21 PM
    Author     : ADMIN
--%>

<%@page import="entity.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String action = (String) request.getAttribute("action");
    Customer cus = (Customer) request.getAttribute("data");
    if (action == null) {
        action = "add";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer</title>
        <link rel="stylesheet" href="css/style.css"/>
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="adminnavbar.jsp"></jsp:include>
        <div class="container mt-2">
            <h3 class="text-center text-capitalize"><%=action%> Customer</h3>
            <form>
                <div class="form-group row">
                    <label class="col-4 col-form-label" for="cid">Customer ID</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-user-circle"></i>
                                </div>
                            </div> 
                            <input id="cid" name="cid" placeholder="Enter cid..." type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="cname" class="col-4 col-form-label">Name</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-check"></i>
                                </div>
                            </div> 
                            <input value="<%= cus != null ? "readonly" : "" %>" id="cname" name="cname" placeholder="Enter cname..." type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="username" class="col-4 col-form-label">Username</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-user"></i>
                                </div>
                            </div> 
                            <input id="username" name="username" placeholder="Enter username..." type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="password" class="col-4 col-form-label">Password</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-lock"></i>
                                </div>
                            </div> 
                            <input id="password" name="password" placeholder="Enter password..." type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="address" class="col-4 col-form-label">Address</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-home"></i>
                                </div>
                            </div> 
                            <input id="address" name="address" placeholder="Enter address..." type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="phone" class="col-4 col-form-label">Phone</label> 
                    <div class="col-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-phone"></i>
                                </div>
                            </div> 
                            <input id="phone" name="phone" placeholder="Enter phone..." type="text" class="form-control" required="required">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-4">Radio Buttons</label> 
                    <div class="col-8">
                        <div class="custom-control custom-radio custom-control-inline">
                            <input name="status" id="status_0" type="radio" required="required" class="custom-control-input" value="1"> 
                            <label for="status_0" class="custom-control-label">Enabled</label>
                        </div>
                        <div class="custom-control custom-radio custom-control-inline">
                            <input name="status" id="status_1" type="radio" required="required" class="custom-control-input" value="0"> 
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
    </body>
</html>
