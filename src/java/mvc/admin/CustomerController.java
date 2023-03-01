/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mvc.admin;

import dao.DAOCustomer;
import entity.Customer;
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
import utils.ServletUtil;
import static utils.ServletUtil.dispatch;
import utils.SessionUtil;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CustomerController", urlPatterns = {"/CustomerController"})
public class CustomerController extends HttpServlet {

    public static String DEFAULT_GO = "view";

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
        HttpSession session = request.getSession();
        if (!SessionUtil.isSessionAdmin(session)) {
            response.sendError(403); //Forbidden
            return;
        }
        //Access is permitted
        String go = request.getParameter("go");
        if (go == null) {
            go = DEFAULT_GO;
        }
        try {
            if (go.equals("view")) {
                view(request, response);
            } else if (go.equals("add")) {
                add(request, response);
            } else if (go.equals("update")) {
                update(request, response);
            } else if (go.equals("delete")){
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

    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        DAOCustomer dao = new DAOCustomer();
        String sql = "SELECT * from Customer "
                + "WHERE (cname like ? or username like ?) "
                + "AND status != ? "
                + "ORDER BY cid";
        PreparedStatement prep = dao.getPrep(sql);
        //Query
        String query = request.getParameter("query");
        if (query == null) {
            query = "";
        }
        prep.setString(1, "%" + query + "%");
        prep.setString(2, "%" + query + "%");
        request.setAttribute("query", query);
        //Status
        String status = request.getParameter("status");
        int s;
        try {
            s = Integer.parseInt(status);
        } catch (NumberFormatException ex) {
            s = -1;
        }
        prep.setInt(3, s);
        request.setAttribute("status", s);
        Vector<Customer> all = dao.getAll(prep);
        request.setAttribute("list", all);
        dispatch(request, response, "admin/viewCustomer.jsp");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
        if (submit == null) {
            request.setAttribute("action", "add");
            dispatch(request, response, "admin/formCustomer.jsp");
        } else {
            String cid = request.getParameter("cid");
            String cname = request.getParameter("cname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            int status = Integer.parseInt(request.getParameter("status"));
            Customer cus = new Customer(cid, cname, username, password, address, status, phone, status);
            DAOCustomer dao = new DAOCustomer();
            int n = dao.add(cus);
            if (n == 1) {
                response.sendRedirect("CustomerController");
            } else {
                dispatch(request, response, "admin/formCustomer.jsp");
            }
        }

    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOCustomer dao = new DAOCustomer();
        String submit = request.getParameter("submit");
        if (submit == null) {
            String id = request.getParameter("id");
            Customer cus = dao.get(id);
            request.setAttribute("data", cus);
            request.setAttribute("action", "update");
            dispatch(request, response, "admin/formCustomer.jsp");
        } else {
            String cid = request.getParameter("cid");
            String cname = request.getParameter("cname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            int status = Integer.parseInt(request.getParameter("status"));
            int isAdmin = Integer.parseInt(request.getParameter("isAdmin"));
            Customer cus = new Customer(cid, cname, username, password, address, status, phone, isAdmin);
            int n = dao.update(cus);
            if (n == 1) {
                response.sendRedirect("CustomerController");
            } else {
                dispatch(request, response, "admin/formCustomer.jsp");
            }
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        if (id!=null){
            DAOCustomer dao = new DAOCustomer();
            int n = dao.remove(id);
            if (n==1){
                ServletUtil.addSuccessMessage(request, "Successfully remove Customer with ID = " + id + ".");
            }
        }
        dispatch(request, response, "CustomerController?go=view");
    }

}
