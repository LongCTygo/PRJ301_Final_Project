/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package display;

import entity.Product;

/**
 *
 * @author ADMIN
 */
public class ProductDisplay extends Product{
    private String cate;

    public String getCate() {
        return cate;
    }

    public ProductDisplay(String cate, String pid, String pname, int quantity, double price, String image, String description, int status, int cateID) {
        super(pid, pname, quantity, price, image, description, status, cateID);
        this.cate = cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }
}

