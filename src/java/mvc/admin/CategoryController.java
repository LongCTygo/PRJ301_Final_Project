/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mvc.admin;

import dao.DAOCategory;
import entity.Category;
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
@WebServlet(name = "CategoryController", urlPatterns = {"/CategoryController"})
public class CategoryController extends HttpServlet {
    
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
        DAOCategory dao = new DAOCategory();
        String sql = "SELECT * from Category WHERE cateName like ? and status != ? ORDER BY cateId ASC";
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
        Vector<Category> all = dao.getAll(prep);request.setAttribute("list", all);
        dispatch(request, response, "admin/viewCategory.jsp");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String submit = request.getParameter("submit");
        if (submit == null) {
            request.setAttribute("action", "add");
            dispatch(request, response, "admin/formCategory.jsp");
        } else {
            String cname = request.getParameter("catename");
            int status = Integer.parseInt(request.getParameter("status"));
            Category cat = new Category(cname, status);
            DAOCategory dao = new DAOCategory();
            int n = dao.add(cat);
            if (n == 1) {
                ServletUtil.addSuccessMessage(request, "Successfully added category " + cat.getCateId() + ".");
            } else if (n == SQLErrorCodeUtil.UNIQUE_KEY_VIOLATION) {
                ServletUtil.addErrorMessage(request, "Failed to add category " + cat.getCateId() + " since a category with such ID already exist.");
            } else {
                ServletUtil.addErrorMessage(request, "Failed to add category " + cat.getCateId() + ". Error = " + n + ".");
            }
            view(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        DAOCategory dao = new DAOCategory();
        String submit = request.getParameter("submit");
        if (submit == null) {
            String id = request.getParameter("id");
            Category cus = dao.get(id);
            request.setAttribute("data", cus);
            request.setAttribute("action", "update");
            dispatch(request, response, "admin/formCategory.jsp");
        } else {
            int cateid = Integer.parseInt(request.getParameter("cateid"));
            String catename = request.getParameter("catename");
            int status = Integer.parseInt(request.getParameter("status"));
            Category cus = new Category(cateid, catename, status);
            int n = dao.update(cus);
            if (n == 1) {
                ServletUtil.addSuccessMessage(request, "Successfully updated user " + cus.getCateId() + ".");
            } else {
                ServletUtil.addErrorMessage(request, "Failed to update user " + cus.getCateId() + ". Error code: "+ n + ".");
            }
            view(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            DAOCategory dao = new DAOCategory();
            int n = dao.remove(id);
            if (n == 1) {
                ServletUtil.addSuccessMessage(request, "Successfully remove Customer with ID = " + id + ".");
            } else {
                ServletUtil.addErrorMessage(request, "Failed to delete, likely due to exisiting relationship.");
            }
        }
        view(request, response);
    }
    
    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        view(request, response, false);
    }
}
