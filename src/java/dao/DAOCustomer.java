package dao;
import connect.DAOEntity;
import entity.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DAOCustomer extends DAOEntity<Customer>{
    @Deprecated
    public int AddCustomer(Customer customer){
        int n = 0;
        String sql = "INSERT INTO [Customer]\n" +
"           ([cid]\n" +
"           ,[cname]\n" +
"           ,[username]\n" +
"           ,[password]\n" +
"           ,[address]\n" +
"           ,[status])\n" +
"     VALUES('"+customer.getCid()+"','"+customer.getCname()+
       "','"+customer.getUsername()+"','"+customer.getPassword()+
       "','"+customer.getAddress()+"',"+customer.getStatus()+")";
        try {
            // tao lenh truyen du lieu
            Statement state = conn.createStatement();
            // chay va nhan ket qua
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    @Override
    public int add(Customer customer){
        int n = 0;
        String sql = "INSERT INTO [Customer]\n" +
"           ([cid]\n" +
"           ,[cname]\n" +
"           ,[username]\n" +
"           ,[password]\n" +
"           ,[address]\n" +
"           ,[status])\n" +
"     VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            //set parameter
            //pre.setDataType(index?,value);
            //dataType là kieu giu lieu cua truong;
            //index of? start 1
            pre.setString(1, customer.getCid());
            pre.setString(2, customer.getCname());
            pre.setString(3, customer.getUsername());
            pre.setString(4, customer.getPassword());
            pre.setString(5, customer.getAddress());
            pre.setInt(6, customer.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    @Override
    public int update(Customer customer){
        int n = 0;
        String sql = "UPDATE [dbo].[Customer]\n" +
"   SET [cname] = ?\n" +
"      ,[username] = ?\n" +
"      ,[password] = ?\n" +
"      ,[address] = ?\n" +
"      ,[status] = ?\n" +
" WHERE [cid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, customer.getCname());
            pre.setString(2, customer.getUsername());
            pre.setString(3, customer.getPassword());
            pre.setString(4, customer.getAddress());
            pre.setInt(5, customer.getStatus());
            pre.setString(6, customer.getCid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    public void displayAll(){
        String sql = "select * from Customer";
//        Statement state = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
//                ResultSet.CONCUR_READ_ONLY);
        try {
            //default: con tro chi di xuong
            //read: chi doc khong sua
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while(rs.next()){
                /* dataType varName = rs.getDataType("fieldName|index"); */
                String cid = rs.getString("cid");
                String name = rs.getString("cname");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String address = rs.getString("address");
                int status = rs.getInt("status");
                Customer cus = new Customer(cid, name, username, password, address, status);
                System.out.println(cus.toString());
            }
            // scroll sens: con tro 2 chieu
            //sensitive: thread safe
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public Vector<Customer> getAll(String sql){
        Vector<Customer> vector = new Vector<Customer>();
        ResultSet rs = this.getData(sql);
        try {
            while(rs.next()){
                String cid = rs.getString("cid");
                String name = rs.getString("cname");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String address = rs.getString("address");
                int status = rs.getInt("status");
                Customer cus = new Customer(cid, name, username, password, address, status);
                vector.add(cus);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    public boolean login(String user, String pass){
        String sql = "select * from Customer where username=? and "
               + " password = ? and status=1";
        PreparedStatement pre;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, user); pre.setString(2, pass);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    @Override
    public int remove(String id){
        int n = 0;
        String sql = "delete from customer where cid ='" + id +"'";
        try {
            //note: Customer --1 ----n--> Bill -> Khong xoa duoc
            //neu cid ton tai tren Bill
            ResultSet rs = this.getData("Select * from Bill where cid='" +id +"'");
            if(rs.next()){
                n=-1;
            }
            else {
                Statement state = conn.createStatement();
                n = state.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
         
    @Override
    public Vector<Customer> getAll() {
        return getAll("SELECT * from Customer");
    }

    @Override
    public Vector<Customer> getAll(PreparedStatement statement) {
        Vector<Customer> vector = new Vector<>();
        ResultSet rs = this.getData(statement);
        try {
            while(rs.next()){
                String cid = rs.getString("cid");
                String name = rs.getString("cname");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String address = rs.getString("address");
                int status = rs.getInt("status");
                Customer cus = new Customer(cid, name, username, password, address, status);
                vector.add(cus);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    
}
