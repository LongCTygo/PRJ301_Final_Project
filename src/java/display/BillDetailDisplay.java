/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package display;

import entity.BillDetail;

/**
 *
 * @author ADMIN
 */
public class BillDetailDisplay extends BillDetail{
    
    private String pname;
    private String image;

    public BillDetailDisplay(String pname, String bid, String pid, String image, int buyQuantity, double buyPrice, double subtotal) {
        super(bid, pid, buyQuantity, buyPrice, subtotal);
        this.pname = pname;
        this.image = image;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
