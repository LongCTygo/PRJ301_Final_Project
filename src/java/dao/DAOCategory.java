
package dao;
import connect.DAOEntity;
import entity.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DAOCategory extends DAOEntity<Category>{

    @Override
    public int add(Category category){
        int n = 0;
        String sql = "INSERT INTO [dbo].[Category]\n" +
"           ([cateName]\n" +
"           ,[status])\n" +
"     VALUES (?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, category.getCateName());
            pre.setInt(2, category.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    @Override
    public int update(Category category){
        int n = 0;
        String sql ="UPDATE [dbo].[Category]\n" +
"   SET [cateName] = ?\n" +
"      ,[status] = ?\n" +
" WHERE [cateId] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, category.getCateName());
            pre.setInt(2, category.getStatus());
            pre.setInt(3, category.getCateId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    public void displayAll(){
        String sql = "select * from Category";
        try {
            //default: con tro chi di xuong
            //read: chi doc khong sua
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while(rs.next()){
                /* dataType varName = rs.getDataType("fieldName|index"); */
                String name = rs.getString("cateName");
                int status = rs.getInt("status");
                int cateid = rs.getInt("cateId");
                Category cat = new Category(cateid, name, status);
                System.out.println(cat.toString());
            }
            // scroll sens: con tro 2 chieu
            //sensitive: thread safe
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public Vector<Category> getAll(String sql){
        Vector<Category> vector = new Vector<Category>();
        ResultSet rs = this.getData(sql);
        try {
            while(rs.next()){
                String name = rs.getString("cateName");
                int status = rs.getInt("status");
                int cateid = rs.getInt("cateId");
                Category cat = new Category(cateid, name, status);
                vector.add(cat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    @Override
    public int remove(String id){
        int n = 0;
        String sql = "delete from Category where cateId ='" + id +"'";
        try {
            //note: Customer --1 ----n--> Bill -> Khong xoa duoc
            //neu cid ton tai tren Bill
            ResultSet rs = this.getData("Select * from Product where cateID='" +id+"'");
            if(rs.next()){
                n=-1;
            }
            else {
                Statement state = conn.createStatement();
                n = state.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }


    @Override
    public Vector<Category> getAll() {
        return getAll("SELECT * from Category");
    }

    @Override
    public Vector<Category> getAll(PreparedStatement statement) {
        Vector<Category> vector = new Vector<>();
        ResultSet rs = this.getData(statement);
        try {
            while(rs.next()){
                String name = rs.getString("cateName");
                int status = rs.getInt("status");
                int cateid = rs.getInt("cateId");
                Category cat = new Category(cateid, name, status);
                vector.add(cat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
}
