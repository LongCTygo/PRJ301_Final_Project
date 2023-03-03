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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public BillDisplay(String username, String cname, String bid, String dateCreate, String recAddress, String recPhone, String note, double totalMoney, int status, String cid) {
        super(bid, dateCreate, recAddress, recPhone, note, totalMoney, status, cid);
        this.username = username;
        this.cname = cname;
    }

    public BillDisplay(String username, String cname, String bid, String recAddress, String recPhone, String note, int status, String cid) {
        super(bid, recAddress, recPhone, note, status, cid);
        this.username = username;
        this.cname = cname;
    }

    private String username;
    private String cname;

}
