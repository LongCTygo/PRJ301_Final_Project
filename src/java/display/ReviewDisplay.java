/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package display;

import entity.Review;

/**
 *
 * @author ADMIN
 */
public class ReviewDisplay extends Review {

    public ReviewDisplay(String cname, String cid, String pid, String review, int score, String date) {
        super(cid, pid, review, score, date);
        this.cname = cname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
    private String cname;
}
