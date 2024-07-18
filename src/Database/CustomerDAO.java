/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Models.Customer;
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerDAO {
    
    
   public static List<Customer> getList() {
        List<Customer> ds = new ArrayList<>();
        String sql = "SELECT * FROM tbCustomer WHERE deleted = 0";

        try (Connection cn = new ConnectDB().GetConnectDB();
             PreparedStatement st = cn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Customer newitem = new Customer();
                newitem.setCus_id(rs.getString(1));
                newitem.setName(rs.getString(2));
                newitem.setPhone(rs.getString(3));
                newitem.setEmail(rs.getString(4));
                newitem.setDiscount(rs.getInt(5));
                newitem.setDeleted(rs.getInt(6));
                ds.add(newitem);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ds;
    }

    public static List<Customer> getListAll() {
        List<Customer> ds = new ArrayList<>();
        String sql = "SELECT * FROM tbCustomer";

        try (Connection cn = new ConnectDB().GetConnectDB();
             PreparedStatement st = cn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Customer newitem = new Customer();
                newitem.setCus_id(rs.getString(1));
                newitem.setName(rs.getString(2));
                newitem.setPhone(rs.getString(3));
                newitem.setEmail(rs.getString(4));
                newitem.setDiscount(rs.getInt(5));
                newitem.setDeleted(rs.getInt(6));
                ds.add(newitem);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ds;
    }

    public static int insert(Customer new_cus) {
        String sql = "SELECT COUNT(cus_id) FROM tbCustomer"; // tạo id mới cho customer cần thêm vào database
        try (Connection cn = new ConnectDB().GetConnectDB();
             PreparedStatement st = cn.prepareStatement(sql);
             ResultSet rs = st.executeQuery();) {

            if (rs.next()) {
                int current_number_oftbCustomer = rs.getInt(1);

                sql = "INSERT INTO tbCustomer VALUES (?, ?, ?, ?, ?, ?)";
                int result = 0;
                do {
                    String newid = createid("CUS", String.valueOf(++current_number_oftbCustomer), 10);
                    new_cus.setCus_id(newid);

                    try (PreparedStatement st2 = cn.prepareStatement(sql);) {
                        st2.setString(1, new_cus.getCus_id());
                        st2.setString(2, new_cus.getName());
                        st2.setString(3, new_cus.getPhone());
                        st2.setString(4, new_cus.getEmail());
                        st2.setInt(5, new_cus.getDiscount());
                        st2.setInt(6, new_cus.getDeleted());

                        result = st2.executeUpdate();
                    }
                } while (result == 0);
                return result;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static int update(Customer c, String newname, String newphone, String newemail, int newdiscount, int newdeleted) {
        String sql = "UPDATE tbCustomer SET name = ?, phone = ?, email = ?, discount = ?, deleted = ? WHERE cus_id = ?";

        try (Connection cn = new ConnectDB().GetConnectDB();
             PreparedStatement st = cn.prepareStatement(sql);) {
            st.setString(1, newname);
            st.setString(2, newphone);
            st.setString(3, newemail);
            st.setInt(4, newdiscount);
            st.setInt(5, newdeleted);
            st.setString(6, c.getCus_id());

            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static int delete(String cus_id) {
        String sql = "DELETE tbCustomer WHERE cus_id = ?";

        try (Connection cn = new ConnectDB().GetConnectDB();
             PreparedStatement st = cn.prepareStatement(sql);) {

            st.setString(1, cus_id);

            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
    public static List<Customer> searchByPhone(String phone) {
    List<Customer> ds = new ArrayList<>();
    String sql = "SELECT * FROM tbCustomer WHERE phone LIKE ? AND deleted = 0";

    try (Connection cn = new ConnectDB().GetConnectDB();
         PreparedStatement st = cn.prepareStatement(sql)) {

        st.setString(1, "%" + phone + "%"); // Tìm kiếm các kết quả có số điện thoại chứa chuỗi phone

        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Customer newitem = new Customer();
                newitem.setCus_id(rs.getString(1));
                newitem.setName(rs.getString(2));
                newitem.setPhone(rs.getString(3));
                newitem.setEmail(rs.getString(4));
                newitem.setDiscount(rs.getInt(5));
                newitem.setDeleted(rs.getInt(6));
                ds.add(newitem);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return ds;
}


    

    // WARNING: những DAO có dùng hàm createid thì các record đã tạo rồi sẽ không xoá. Tức là ko nên tạo method delete() để xoá record trong table
    private static String createid(String startid, String number_want_toset, int idsize) {
        String str_result = "";

        int blank = idsize - (startid.length() + number_want_toset.length());
        str_result += startid;
        for (int i = 0; i < blank; i++) {
            str_result += "0";
        }
        str_result += number_want_toset;

        return str_result;
    }
    
   public static List<Customer> getCustomerIdList() {
        List<Customer> ds = new ArrayList<>();
        String sql = "SELECT cus_id,name FROM tbCustomer ";

        try (Connection cn = new ConnectDB().GetConnectDB();
           Statement stm = cn.createStatement();
          ResultSet  rs = stm.executeQuery(sql);) {

            while (rs.next()) {
                Customer newitem = new Customer();
                newitem.setCus_id(rs.getString(1));
                newitem.setName(rs.getString(2));             
                ds.add(newitem);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ds;
    }
}





    
   