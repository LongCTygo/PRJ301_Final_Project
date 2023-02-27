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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ClientController", urlPatterns = {"/ClientController"})
public class ClientController extends HttpServlet {

    public static final String DEFAULT_GO = "home";

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
            try {
                if (go.equals("listShop")) {
                    listShop(request, response);
                }
                if (go.equals("detail")) {
                    detail(request, response);
                }
                if (go.equals("home")) {
                    home(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
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

    private void listShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        DAOProduct daoPro = new DAOProduct();
        int params = 1;
        //Initial SQL
        String sql = "select * from Product join Category on Product.cateID = Category.cateID\n"
                + "WHERE Product.pname LIKE ? ";
        //Search
        String query = request.getParameter("query");
        if (query == null) {
            query = "";
        }
        request.setAttribute("query", query);
        //Category
        String cateid = request.getParameter("cateid");
        Integer id = null;
        if (cateid != null) {
            id = Integer.parseInt(cateid);
            sql += " AND Product.cateid = ?";
            params++;
        }
        //Sorting
        sql += DAOProduct.DEFAULT_ORDER_BY;
        //Construct statement
        PreparedStatement statement = daoPro.conn.prepareStatement(sql);
        statement.setString(1, "%" + query + "%");
        if (params >= 2) {
            statement.setInt(2, id);
        }
        Vector<ProductDisplay> productList = daoPro.getDisplay(statement);
        request.setAttribute("productList", productList);
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

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String pid = request.getParameter("pid");
        if (pid != null) {
            DAOProduct daoPro = new DAOProduct();
            //Create statement
            PreparedStatement statement = daoPro.conn.prepareStatement("select * from Product as a join Category as b on a.cateID = b.cateID WHERE pid = ?");
            statement.setString(1, pid);
            Vector<ProductDisplay> vector = daoPro.getDisplay(statement);
            if (!vector.isEmpty()) {
                //Product 
                ProductDisplay pd = vector.get(0);
                request.setAttribute("product", pd);
                //Review
                DAOReview daoRev = new DAOReview();
                //review statement
                PreparedStatement statementRev = daoRev.conn.prepareStatement("SELECT * from Review a, Customer b WHERE a.cid = b.cid AND a.pid = ?");
                statementRev.setString(1, pid);
                Vector<ReviewDisplay> revVec = daoRev.getDisplay(statementRev);
                request.setAttribute("reviews", revVec);
                //Suggestions
                PreparedStatement statementSug = daoPro.conn.prepareStatement("select * from Product as a join Category as b on a.cateID = b.cateID WHERE a.quantity > 0 AND a.pid != ? ORDER By newID()");
                statementSug.setString(1, pid);
                Vector<ProductDisplay> suggestions = daoPro.getDisplay(statementSug);
                request.setAttribute("suggestions", suggestions);
                dispatch(request, response, "client/detail.jsp");

            }
        }
    }

    private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response, "client/index.jsp");
    }
}
