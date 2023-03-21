/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mvc.admin;

import dao.DAOBill;
import display.BillDisplay;
import entity.Bill;
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
                view(request, response, true);
            } else if (go.equals("add")) {
                add(request, response);
            } else if (go.equals("update")) {
                update(request, response);
            } else if (go.equals("delete")) {
                delete(request, response);
            } else if (go.equals("updateStatus")) {
                updateStatus(request, response);
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

    private void view(HttpServletRequest request, HttpServletResponse response, boolean isDirect) throws ServletException, IOException, SQLException {
        DAOBill dao = new DAOBill();
        String sql = "SELECT * from Bill a join Customer b on a.cid = b.cid "
                + "WHERE b.username like ? "
                + "AND a.status in (?,?,?) "
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
        String status = isDirect ? request.getParameter("status") : "-1";
        boolean isSearchAll = false;
        int s = -1;
        try {
            s = Integer.parseInt(status);
            if (s >= 0 && s <= 2) {
                for (int i = 2; i < 5; i++) {
                    prep.setInt(i, s);
                }
            } else {
                isSearchAll = true;
            }
        } catch (NumberFormatException ex) {
            isSearchAll = true;
        }
        if (isSearchAll) {
            prep.setInt(2, 0);
            prep.setInt(3, 1);
            prep.setInt(4, 2);
        }
        request.setAttribute("query_status", isSearchAll ? -1 : s);
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
            BillDisplay bill = dao.get(id);
            if (bill.getStatus() == 0) {
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
            } else {
                ServletUtil.addErrorMessage(request, "You can only remove Bill that has the status 'Wait'.");
            }

        }
        view(request, response);
    }

    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        view(request, response, false);
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String s = request.getParameter("status");
        String billID = request.getParameter("bill");
        try {
            int status = Integer.parseInt(s);
            if (status >= 0 && status <= 2) {
                DAOBill dao = new DAOBill();
                Bill bill = dao.get(billID);
                if (bill != null) {
                    if (bill.getStatus() > status) {
                        ServletUtil.addErrorMessage(request, "You cannot change the status from " + bill.getStatus() + " to " + status + ".");
                    } else {
                        bill.setStatus(status);
                        dao.update(bill);
                        ServletUtil.addSuccessMessage(request, "Successfully modified the bill " + billID);
                    }
                } else {
                    ServletUtil.addErrorMessage(request, "Could not find a bill with billid = " + billID);
                }
            } else {
                ServletUtil.addErrorMessage(request, "Status cannot be " + status);
            }
        } catch (NumberFormatException ex) {
            ServletUtil.addErrorMessage(request, "Couldn't update, status is invalid.");
        }
        view(request, response);
    }
}
