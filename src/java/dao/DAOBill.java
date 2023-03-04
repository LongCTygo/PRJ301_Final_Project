package dao;

import connect.DAOEntity;
import display.BillDisplay;
import entity.Bill;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOBill extends DAOEntity<Bill> {

    @Deprecated
    public int addBill(Bill bill) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[Bill]\n"
                + "           ([bid]\n"
                + "           ,[dateCreate]\n"
                + "           ,[recAddress]\n"
                + "           ,[recPhone]\n"
                + "           ,[note]\n"
                + "           ,[totalMoney]\n"
                + "           ,[status]\n"
                + "           ,[cid])\n"
                + "     VALUES('" + bill.getBid() + "','" + bill.getDateCreate() + "','" + bill.getRecAddress()
                + "','" + bill.getRecPhone() + "','" + bill.getNote() + "','" + bill.getTotalMoney()
                + "','" + bill.getStatus() + "','" + bill.getCid() + "')";
        try {
            Statement state = conn.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    @Override
    public int add(Bill bill) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[Bill]\n"
                + "           ([bid]\n"
                + "           ,[recAddress]\n"
                + "           ,[recPhone]\n"
                + "           ,[note]\n"
                + "           ,[totalMoney]\n"
                + "           ,[status]\n"
                + "           ,[cid])\n"
                + "     VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, bill.getBid());
            pre.setString(2, bill.getRecAddress());
            pre.setString(3, bill.getRecPhone());
            pre.setString(4, bill.getNote());
            pre.setDouble(5, bill.getTotalMoney());
            pre.setInt(6, bill.getStatus());
            pre.setString(7, bill.getCid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n;
    }

    @Override
    public int update(Bill bill) {
        int n = 0;
        String sql = "UPDATE [dbo].[Bill]\n"
                + "   SET [recAddress] = ?\n"
                + "      ,[recPhone] = ?\n"
                + "      ,[note] = ?\n"
                + "      ,[totalMoney] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[cid] = ?\n"
                + " WHERE [bid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, bill.getRecAddress());
            pre.setString(2, bill.getRecPhone());
            pre.setString(3, bill.getNote());
            pre.setDouble(4, bill.getTotalMoney());
            pre.setInt(5, bill.getStatus());
            pre.setString(6, bill.getCid());
            pre.setString(7, bill.getBid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n;
    }

    @Override
    public Vector<Bill> getAll(String sql) {
        Vector<Bill> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                String dateCreate = rs.getString("dateCreate");
                String recAddress = rs.getString("recAddress");
                String recPhone = rs.getString("recPhone");
                String note = rs.getString("note");
                double totalMoney = rs.getDouble("totalMoney");
                int status = rs.getInt("status");
                String cid = rs.getString("cid");
                String bid = rs.getString("bid");
                Bill bill = new Bill(bid, dateCreate, recAddress, recPhone, note, totalMoney, status, cid);
                vector.add(bill);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    @Override
    public int remove(String bid) {
        int n = 0;
        String sql = "delete from Bill where bid = '" + bid + "'";
        ResultSet rs = this.getData("select * from BillDetail where bid ='" + bid + "'");
        try {
            if (rs.next()) {
                n = -1;
            } else {
                Statement state = conn.createStatement();
                n = state.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    @Override
    public Vector<Bill> getAll() {
        return getAll("SELECT * from Bill");
    }

    @Override
    public Vector<Bill> getAll(PreparedStatement statement) {
        Vector<Bill> vector = new Vector<>();
        ResultSet rs = this.getData(statement);
        try {
            while (rs.next()) {
                String dateCreate = rs.getString("dateCreate");
                String recAddress = rs.getString("recAddress");
                String recPhone = rs.getString("recPhone");
                String note = rs.getString("note");
                double totalMoney = rs.getDouble("totalMoney");
                int status = rs.getInt("status");
                String cid = rs.getString("cid");
                String bid = rs.getString("bid");
                Bill bill = new Bill(bid, dateCreate, recAddress, recPhone, note, totalMoney, status, cid);
                vector.add(bill);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    
    public Vector<BillDisplay> getDisplay(PreparedStatement statement){
        Vector<BillDisplay> vector = new Vector<>();
        ResultSet rs = this.getData(statement);
        try {
            while (rs.next()) {
                String dateCreate = rs.getString("dateCreate");
                String recAddress = rs.getString("recAddress");
                String recPhone = rs.getString("recPhone");
                String note = rs.getString("note");
                double totalMoney = rs.getDouble("totalMoney");
                int status = rs.getInt("status");
                String cid = rs.getString("cid");
                String bid = rs.getString("bid");
                String username = rs.getString("username");
                String cname = rs.getString("cname");
                BillDisplay bill = new BillDisplay(username, cname, bid, 
                        dateCreate, recAddress, recPhone, note, 
                        totalMoney, status, cid);
                vector.add(bill);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    
    public Vector<BillDisplay> getDisplay(){
        try {
            PreparedStatement statement = this.getPrep("SELECT * from Bill a join Customer b on a.cid = b.cid");
            return this.getDisplay(statement);
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public BillDisplay get(String bid) {
        try {
            PreparedStatement statement = this.getPrep("SELECT * from Bill a join Customer b on a.cid = b.cid where bid = ?");
            statement.setString(1, bid);
            Vector<BillDisplay> all = this.getDisplay(statement);
            if (!all.isEmpty()) {
                return all.get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
