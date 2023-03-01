/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */
public final class ServletUtil {
    private ServletUtil(){}
    
    /**
     * Forwards a request from a servlet to the URL
     * 
     * @param request a ServletRequest object that represents the request the client makes of the servlet
     * @param response a ServletResponse object that represents the response the servlet returns to the client
     * @param url The target URL
     * @throws ServletException if the target resource throws this exception
     * @throws IOException if the target resource throws this exception
     * @see RequestDispatcher#forward(jakarta.servlet.ServletRequest, jakarta.servlet.ServletResponse) 
     * @see HttpServletRequest#getRequestDispatcher(java.lang.String) 
     */
    public static void dispatch(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException
 {
        RequestDispatcher dispatch
                = request.getRequestDispatcher(url);
        dispatch.forward(request, response);
    }
}
