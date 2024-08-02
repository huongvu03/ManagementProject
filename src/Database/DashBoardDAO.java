
package Database;

import Models.Bill;
import Models.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.Alert;

public class DashBoardDAO {
    public Alert alert;
    static ConnectDB connect = new ConnectDB();
    static Connection cn = null;
    static Statement stm = null;// de chay cau lenh ko co bien
    static ResultSet rs = null; // hung ket qua tra ve
    static Scanner sc;
    static PreparedStatement pStm = null;// de chay cau lenh co bien

     public ArrayList<Bill> listDB(String from, String to) {
        ArrayList<Bill> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Bill");

        if (from != null && to != null) {
            sql.append(" WHERE billDate BETWEEN '").append(from).append("' AND '").append(to).append("'");
        } else if (from != null) {
            sql.append(" WHERE billDate >= '").append(from).append("'");
        } else if (to != null) {
            sql.append(" WHERE billDate <= '").append(to).append("'");
        }

        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql.toString());
            while (rs.next()) {
                Bill pro = new Bill();
                pro.setBillId(rs.getInt("billId"));
                pro.setTableNo(rs.getString("tableNo"));
                pro.setGuestNo(rs.getInt("guestNo"));
                pro.setCus_id(rs.getString("cus_id"));
                pro.setUserName(rs.getString("userName"));
                pro.setBillTotal(rs.getDouble("billTotal"));
                pro.setBillDiscount(rs.getDouble("billDiscount"));
                pro.setBillTax(rs.getDouble("billTax"));
                pro.setBillService(rs.getDouble("billService"));
                pro.setBillSubTotal(rs.getDouble("billSubTotal"));
                pro.setBillDate(rs.getDate("billDate"));
                pro.setBillStatus(rs.getString("billStatus"));
                list.add(pro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
