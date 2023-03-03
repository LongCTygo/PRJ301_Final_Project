/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mvc.admin;

import dao.DAOBill;
import dao.DAOBillDetail;
import display.BillDisplay;
import jakarta.servlet.http.HttpSession;
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
import utils.ServletUtil;
import static utils.ServletUtil.dispatch;
import utils.SessionUtil;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "BillController", urlPatterns = {"/BillController"})
public class BillController extends HttpServlet {
    
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
                view(request, response);
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

    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        DAOBill dao = new DAOBill();
        String sql = "SELECT * from Bill a join Customer b on a.cid = b.cid "
                + "WHERE b.username like ? "
                + "AND a.status != ? "
                + "ORDER BY a.cid";
        PreparedStatement prep = dao.getPrep(sql);
        //Query
        String query = request.getParameter("query");
        if (query == null) {
            query = "";
        }
        prep.setString(1, "%" + query + "%");
        request.setAttribute("query", query);
        //Status
        String status = request.getParameter("status");
        int s;
        try {
            s = Integer.parseInt(status);
        } catch (NumberFormatException ex) {
            s = -1;
        }
        prep.setInt(2, s);
        request.setAttribute("query_status", s);
        Vector<BillDisplay> all = dao.getDisplay(prep);
        request.setAttribute("list", all);
        dispatch(request, response, "admin/viewBill.jsp");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        //For bill, it must delete all billdetails first
        String id = request.getParameter("id");
        if (id != null) {
            DAOBill dao = new DAOBill();
            ///Remove bill details
            PreparedStatement statement = dao.getPrep("DELETE FROM BillDetail where bid = ?");
            statement.setString(1, id);
            statement.execute();
            //Remove the bill
            int n = dao.remove(id);
            if (n == 1) {
                ServletUtil.addSuccessMessage(request, "Successfully remove Bill with ID = " + id + ".");
            } else {
                ServletUtil.addErrorMessage(request, "Failed to delete, likely due to exisiting relationship.");
            }
        }
        view(request, response);
    }

}
