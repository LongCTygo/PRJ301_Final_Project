/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mvc.admin;

import dao.DAOBill;
import dao.DAOBillDetail;
import display.BillDetailDisplay;
import entity.Bill;
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
@WebServlet(name = "BillDetailController", urlPatterns = {"/BillDetailController"})
public class BillDetailController extends HttpServlet {

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
        //
        String go = request.getParameter("go");
        if (go == null) {
            go = DEFAULT_GO;
        }
        try {
            if (go.equals("view")) {
                view(request, response, true);
            } else if (go.equals("viewDetail")) {
                viewDetail(request, response);
            } else if (go.equals("add")) {
                add(request, response);
            } else if (go.equals("update")) {
                update(request, response);
            } else if (go.equals("delete")) {
                delete(request, response);
            } else {
                view(request, response, true);
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

    private void view(HttpServletRequest request, HttpServletResponse response, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void viewDetail(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        //Get the bill
        String bid = request.getParameter("id");
        if (bid == null){
            ServletUtil.addErrorMessage(request, "No Bill ID was given.");
            dispatch(request, response, "admin/viewBill.jsp");
            return;
        }
        DAOBill daob = new DAOBill();
        request.setAttribute("bill", daob.get(bid));
        //Get all bill detail
        DAOBillDetail daobd = new DAOBillDetail();
        PreparedStatement statement = daobd.getPrep("SELECT * from BillDetail a join Product b on a.pid = b.pid WHERE a.bid = ?");
        statement.setString(1, bid);
        Vector<BillDetailDisplay> billDetails = daobd.getDisplay(statement);
        //Set attributes for the display tab
        request.setAttribute("list", billDetails);
        dispatch(request, response, "admin/billDetail.jsp");
    }
}
