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
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SessionUtil;

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
                listShop(request, response);
            } else if (go.equals("detail")) {
                detail(request, response, session);
            } else if (go.equals("home")) {
                home(request, response);
            } else if (go.equals("cart")) {
                cart(request, response, session);
            } else if (go.equals("addCart")) {
                addCart(request, response, session);
            } else if (go.equals("updateCart")) {
                updateCart(request, response, session);
            } else if (go.equals("checkout")) {
                checkout(request, response, session);
            } else {
                request.setAttribute("context", "404");
                dispatch(request, response, "ErrorPage");
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("context", "exception");
            request.setAttribute("exception", ex);
            dispatch(request, response, "ErrorPage");
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
            listShop(request, response);
        }
    }

    private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        Hashtable<String, Integer> cart = SessionUtil.getCart(session);
        Vector<Product> vector = new Vector<>();
        DAOProduct dao = new DAOProduct();
        Enumeration<String> ems = cart.keys();
        while (ems.hasMoreElements()) {
            String pid = ems.nextElement();
            Product pro = dao.get(pid);
            if (pro == null) {
                continue;
            }
            //set quantity
            pro.setQuantity((int) cart.get(pid));
            vector.add(pro);
        }
        request.setAttribute("cartList", vector);
        dispatch(request, response, "client/cart.jsp");
    }

    private void addCart(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        Hashtable<String, Integer> cart = SessionUtil.getCart(session);
        String pid = request.getParameter("pid");
        String a = request.getParameter("amount");
        int amount = a == null ? 1 : Integer.parseInt(a);
        if (amount <= 0) {
            amount = 1;
        }
        Object value = cart.get(pid);
        if (value == null) {
            cart.put(pid, amount);
        } else {
            cart.put(pid, (Integer) (value) + amount);
        }
        SessionUtil.setCart(session, cart);
        addSuccessMessage(request, "Added to Cart!");
        dispatch(request, response, "ClientController?go=cart");
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        Hashtable<String, Integer> cart = SessionUtil.getCart(session);
        //Check if the action is to remove an item
        String remove = request.getParameter("remove");
        //if remove
        if (remove != null) {
            cart.remove(remove);
            addSuccessMessage(request, "Removed from Cart!");
        } else {
            //Normal Update
            Enumeration<String> ems = request.getParameterNames();
            while (ems.hasMoreElements()) {
                try {
                    String key = ems.nextElement();
                    int newValue = Integer.parseInt(request.getParameter(key));
                    if (newValue <= 0) {
                        cart.remove(key);
                    } else {
                        cart.put(key, newValue);
                    }
                } catch (NumberFormatException ex) {
                }
            }
            addSuccessMessage(request, "Cart Updated!");
        }
        SessionUtil.setCart(session, cart);
        dispatch(request, response, "ClientController?go=cart");
    }

    private void addSuccessMessage(HttpServletRequest request, String msg) {
        Vector<String> success = (Vector<String>) request.getAttribute("success");
        if (success == null) {
            success = new Vector<>();
        }
        success.add(msg);
        request.setAttribute("success", success);
    }

    private void addErrorMessage(HttpServletRequest request, String msg) {
        Vector<String> success = (Vector<String>) request.getAttribute("error");
        if (success == null) {
            success = new Vector<>();
        }
        success.add(msg);
        request.setAttribute("error", success);
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        Hashtable<String, Integer> cart = SessionUtil.getCart(session);
        boolean canProceedtoCheckout = true;
        Enumeration<String> ems = cart.keys();
        if (cart.isEmpty()) {
            //Cart is empty
            addErrorMessage(request, "Cart is Empty!");
            dispatch(request, response, "ClientController?go=cart");
            return;
        }
        DAOProduct dao = new DAOProduct();
        while (ems.hasMoreElements()) {
            String pid = ems.nextElement();
            int amount = cart.get(pid);
            if (amount <= 0) {
                addErrorMessage(request, String.format("Product %s amount was %d?", pid, amount));
                canProceedtoCheckout = false;
                continue;
            }
            //Get Product from Database
            Product pro = dao.get(pid);
            if (pro == null) {
                addErrorMessage(request, String.format("Product %s was in cart, but was not in database somehow?", pid));
                canProceedtoCheckout = false;
                continue;
            }
            int stock = pro.getQuantity();
            if (stock < amount) {
                addErrorMessage(request, String.format("Cannot buy %dx %s. %d are available.", amount, pro.getPname(), stock));
                canProceedtoCheckout = false;
            }
        }
        if (canProceedtoCheckout) {
            addSuccessMessage(request, "TODO: logic for checkout here. Add bills and stuffs idk");
        }
        dispatch(request, response, "ClientController?go=cart");
    }

}
