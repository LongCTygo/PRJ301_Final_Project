<%-- 
    Document   : detail
    Created on : Feb 22, 2023, 5:43:32 PM
    Author     : ADMIN
--%>

<%@page import="entity.Review"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="display.ProductDisplay,display.ReviewDisplay,java.util.Vector,java.io.IOException,dao.DAOProduct"%>

<%
    ProductDisplay p = (ProductDisplay) request.getAttribute("product");
    Vector<ReviewDisplay> vector = (Vector<ReviewDisplay>) request.getAttribute("reviews");
    Vector<ProductDisplay> suggestions = (Vector<ProductDisplay>) request.getAttribute("suggestions");
    Review userReview = null;
    String cid = (String) session.getAttribute("cid");
    //Avg Score
    double avg = 0;
    for (ReviewDisplay rd : vector) {
        avg += rd.getScore();
        if (rd.getCid().equals(cid)) {
            userReview = rd;
        }
    }
    if (avg != 0) {
        avg /= vector.size();
    }
    int score;
    String review;
    if (userReview == null) {
        score = 5;
        review = "";
    } else {
        score = userReview.getScore();
        review = userReview.getReview();
    }
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>EShopper - <%=p.getPname()%></title>
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
            <jsp:param name="go" value='<%=(String) request.getAttribute("go")%>'></jsp:param>
        </jsp:include>
        <!-- Navbar End -->


        <!-- Page Header Start -->
        <div class="container-fluid bg-secondary mb-5">
            <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
                <h1 class="font-weight-semi-bold text-uppercase mb-3">Shop Detail</h1>
                <div class="d-inline-flex">
                    <p class="m-0"><a href="ClientController">Home</a></p>
                    <p class="m-0 px-2">-</p>
                    <p class="m-0">Shop Detail</p>
                </div>
            </div>
        </div>
        <!-- Page Header End -->


        <!-- Shop Detail Start -->
        <div class="container-fluid py-5">
            <div class="row px-xl-5">
                <div class="col-lg-5 pb-5">
                    <div id="product-carousel" class="carousel slide" data-ride="carousel">
                        <div class="carousel-inner border">
                            <div class="carousel-item active">
                                <img class="w-100 h-100" src="img/<%=p.getImage()%>" alt="Image">
                            </div>
                        </div>
                        <a class="carousel-control-prev" href="#product-carousel" data-slide="prev">
                            <i class="fa fa-2x fa-angle-left text-dark"></i>
                        </a>
                        <a class="carousel-control-next" href="#product-carousel" data-slide="next">
                            <i class="fa fa-2x fa-angle-right text-dark"></i>
                        </a>
                    </div>
                </div>

                <div class="col-lg-7 pb-5">
                    <h3 class="font-weight-semi-bold"><%=p.getPname()%></h3>
                    <div class="d-flex mb-3">
                        <div class="text-primary mr-2">
                            <%printStar(avg, out);%>
                        </div>
                        <small class="pt-1">(<%=vector.size()%> Review(s))</small>
                    </div>
                    <h3 class="font-weight-semi-bold mb-4"><%=p.getPriceFormat()%></h3>
                    <p class="mb-4"><%=p.getDescription()%></p>
                    <h6 class="font-weight-semi-bold mb-4">In stock: <%= p.getQuantity()%></h6>
                    <div class="d-flex align-items-center mb-4 pt-2">
                        <form action="ClientController">
                            <input type="hidden" name="go" value="addCart">
                            <input type="hidden" name="pid" value="<%= p.getPid()%>">
                            <div class="input-group quantity mr-3" style="width: 130px;">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-primary btn-minus" >
                                        <i class="fa fa-minus"></i>
                                    </button>
                                </div>
                                <input type="text" name="amount" class="form-control bg-secondary text-center" value=1 max=<%=p.getQuantity()%>>
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-primary btn-plus">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary px-3" <%= (p.getQuantity()==0 ? "disabled" : "") %>><i class="fa fa-shopping-cart mr-1"></i> Add To Cart</button>
                        </form>
                    </div>
                    <div class="d-flex pt-2">
                        <p class="text-dark font-weight-medium mb-0 mr-2">Share on:</p>
                        <div class="d-inline-flex">
                            <a class="text-dark px-2" href="">
                                <i class="fab fa-facebook-f"></i>
                            </a>
                            <a class="text-dark px-2" href="">
                                <i class="fab fa-twitter"></i>
                            </a>
                            <a class="text-dark px-2" href="">
                                <i class="fab fa-linkedin-in"></i>
                            </a>
                            <a class="text-dark px-2" href="">
                                <i class="fab fa-pinterest"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row px-xl-5">
                <div class="col">
                    <div class="nav nav-tabs justify-content-center border-secondary mb-4">
                        <a class="nav-item nav-link active" data-toggle="tab" href="#tab-pane-1">Description</a>
                        <a class="nav-item nav-link" data-toggle="tab" href="#tab-pane-2">Reviews (<%=vector.size()%>)</a>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="tab-pane-1">
                            <h4 class="mb-3">Product Description</h4>
                            <p>At our clothing store, we pride ourselves on providing high-quality, sustainably-sourced clothing for men and women. We believe that fashion can be both stylish and ethical, and we are committed to using materials that are both eco-friendly and socially responsible. </p>
                            <p>All of our fabrics are carefully selected to ensure that they meet our standards for sustainability and durability. We work with suppliers who use organic cotton, recycled materials, and other environmentally-friendly fibers. Additionally, we only partner with factories that provide safe and fair working conditions for their employees.</p>
                            <p>Our clothing collection includes a wide range of styles, from cozy sweaters to stylish dresses and versatile pants. Whether you're dressing up for a special occasion or just need comfortable clothes for everyday wear, we have something for everyone.</p>
                            <p>We believe that fashion should be accessible to everyone, which is why we offer our clothing at fair and transparent prices. When you shop with us, you can feel good knowing that you are supporting a sustainable, ethical business that is committed to making a positive impact in the world.</p>
                        </div>
                        <div class="tab-pane fade" id="tab-pane-2">
                            <div class="row">
                                <div class="col-md-6">
                                    <h4 class="mb-4"><%=vector.size()%> review(s) for "<%=p.getPname()%>"</h4>
                                    <% for (ReviewDisplay rd : vector) {%>
                                    <div class="media mb-4">
                                        <img src="img/user.jpg" alt="<%=rd.getCname()%>" class="img-fluid mr-3 mt-1" style="width: 45px;">
                                        <div class="media-body">
                                            <h6><%=rd.getCname()%><small> - <i><%=rd.getDate()%></i></small></h6>
                                            <div class="text-primary mb-2">
                                                <%printStar(rd.getScore(), out);%>
                                            </div>
                                            <p><%=rd.getReview()%></p>
                                        </div>
                                    </div>
                                    <%}%>
                                </div>
                                <div class="col-md-6">
                                    <h4 class="mb-4">Leave a review</h4>
                                    <small>Your email address will not be published. Required fields are marked *</small>
                                    <% if (userReview != null){ %>
                                    <div class="alert alert-success"" role="alert">
                                        You have already reviewed this product. You can update the review.
                                    </div>
                                    <%}%>
                                    <form action="ClientController" method="post" id="reviewForm">
                                        <% if (userReview!=null){ %>
                                        <div class="form-group mb-0">
                                            <input name="remove" type="submit" value="Delete This Review" class="btn btn-primary px-3">
                                        </div>
                                        <%}%>
                                        <div class="d-flex my-3">
                                            <p class="mb-0 mr-2">Your Rating * :</p>
                                            <div class="text-primary">
                                                <input value="<%= score %>" name="score" type="range" class="form-range" min="0" max="10" id="rating" oninput="this.nextElementSibling.value = this.value">
                                                <output><%= score %></output>
                                                <i class="far fa-star"></i>
                                            </div>
                                        </div> 
                                        <input type="hidden" name="go" value="review">
                                        <input type="hidden" name="pid" value="<%= p.getPid()%>">
                                        <div class="form-group">
                                            <label for="message">Your Review *</label>
                                            <textarea  name="review" id="reviewForm" cols="30" rows="5" class="form-control" required><%= review %></textarea>
                                        </div>
                                        <div class="form-group mb-0">
                                            <input type="submit" value="Leave Your Review" class="btn btn-primary px-3">
                                        </div>
                                        
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Shop Detail End -->


        <!-- Products Start -->
        <div class="container-fluid py-5">
            <div class="text-center mb-4">
                <h2 class="section-title px-5"><span class="px-2">You May Also Like</span></h2>
            </div>
            <div class="row px-xl-5">
                <div class="col">
                    <div class="owl-carousel related-carousel">
                        <% for (ProductDisplay sug : suggestions) {%>
                        <div class="card product-item border-0">
                            <div class="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                                <img class="img-fluid w-100" src="img/<%=sug.getImage()%>" alt="">
                            </div>
                            <div class="card-body border-left border-right text-center p-0 pt-4 pb-3">
                                <h6 class="text-truncate mb-3"><%=sug.getPname()%></h6>
                                <h6 style="color:red" class="text-truncate mb-3">
                                    <%if (sug.getQuantity() == 0) {%>Sold Out<%} else {%>Stock: <%=sug.getQuantity()%><%}%>
                                </h6>
                                <div class="d-flex justify-content-center">
                                    <h6><%=sug.getPriceFormat()%></h6>
                                    <h6 class="text-muted ml-2"><del>$<%=String.format("%02.2f", sug.getPrice() * 2)%></del></h6>
                                </div>
                            </div>
                            <div class="card-footer d-flex justify-content-between bg-light border">
                                <a href="ClientController?go=detail&pid=<%=sug.getPid()%>" class="btn btn-sm text-dark p-0"><i class="fas fa-eye text-primary mr-1"></i>View Detail</a>
                                <a href="ClientController?go=addCart&pid=<%=sug.getPid()%>" class="btn btn-sm text-dark p-0 <%if (sug.getQuantity() == 0) {%>disabled<%}%>"><i class="fas fa-shopping-cart text-primary mr-1"></i>Add To Cart</a>
                            </div>
                        </div>
                        <%}%>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Products End -->


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

<%!
    private void printStar(double score, JspWriter out) throws IOException {
        int star = (int) Math.round(score);
        if (star < 0 || star > 10) {
            throw new IllegalStateException("Stars must be between 0 and 10, but got " + star);
        }
        int i = 0;
        for (; i < (int) (star / 2); i++) {
            out.print("<i class=\"fas fa-star\"></i>");
        }
        if (star % 2 == 1) {
            out.print("<i class=\"fas fa-star-half-alt\"></i>");
            ++i;
        }
        for (; i < 5; i++) {
            out.print("<i class=\"far fa-star\"></i>");
        }
    }
%>