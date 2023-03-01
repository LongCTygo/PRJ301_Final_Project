/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mvc;

import dao.DAOCustomer;
import entity.Customer;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Vector;
import utils.SessionUtil;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {

    public static String DEFAULT_GO = "home";

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
            if (go.equals("home")) {
                dispatch(request, response, "admin/index.jsp");
            } else if (go.equals("viewCustomer")) {
                viewCustomer(request, response);
            }
        } catch (SQLException ex) {

        }
    }

    void dispatch(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        //call jsp
        RequestDispatcher dispatch
                = request.getRequestDispatcher(url);
        dispatch.forward(request, response);
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

    private void viewCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        DAOCustomer dao = new DAOCustomer();
        String sql = "SELECT * from Customer "
                + "WHERE (cname like ? or username like ?) "
                + "AND status != ? "
                + "ORDER BY cid";
        PreparedStatement prep = dao.getPrep(sql);
        //Query
        String query = request.getParameter("query");
        if (query == null){
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
        } catch (NumberFormatException ex){
            s = -1;
        }
        prep.setInt(3, s);
        request.setAttribute("status", s);
        Vector<Customer> all = dao.getAll(prep);
        request.setAttribute("list", all);
        dispatch(request, response, "admin/viewCustomer.jsp");
    }

}
