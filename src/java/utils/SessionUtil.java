/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import dao.DAOCustomer;
import entity.Customer;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Hashtable;

/**
 *
 * @author ADMIN
 */
public final class SessionUtil{
    private SessionUtil(){}
    public static final String CART_ATTRIBUTE = "cart";
    public static final String USER_ATTRIBUTE = "cid";
    public static final String ADMIN_ATTRIBUTE = "admin";
    
    /**
     * Get the hash table representing the cart of the current session. 
     * The function will create a new cart and set it to the session if one does not exist
     * @param session The current session
     * @return the current cart, or a new (empty) one if none exist.
     * @throws NullPointerException if the session is null
     */
    public static Hashtable<String, Integer> getCart(HttpSession session){
        Hashtable<String, Integer> cart = (Hashtable<String, Integer>) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Hashtable<>();
            setCart(session, cart);
        }
        return cart;
    }
    
    /**
     * Set the cart of the current session to the new one.
     * @param session The current session
     * @param cart A Hash Table representing a cart
     * @throws NullPointerException if the session is null
     */
    public static void setCart(HttpSession session, Hashtable<String, Integer> cart){
        session.setAttribute(CART_ATTRIBUTE, cart);
    }
    
    /**
     * Check if the current session is logged in.
     * @param session
     * @return whether the user is logged in or not.
     * @throws NullPointerException if the session is null
     */
    public static boolean isSessionLoggedIn(HttpSession session){
        return session.getAttribute(USER_ATTRIBUTE) != null;
    }
    
    public static boolean isSessionAdmin(HttpSession session){
        return session.getAttribute(ADMIN_ATTRIBUTE) != null;
    }
}
