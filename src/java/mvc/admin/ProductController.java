/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mvc.admin;

import dao.DAOProduct;
import display.ProductDisplay;
import entity.Product;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
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
import utils.SQLErrorCodeUtil;
import utils.ServletUtil;
import static utils.ServletUtil.dispatch;
import utils.SessionUtil;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    public static final String DEFAULT_GO = "view";

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (!SessionUtil.isSessionAdmin(session)) {
            ServletUtil.addErrorMessage(request, "Please login first");
            dispatch(request, response, "admin/login.jsp");
            return;
        }
        //Access is permitted
        String go = request.getParameter("go");
        if (go == null) {
            go = DEFAULT_GO;
        }
        try {
            if (go.equals("view")) {
                view(request, response, true);
            } else if (go.equals("add")) {
                add(request, response);
            } else if (go.equals("update")) {
                update(request, response);
            } else if (go.equals("delete")) {
                delete(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void view(HttpServletRequest request, HttpServletResponse response, boolean isDirect) throws SQLException, ServletException, IOException {
        DAOProduct dao = new DAOProduct();
        String sql = "select * from Product as a join Category as b on a.cateID = b.cateID "
                + "WHERE a.pname like ? and a.status != ? ORDER BY pid ASC";
        PreparedStatement prep = dao.getPrep(sql);
        //name
        String query = request.getParameter("query");
        if (query == null) {
            query = "";
        }
        prep.setString(1, "%" + query + "%");
        request.setAttribute("query", query);
        //Status
        String status = isDirect ? request.getParameter("status") : "-1";
        int s;
        try {
            s = Integer.parseInt(status);
        } catch (NumberFormatException ex) {
            s = -1;
        }
        prep.setInt(2, s);
        request.setAttribute("query_status", s);
        Vector<ProductDisplay> all = dao.getDisplay(prep);
        request.setAttribute("list", all);
        dispatch(request, response, "admin/viewProduct.jsp");

    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        String submit = request.getParameter("submit");
        if (submit == null) {
            request.setAttribute("action", "add");
            dispatch(request, response, "admin/formProduct.jsp");
        } else {
            try {
                String pid = request.getParameter("pid");
                String pname = request.getParameter("pname");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                double price = Double.parseDouble(request.getParameter("price"));
                String image = request.getParameter("image");
                String description = request.getParameter("description");
                int status = Integer.parseInt(request.getParameter("status"));
                int cateid = Integer.parseInt(request.getParameter("cateid"));
                Product pro = new Product(pid, pname, quantity, price, image, description, status, cateid);

                DAOProduct dao = new DAOProduct();
                int n = dao.add(pro);
                if (n == 1) {
                    ServletUtil.addSuccessMessage(request, "Successfully added product " + pro.getPid() + ".");
                } else if (n == SQLErrorCodeUtil.UNIQUE_KEY_VIOLATION) {
                    ServletUtil.addErrorMessage(request, "Failed to add product " + pro.getPid() + " since a product with such ID already exist.");
                } else {
                    ServletUtil.addErrorMessage(request, "Failed to add product " + pro.getPid() + ". Error = " + n + ".");
                }
            } catch (NumberFormatException ex) {
                ServletUtil.addErrorMessage(request, "Number Format was wrong. Message: " + ex.getMessage());
            }
            view(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            DAOProduct dao = new DAOProduct();
            int n = dao.remove(id);
            if (n == 1) {
                ServletUtil.addSuccessMessage(request, "Successfully remove Product with ID = " + id + ".");
            } else {
                ServletUtil.addErrorMessage(request, "Failed to delete, likely due to exisiting relationship.");
            }
        }
        view(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        DAOProduct dao = new DAOProduct();
        String submit = request.getParameter("submit");
        if (submit == null) {
            String id = request.getParameter("id");
            Product cus = dao.get(id);
            request.setAttribute("data", cus);
            request.setAttribute("action", "update");
            dispatch(request, response, "admin/formProduct.jsp");
        } else {
            String pid = request.getParameter("pid");
            String pname = request.getParameter("pname");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("price"));
            String image = request.getParameter("image");
            String description = request.getParameter("description");
            int status = Integer.parseInt(request.getParameter("status"));
            int cateid = Integer.parseInt(request.getParameter("cateid"));
            Product pro = new Product(pid, pname, quantity, price, image, description, status, cateid);
            int n = dao.update(pro);
            if (n == 1) {
                ServletUtil.addSuccessMessage(request, "Successfully updated product " + pid + ".");
            } else {
                ServletUtil.addErrorMessage(request, "Failed to update product " + pid + ". Error code: " + n + ".");
            }
            view(request, response);
        }
    }
    
    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        view(request, response, false);
    }

}
