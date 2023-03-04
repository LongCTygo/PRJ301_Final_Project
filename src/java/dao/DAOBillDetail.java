package dao;

import connect.DAOEntity;
import display.BillDetailDisplay;
import entity.BillDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOBillDetail extends DAOEntity<BillDetail> {

    @Override
    public int add(BillDetail billdetail) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[BillDetail]\n"
                + "           ([bid]\n"
                + "           ,[pid]\n"
                + "           ,[buyQuantity]\n"
                + "           ,[buyPrice]\n"
                + "           ,[subtotal])\n"
                + "     VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, billdetail.getBid());
            pre.setString(2, billdetail.getPid());
            pre.setInt(3, billdetail.getBuyQuantity());
            pre.setDouble(4, billdetail.getBuyPrice());
            pre.setDouble(5, billdetail.getSubtotal());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n;
    }

    @Override
    public int update(BillDetail billdetail) {
        int n = 0;
        String sql = "UPDATE [dbo].[BillDetail]\n"
                + "   SET [buyQuantity] = ?\n"
                + "      ,[buyPrice] = ?\n"
                + "      ,[subtotal] = ?\n"
                + " WHERE [bid] = ? and [pid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, billdetail.getBuyQuantity());
            pre.setDouble(2, billdetail.getBuyPrice());
            pre.setDouble(3, billdetail.getSubtotal());
            pre.setString(4, billdetail.getBid());
            pre.setString(5, billdetail.getPid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Vector<BillDetail> getAll(String sql) {
        Vector<BillDetail> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                int buyquantity = rs.getInt("buyQuantity");
                double buyprice = rs.getDouble("buyPrice");
                double subtotal = rs.getDouble("subtotal");
                String bid = rs.getString("bid");
                String pid = rs.getString("pid");
                BillDetail bl = new BillDetail(bid, pid, buyquantity, buyprice, subtotal);
                vector.add(bl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    @Override
    public int remove(String pid, String bid) {
        int n = 0;
        String sql = "delete from BillDetail where pid ='" + pid + "' and bid ='" + bid + "'";
        try {
            Statement state = conn.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    @Override
    public Vector<BillDetail> getAll() {
        return getAll("SELECT * from BillDetail");
    }

    @Override
    public Vector<BillDetail> getAll(PreparedStatement statement) {
        Vector<BillDetail> vector = new Vector<>();
        ResultSet rs = this.getData(statement);
        try {
            while (rs.next()) {
                int buyquantity = rs.getInt("buyQuantity");
                double buyprice = rs.getDouble("buyPrice");
                double subtotal = rs.getDouble("subtotal");
                String bid = rs.getString("bid");
                String pid = rs.getString("pid");
                BillDetail bl = new BillDetail(bid, pid, buyquantity, buyprice, subtotal);
                vector.add(bl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    
    public Vector<BillDetailDisplay> getDisplay(PreparedStatement statement){
        Vector<BillDetailDisplay> vector = new Vector<>();
        try {
            ResultSet rs = this.getData(statement);
            while (rs.next()) {
                String pname = rs.getString("pname");
                String bid = rs.getString("bid");
                String pid = rs.getString("pid");
                String image = rs.getString("image");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double subtotal = rs.getDouble("subtotal");
                BillDetailDisplay bdd = new BillDetailDisplay(pname, bid, pid, image, quantity, price, subtotal);
                vector.add(bdd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    
    public Vector<BillDetailDisplay> getDisplay(){
        try {
            PreparedStatement statement = this.getPrep("SELECT * from BillDetail a join Product b on a.pid = b.pid");
            return getDisplay(statement);
        } catch (SQLException ex) {
            Logger.getLogger(DAOBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Vector<>();
    }
}
