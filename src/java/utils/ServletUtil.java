/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public final class ServletUtil {

    private ServletUtil() {
    }

    public static final String SUCCESS_MSG_ATTRIBUTE = "success";
    public static final String ERROR_MSG_ATTRIBUTE = "error";

    /**
     * Forwards a request from a servlet to the URL
     *
     * @param request a ServletRequest object that represents the request the
     * client makes of the servlet
     * @param response a ServletResponse object that represents the response the
     * servlet returns to the client
     * @param url The target URL
     * @throws ServletException if the target resource throws this exception
     * @throws IOException if the target resource throws this exception
     * @see RequestDispatcher#forward(jakarta.servlet.ServletRequest,
     * jakarta.servlet.ServletResponse)
     * @see HttpServletRequest#getRequestDispatcher(java.lang.String)
     */
    public static void dispatch(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        RequestDispatcher dispatch
                = request.getRequestDispatcher(url);
        dispatch.forward(request, response);
    }

    public static void addSuccessMessage(HttpServletRequest request, String msg) {
        Vector<String> success = (Vector<String>) request.getAttribute(SUCCESS_MSG_ATTRIBUTE);
        if (success == null) {
            success = new Vector<>();
        }
        success.add(msg);
        request.setAttribute(SUCCESS_MSG_ATTRIBUTE, success);
    }

    public static void addErrorMessage(HttpServletRequest request, String msg) {
        Vector<String> errors = (Vector<String>) request.getAttribute(ERROR_MSG_ATTRIBUTE);
        if (errors == null) {
            errors = new Vector<>();
        }
        errors.add(msg);
        request.setAttribute("error", errors);
    }

    public static void printErrorMessages(HttpServletRequest request, JspWriter out) {
        Object ob = request.getAttribute(ERROR_MSG_ATTRIBUTE);
        if (ob == null) {
            Logger.getLogger(ServletUtil.class.getName()).log(Level.WARNING, "Error Messages is null.");
            return;
        }
        Vector<String> vector;
        try {
            vector = (Vector<String>) ob;
        } catch (ClassCastException ex) {
            Logger.getLogger(ServletUtil.class.getName()).log(Level.WARNING, "Error Messages is not an instance of Vector<String>?");
            return;
        }
        String msg = "<div class=\"alert alert-danger alert-dismissible fade show\"\" role=\"alert\">\n"
                + "                            %s\n"
                + "                            <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n"
                + "                                <span aria-hidden=\"true\">&times;</span>\n"
                + "                            </button>\n"
                + "                        </div>";
        for (String m : vector){
            try {
                out.print(String.format(msg, m));
            } catch (IOException ex) {
                Logger.getLogger(ServletUtil.class.getName()).log(Level.SEVERE, "Did not print due to a IOException", ex);
            }
        }
    }
    
    public static void printSuccessMessages(HttpServletRequest request, JspWriter out) {
        Object ob = request.getAttribute(SUCCESS_MSG_ATTRIBUTE);
        if (ob == null) {
            Logger.getLogger("ServletUtils").log(Level.WARNING, "Success Messages is null.");
            return;
        }
        Vector<String> vector;
        try {
            vector = (Vector<String>) ob;
        } catch (ClassCastException ex) {
            Logger.getLogger("ServletUtils").log(Level.WARNING, "Success Messages is not an instance of Vector<String>?");
            return;
        }
        String msg = "<div class=\"alert alert-success alert-dismissible fade show\"\" role=\"alert\">\n"
                + "                            %s\n"
                + "                            <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n"
                + "                                <span aria-hidden=\"true\">&times;</span>\n"
                + "                            </button>\n"
                + "                        </div>";
        for (String m : vector){
            try {
                out.print(String.format(msg, m));
            } catch (IOException ex) {
                Logger.getLogger(ServletUtil.class.getName()).log(Level.SEVERE, "Did not print due to a IOException", ex);
            }
        }
    }
}
