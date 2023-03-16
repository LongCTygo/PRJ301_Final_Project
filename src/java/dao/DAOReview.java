/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connect.DAOEntity;
import display.ReviewDisplay;
import entity.Review;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class DAOReview extends DAOEntity<Review> {

    @Override
    public int add(Review entity) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[Review]\n"
                + "           ([cid]\n"
                + "           ,[pid]\n"
                + "           ,[score]\n"
                + "           ,[comment])\n"
                + "     VALUES\n"
                + "           (?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, entity.getCid());
            pre.setString(2, entity.getPid());
            pre.setInt(3, entity.getScore());
            pre.setString(4, entity.getReview());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getErrorCode();
        }
        return n;
    }

    @Override
    public int update(Review entity) {
        int n = 0;
        String sql = "UPDATE [dbo].[Review]\n"
                + "   SET [score] = ?\n"
                + "      ,[comment] = ?\n"
                + "      ,[writtenDate] = ?\n"
                + " WHERE [cid] = ?"
                + " AND [pid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, entity.getScore());
            pre.setString(2, entity.getReview());
            pre.setString(3, entity.getDate());
            pre.setString(4, entity.getCid());
            pre.setString(5, entity.getPid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    @Override
    public Vector<Review> getAll() {
        return getAll("SELECT * from Review");
    }

    @Override
    public Vector<Review> getAll(String sql) {
        Vector<Review> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                String cid = rs.getString(1);
                String pid = rs.getString(2);
                String date = rs.getString(3);
                int score = rs.getInt(4);
                String comment = rs.getString(5);
                Review r = new Review(cid, pid, date, score, date);
                vector.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<ReviewDisplay> getDisplay() {
        return getDisplay("SELECT * from Review a, Customer b WHERE a.cid = b.cid");
    }

    @Deprecated
    public Vector<ReviewDisplay> getDisplay(String sql) {
        Vector<ReviewDisplay> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                String cname = rs.getString(7);
                String cid = rs.getString(1);
                String pid = rs.getString(2);
                String date = rs.getString(3);
                int score = rs.getInt(4);
                String review = rs.getString(5);
                ReviewDisplay r = new ReviewDisplay(cname, cid, pid, review, score, date);
                vector.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<ReviewDisplay> getDisplay(PreparedStatement statement) {
        Vector<ReviewDisplay> vector = new Vector<>();
        ResultSet rs = this.getData(statement);
        try {
            while (rs.next()) {
                String cname = rs.getString(7);
                String cid = rs.getString(1);
                String pid = rs.getString(2);
                String date = rs.getString(3);
                int score = rs.getInt(4);
                String review = rs.getString(5);
                ReviewDisplay r = new ReviewDisplay(cname, cid, pid, review, score, date);
                vector.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    @Override
    public Vector<Review> getAll(PreparedStatement statement) {
        Vector<Review> vector = new Vector<>();
        ResultSet rs = this.getData(statement);
        try {
            while (rs.next()) {
                String cid = rs.getString(1);
                String pid = rs.getString(2);
                String date = rs.getString(3);
                int score = rs.getInt(4);
                String comment = rs.getString(5);
                Review r = new Review(cid, pid, comment, score, date);
                vector.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    @Override
    public int remove(String pid, String cid) {
        int n = 0;
        String sql = "delete from Review where pid = ? and cid = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, pid);
            pre.setString(2, cid);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBillDetail.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getErrorCode();
        }
        return n;
    }
    
    

}
