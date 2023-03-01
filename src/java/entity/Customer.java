package entity;
public class Customer {

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public boolean isAdmin(){
        return this.isAdmin == 1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    private String cid; 
    private String cname; 
    private String username;
    private String password;
    private String address;
    private int status;
    private String phone;
    private int isAdmin = 0;

    public Customer(String cid, String cname, String username, String password, String address, int status, String phone) {
        this.cid = cid;
        this.cname = cname;
        this.username = username;
        this.password = password;
        this.address = address;
        this.status = status;
        this.phone = phone;
    }
    
    public Customer(String cid, String cname, String username, String password, String address, int status, String phone, int isAdmin) {
        this(cid, cname, username, password, address, status, phone);
        this.isAdmin = isAdmin;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Customer{" + "cid=" + cid + ", cname=" + cname + ", username=" + username + ", password=" + password + ", address=" + address + ", status=" + status + '}';
    }
    
}
