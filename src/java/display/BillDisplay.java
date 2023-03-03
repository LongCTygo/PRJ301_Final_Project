/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package display;

import entity.Bill;

/**
 *
 * @author ADMIN
 */
public class BillDisplay extends Bill {

    private String username;

    public BillDisplay(String cname, String bid, String dateCreate, String recAddress, String recPhone, String note, double totalMoney, int status, String cid) {
        super(bid, dateCreate, recAddress, recPhone, note, totalMoney, status, cid);
        this.username = cname;
    }

    public BillDisplay(String cname, String bid, String recAddress, String recPhone, String note, int status, String cid) {
        super(bid, recAddress, recPhone, note, status, cid);
        this.username = cname;
    }

    public String getCname() {
        return username;
    }

    public void setCname(String cname) {
        this.username = cname;
    }

}
