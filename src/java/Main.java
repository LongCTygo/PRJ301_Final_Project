
import connect.DBConnect;
import dao.DAOBill;
import dao.DAOProduct;
import display.BillDisplay;
import java.util.Vector;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ADMIN
 */
public class Main {
    public static void main(String[] args) {
        DAOBill dao = new DAOBill();
        Vector<BillDisplay> display = dao.getDisplay();
        System.out.println(display.size());
    }
}
