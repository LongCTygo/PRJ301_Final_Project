
package dao;
import connect.DAOEntity;
import entity.Admin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DAOAdmin extends DAOEntity<Admin>{
    
    @Override
    public int add(Admin admin){
        int n = 0;
        String sql = "INSERT INTO [dbo].[admin]\n" +
"           ([admin]\n" +
"           ,[password])\n" +
"     VALUES(?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, admin.getAdmin());
            pre.setString(2, admin.getPassword());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    @Override
    public int update(Admin admin){
        int n = 0;
        String sql = "UPDATE [dbo].[admin]\n" +
"   SET [password] = ?\n" +
" WHERE [admin] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, admin.getPassword());
            pre.setString(2, admin.getAdmin());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return n;
    }
    @Override
    public Vector<Admin> getAll(String sql){
        Vector<Admin> vector = new Vector<Admin>();
        ResultSet rs = this.getData(sql);
        try {
            while(rs.next()){
                String admin = rs.getString("admin");
                String password = rs.getString("password");
                Admin Admin = new Admin(admin, password);
                vector.add(Admin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    @Override
    public int remove(String admin){
        int n = 0;
        String sql = "Delete from admin where admin = '" + admin+"'";
        Statement state;
        try {
            state = conn.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    

    @Override
    public Vector<Admin> getAll() {
        return getAll("SELECT * from Admin");
    }

    @Override
    public Vector<Admin> getAll(PreparedStatement statement){
        Vector<Admin> vector = new Vector<>();
        ResultSet rs = this.getData(statement);
        try {
            while(rs.next()){
                String admin = rs.getString("admin");
                String password = rs.getString("password");
                Admin Admin = new Admin(admin, password);
                vector.add(Admin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    
    public String login(String user, String pass){
        String sql = "select * from Admin where admin=? and "
                + " password = ?";
        PreparedStatement pre;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, user);
            pre.setString(2, pass);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}