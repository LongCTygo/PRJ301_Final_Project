/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mvc;

import dao.DAOProduct;
import dao.DAOReview;
import display.ProductDisplay;
import display.ReviewDisplay;
import entity.Product;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ClientController", urlPatterns = {"/ClientController"})
public class ClientController extends HttpServlet {

    public static final String DEFAULT_GO = "listShop";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String go = request.getParameter("go");
            if (go == null) {
                go = DEFAULT_GO;
            }
            request.setAttribute("go", go);
            //List Shop
            if (go.equals("listShop")) {
                listShop(request, response);
            }
            if (go.equals("detail")) {
                detail(request, response);
            }
            if (go.equals("home")) {
                home(request, response);
            }
        }
    }

    void dispatch(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        //call jsp
        RequestDispatcher dispatch
                = request.getRequestDispatcher(url);
        dispatch.forward(request, response);
    }

    private void listShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOProduct daoPro = new DAOProduct();
        //Initial SQL
        String sql = "select * from Product join Category on Product.cateID = Category.cateID";
        //Search
        String query = request.getParameter("query");
        if (query == null) {
            query = "";
        }
        sql += String.format(" WHERE Product.pname like '%%%s%%'", query);
        request.setAttribute("query", query);
        //Category
        String cateid = request.getParameter("cateid");
        if (cateid != null) {
            try {
                int id = Integer.parseInt(cateid);
                sql += String.format(" AND Product.cateid = '%d'", id);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        //Sorting
        sql += DAOProduct.DEFAULT_ORDER_BY;
        //list
        Vector<ProductDisplay> productList = daoPro.getDisplay(sql);
        request.setAttribute("productList", productList);
        //Sort

        dispatch(request, response, "client/shop.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        if (pid != null) {
            DAOProduct daoPro = new DAOProduct();
            Vector<ProductDisplay> vector = daoPro.getDisplay(String.format("select * from Product as a join Category as b on a.cateID = b.cateID WHERE pid = '%s'", pid));
            if (!vector.isEmpty()) {
                //Product
                ProductDisplay pd = vector.get(0);
                request.setAttribute("product", pd);
                //Review
                DAOReview daoRev = new DAOReview();
                Vector<ReviewDisplay> revVec = daoRev.getDisplay("SELECT * from Review a, Customer b WHERE a.cid = b.cid AND a.pid = '" + pd.getPid() + "'");
                request.setAttribute("reviews", revVec);
                //Suggestions
                Vector<ProductDisplay> suggestions = daoPro.getDisplay("select * from Product as a join Category as b on a.cateID = b.cateID WHERE a.quantity > 0 AND a.pid != '" + pd.getPid() + "' ORDER By newID()");
                request.setAttribute("suggestions", suggestions);
                dispatch(request, response, "client/detail.jsp");

            }
        }
    }

    private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response, "client/index.jsp");
    }
}
