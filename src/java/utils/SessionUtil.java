/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 *
 * @author LongCT_, LongCTHE170489
 */
public final class SessionUtil {

    private SessionUtil() {
    }
    public static final String CART_ATTRIBUTE = "cart";
    public static final String USER_ATTRIBUTE = "cid";
    public static final String ADMIN_ATTRIBUTE = "admin";

    /**
     * Get the hash table representing the cart of the current session. The
     * function will create a new cart and set it to the session if one does not
     * exist
     *
     * @param session The current session
     * @return the current cart, or a new (empty) one if none exist.
     * @throws NullPointerException if the session is null
     */
    public static Hashtable<String, Integer> getCart(HttpSession session) {
        Hashtable<String, Integer> cart = (Hashtable<String, Integer>) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Hashtable<>();
            setCart(session, cart);
        }
        return cart;
    }

    /**
     * Set the cart of the current session to the new one.
     *
     * @param session The current session
     * @param cart A Hash Table representing a cart
     * @throws NullPointerException if the session is null
     */
    public static void setCart(HttpSession session, Hashtable<String, Integer> cart) {
        session.setAttribute(CART_ATTRIBUTE, cart);
    }

    /**
     * Add an amount of items with the product id to the cart. Use a negative
     * value for amount to subtract. This method will remove that item from the
     * cart if the item amount became non-positive after adding.
     *
     * @param session The current session
     * @param pid The product ID
     * @param amount The amount of product to add.
     * @return the amount of that item currently inside that cart. 0 if it is
     * removed.
     * @throws NullPointerException if the session is null
     */
    public static int addtoCart(HttpSession session, String pid, int amount) {
        Hashtable<String, Integer> cart = getCart(session);
        Integer value = cart.get(pid);
        if (value == null) {
            cart.put(pid, amount);
        } else {
            cart.put(pid, value + amount);
        }
        //check amount
        int newAmount = cart.get(pid);
        if (newAmount <= 0) {
            cart.remove(pid);
            return 0;
        }
        return newAmount;
    }

    /**
     * Remove an item from a cart.
     *
     * @param session The current session
     * @param pid The product ID
     * @return The amount of that product before removing.
     * @throws NullPointerException if the session is null
     */
    public static int removeFromCart(HttpSession session, String pid) {
        Hashtable<String, Integer> cart = getCart(session);
        return cart.remove(pid);
    }

    /**
     * Set the amount of item for an item inside the cart. This method will
     * remove that item from the cart if the item amount is non-positive.
     * Consider using
     * {@link SessionUtil#removeFromCart(jakarta.servlet.http.HttpSession, java.lang.String) removeFromCart.}
     * for removing.
     *
     * @param session The current session
     * @param pid The product ID
     * @param amount The amount of product to set.
     */
    public static void setToCart(HttpSession session, String pid, int amount) {
        Hashtable<String, Integer> cart = getCart(session);
        if (amount <= 0) {
            cart.remove(pid);
        } else {
            cart.put(pid, amount);
        }

    }

    /**
     * Clear the cart.
     *
     * @param session The current session
     * @throws NullPointerException if the session is null
     */
    public static void clearCart( HttpSession session) {
        session.removeAttribute(CART_ATTRIBUTE);
    }

    /**
     * Check if the current session is logged in.
     *
     * @param session
     * @return whether the user is logged in or not.
     * @throws NullPointerException if the session is null
     */
    public static boolean isSessionLoggedIn(HttpSession session) {
        return session.getAttribute(USER_ATTRIBUTE) != null;
    }

    /**
     * Check if the current session is an admin.
     *
     * @param session
     * @return whether the user is an admin or not.
     * @throws NullPointerException if the session is null
     */
    public static boolean isSessionAdmin(HttpSession session) {
        return session.getAttribute(ADMIN_ATTRIBUTE) != null;
    }
}
