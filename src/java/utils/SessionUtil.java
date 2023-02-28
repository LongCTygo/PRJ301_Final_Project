/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 *
 * @author ADMIN
 */
public class SessionUtil {
    public static Hashtable<String, Integer> getCart(HttpSession session){
        Hashtable<String, Integer> cart = (Hashtable<String, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new Hashtable<>();
        }
        return cart;
    }
    
    public static void setCart(HttpSession session, Hashtable<String, Integer> cart){
        session.setAttribute("cart", cart);
    }
}
