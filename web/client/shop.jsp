<%-- 
    Document   : shop
    Created on : Feb 22, 2023, 4:28:41 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="display.ProductDisplay,java.util.Vector,dao.DAOCategory,entity.Category"%>
<%
    Vector<ProductDisplay> vector = (Vector<ProductDisplay>) request.getAttribute("productList");
    DAOCategory daoCat = new DAOCategory();
    Vector<Category> catList = daoCat.getAll();
    String query = (String) request.getAttribute("query");
    String cateid = request.getParameter("cateid");
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>EShopper</title>
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
            <jsp:param name="go" value='<%=(String)( request.getAttribute("go") )%>'></jsp:param>
        </jsp:include>
        <!-- Navbar End -->


        <!-- Page Header Start -->
        <div class="container-fluid bg-secondary mb-5">
            <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
                <h1 class="font-weight-semi-bold text-uppercase mb-3">Our Shop</h1>
                <div class="d-inline-flex">
                    <p class="m-0"><a href="ClientController">Home</a></p>
                    <p class="m-0 px-2">-</p>
                    <p class="m-0">Shop</p>
                </div>
            </div>
        </div>
        <!-- Page Header End -->


        <!-- Shop Start -->
        <div class="container-fluid pt-5">
            <div class="row px-xl-5">
                <!-- Shop Sidebar Start -->
                <div class="col-lg-3 col-md-12">
                    <!-- Price Start -->
                    <div class="border-bottom mb-4 pb-4">
                        <h5 class="font-weight-semi-bold mb-4">Filter by price</h5>
                        <form>
                            <div class="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                <input type="checkbox" class="custom-control-input" checked id="price-all">
                                <label class="custom-control-label" for="price-all">All Price</label>
                                <span class="badge border font-weight-normal">1000</span>
                            </div>
                            <div class="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                <input type="checkbox" class="custom-control-input" id="price-1">
                                <label class="custom-control-label" for="price-1">$0 - $100</label>
                                <span class="badge border font-weight-normal">150</span>
                            </div>
                            <div class="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                <input type="checkbox" class="custom-control-input" id="price-2">
                                <label class="custom-control-label" for="price-2">$100 - $200</label>
                                <span class="badge border font-weight-normal">295</span>
                            </div>
                            <div class="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                <input type="checkbox" class="custom-control-input" id="price-3">
                                <label class="custom-control-label" for="price-3">$200 - $300</label>
                                <span class="badge border font-weight-normal">246</span>
                            </div>
                            <div class="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                <input type="checkbox" class="custom-control-input" id="price-4">
                                <label class="custom-control-label" for="price-4">$300 - $400</label>
                                <span class="badge border font-weight-normal">145</span>
                            </div>
                            <div class="custom-control custom-checkbox d-flex align-items-center justify-content-between">
                                <input type="checkbox" class="custom-control-input" id="price-5">
                                <label class="custom-control-label" for="price-5">$400 - $500</label>
                                <span class="badge border font-weight-normal">168</span>
                            </div>
                        </form>
                    </div>
                    <!-- Price End -->

                    <!-- Category Start -->
                    <div class="border-bottom mb-4 pb-4">
                        <h5 class="font-weight-semi-bold mb-4">Filter by Category</h5>
                        <form>
                            <div class="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                <input type="checkbox" class="custom-control-input" checked id="category-all">
                                <label class="custom-control-label" for="category-all">All Category</label>
                                <span class="badge border font-weight-normal">1000</span>
                            </div>
                            <% for (Category cat: catList){ %>
                            <div class="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                <input type="checkbox" class="custom-control-input" id="category-<%=cat.getCateId()%>">
                                <label class="custom-control-label" for="category-<%=cat.getCateId()%>"><%=cat.getCateName()%></label>
                                <span class="badge border font-weight-normal">1000</span>
                            </div>
                            <%}%>
                        </form>
                    </div>
                    <!-- Category End -->

                </div>
                <!-- Shop Sidebar End -->


                <!-- Shop Product Start -->
                <div class="col-lg-9 col-md-12">
                    <div class="row pb-3">
                        <!-- Search Bar -->
                        <div class="col-12 pb-1">
                            <div class="d-flex align-items-center justify-content-between mb-4">
                                <form name="searchbar" action="ClientController">
                                    <input  type="hidden" name="go" value="listShop">
                                    <div class="input-group">
                                        <% if (cateid!=null){
                                            out.print("<input type=\"hidden\" name=\"cateid\" value=\""+cateid+"\">");
                                            }%>
                                        <input type="text" class="form-control" name="query" placeholder="Search by name" value="<%=query%>">
                                        <div class="input-group-append">
                                            <span onclick="searchbar.submit()" class="input-group-text bg-transparent text-primary">
                                                <i class="fa fa-search"></i>
                                            </span>
                                        </div>
                                    </div>
                                </form>
                                <div>
                                    Total: <%=vector.size()%>
                                </div>
                                <div class="dropdown ml-4">
                                    <button class="btn border dropdown-toggle" type="button" id="triggerId" data-toggle="dropdown" aria-haspopup="true"
                                            aria-expanded="false">
                                        Sort by
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="triggerId">
                                        <a class="dropdown-item" href="#">Latest</a>
                                        <a class="dropdown-item" href="#">Popularity</a>
                                        <a class="dropdown-item" href="#">Best Rating</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Cards -->

                        <% for (ProductDisplay p : vector) { %>
                        <div class="card product-item border-0 mb-4">
                            <div class="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                                <img class="img-fluid w-100" src="img/<%=p.getImage()%>" alt="">
                            </div>
                            <div class="card-body border-left border-right text-center p-0 pt-4 pb-3">
                                <h6 class="text-truncate mb-3"><%=p.getPname()%></h6>
                                <h6 style="color:red" class="text-truncate mb-3">
                                    <%if (p.getQuantity() == 0){%>Sold Out<%}
                                    else{%>Stock: <%=p.getQuantity()%><%}%>
                                </h6>
                                <div class="d-flex justify-content-center">
                                    <h6><%=p.getPriceFormat()%></h6>
                                    <h6 class="text-muted ml-2"><del>$<%=String.format("%02.2f",p.getPrice()*2)%></del></h6>
                                </div>
                            </div>
                            <div class="card-footer d-flex justify-content-between bg-light border">
                                <a href="ClientController?go=detail&pid=<%=p.getPid()%>" class="btn btn-sm text-dark p-0"><i class="fas fa-eye text-primary mr-1"></i>View Detail</a>
                                <a href="ClientController?go=addCart&pid=<%=p.getPid()%>" class="btn btn-sm text-dark p-0 <%if (p.getQuantity()==0){%>disabled<%}%>"><i class="fas fa-shopping-cart text-primary mr-1"></i>Add To Cart</a>
                            </div>
                        </div>
                        <%}%>
                    </div>
                </div>
                <!-- Shop Product End -->
            </div>
        </div>
        <!-- Shop End -->


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
