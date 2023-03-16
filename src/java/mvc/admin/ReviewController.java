/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mvc.admin;

import dao.DAOReview;
import display.ReviewDisplay;
import entity.Review;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ServletUtil;
import static utils.ServletUtil.dispatch;
import utils.SessionUtil;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ReviewController", urlPatterns = {"/ReviewController"})
public class ReviewController extends HttpServlet {
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
        if ("view".equals(go)){
            view(request, response);
        } else if (go.equals("delete")){
            delete(request, response);
        }
    }
    
    private static void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        DAOReview dao = new DAOReview();
        String sql = "SELECT * from Review where comment like ?";
        String query = request.getParameter("query");
        if (query == null){
            query = "";
        }
        Vector<Review> vector = new Vector<>();
        try {
            PreparedStatement prep = dao.getPrep(sql);
            prep.setString(1, "%" + query + "%");
            vector = dao.getAll(prep);
        } catch (SQLException ex) {
            Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("list", vector);
        dispatch(request, response, "admin/viewReview.jsp");
    }
    
    private static void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String cid = request.getParameter("cid");
        String pid = request.getParameter("pid");
        DAOReview dao = new DAOReview();
        dao.remove(pid, cid);
        view(request, response);
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

}
