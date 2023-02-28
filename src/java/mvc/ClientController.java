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
import jakarta.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
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
        String go = request.getParameter("go");
        
        //Get cart
        HttpSession session = request.getSession();
        
        if (go == null) {
            go = DEFAULT_GO;
        }
        request.setAttribute("go", go);
        //List Shop
        try {
            if (go.equals("listShop")) {
                listShop(request, response, session);
            } else if (go.equals("detail")) {
                detail(request, response, session);
            } else if (go.equals("home")) {
                home(request, response, session);
            } else if (go.equals("cart")) {
                cart(request, response, session);
            } else if (go.equals("addCart")) {
                addCart(request, response, session);
            } else if (go.equals("updateCart")) {
                updateCart(request, response, session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void dispatch(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        //call jsp
        RequestDispatcher dispatch
                = request.getRequestDispatcher(url);
        dispatch.forward(request, response);
    }

    private void listShop(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
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
        PreparedStatement statement = daoPro.getPrep(sql);
        statement.setString(1, "%" + query + "%");
        if (params >= 2) {
            statement.setInt(2, id);
        }
        Vector<ProductDisplay> productList = daoPro.getDisplay(statement);
        request.setAttribute("productList", productList);
        dispatch(request, response, "client/shop.jsp");
    }

    private void detail(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
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
        } else {
            listShop(request, response, session);
        }
    }

    private void home(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        dispatch(request, response, "client/index.jsp");

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

    private void cart(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        //get cart data
        Vector<Product> vector = new Vector<>();
        DAOProduct dao = new DAOProduct();
        Enumeration<String> ems = session.getAttributeNames();
        while (ems.hasMoreElements()) {
            String pid = ems.nextElement();
            String sql = "SELECT * from Product WHERE pid = ?";
            //Statement
            PreparedStatement statement = dao.getPrep(sql);
            statement.setString(1, pid);
            Vector<Product> all = dao.getAll(statement);
            if (all.isEmpty()) {
                continue;
            }
            Product pro = all.get(0);
            //set quantity
            pro.setQuantity((int) session.getAttribute(pid));
            vector.add(pro);
        }
        request.setAttribute("cartList", vector);
        dispatch(request, response, "client/cart.jsp");
    }

    private void addCart(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        String pid = request.getParameter("pid");
        String a = request.getParameter("amount");
        int amount = a == null ? 1 : Integer.parseInt(a);
        Object value = session.getAttribute(pid);
        if (value == null) {
            session.setAttribute(pid, amount);
        } else {
            session.setAttribute(pid, (Integer) (value) + amount);
        }
        System.out.printf("Added %d items of ID '%s' to cart! \n", amount, pid);
        response.sendRedirect("ClientController?go=cart");
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        //Check if the action is to remove an item
        String remove = request.getParameter("remove");
        //if remove
        if (remove != null) {
            session.removeAttribute(remove);
        } else {
            //Normal Update
            Enumeration<String> ems = request.getParameterNames();
            while (ems.hasMoreElements()) {
                try {
                    String key = ems.nextElement();
                    int newValue = Integer.parseInt(request.getParameter(key));
                    if (newValue == 0) {
                        session.removeAttribute(key);
                    } else {
                        session.setAttribute(key, newValue);
                    }
                } catch (NumberFormatException ex) {
                }
            }
        }
        response.sendRedirect("ClientController?go=cart");
    }

}
