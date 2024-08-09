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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.sql.ResultSet;
public class CustomerDAO {

    private static final double SILVER_MIN = 10000000;
    private static final double SILVER_MAX = 29999999;
    private static final int SILVER_DISCOUNT = 15;
    private static final int SILVER_MAX_DISCOUNT = 200000;

    private static final double GOLD_MIN = 30000000;
    private static final double GOLD_MAX = 49999999;
    private static final int GOLD_DISCOUNT = 20;
    private static final int GOLD_MAX_DISCOUNT = 300000;

    private static final double PLATINUM_MIN = 50000000;
    private static final double PLATINUM_MAX = Double.MAX_VALUE; // Không giới hạn trên
    private static final int PLATINUM_DISCOUNT = 25;
    private static final int PLATINUM_MAX_DISCOUNT = 400000;

    // Phương thức lấy danh sách khách hàng chưa bị xóa
//    public static List<Customer> getList() {
//        List<Customer> ds = new ArrayList<>();
//        String sql = "SELECT * FROM tbCustomer WHERE deleted = 0";
//
//        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement st = cn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
//
//            while (rs.next()) {
//                ds.add(extractCustomerFromResultSet(rs));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return ds;
//    }
//    // Phương thức lấy danh sách tất cả khách hàng
//    public static List<Customer> getListAll() {
//        List<Customer> ds = new ArrayList<>();
//        String sql = "SELECT * FROM tbCustomer";
//
//        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement st = cn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
//
//            while (rs.next()) {
//                ds.add(extractCustomerFromResultSet(rs));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return ds;
//    }
    // Phương thức thêm khách hàng mới
    public static int insert(Customer new_cus) {
        String sql = "SELECT COUNT(cus_id) FROM tbCustomer";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement st = cn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            if (rs.next()) {
                int current_number_of_tbCustomer = rs.getInt(1);

                sql = "INSERT INTO tbCustomer (cus_id, name, phone, email, discount, deleted) VALUES (?, ?, ?, ?, ?, ?)";
                int result = 0;
                do {
                    String newid = createid("CUS", String.valueOf(++current_number_of_tbCustomer), 10);
                    new_cus.setCus_id(newid);

                    try (PreparedStatement st2 = cn.prepareStatement(sql)) {
                        st2.setString(1, new_cus.getCus_id());
                        st2.setString(2, new_cus.getName());
                        st2.setString(3, new_cus.getPhone());
                        st2.setString(4, new_cus.getEmail());
                        st2.setInt(5, 0); // Mặc định không giảm giá khi thêm mới
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

    // Phương thức cập nhật thông tin khách hàng
    public static int update(Customer c, String newname, String newphone, String newemail, int newdiscount, int newdeleted) {
        String sql = "UPDATE tbCustomer SET name = ?, phone = ?, email = ?, discount = ?, deleted = ? WHERE cus_id = ?";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement st = cn.prepareStatement(sql)) {
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

    // Phương thức xóa khách hàng
    public static int delete(String cus_id) {
        String sql = "DELETE FROM tbCustomer WHERE cus_id = ?";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement st = cn.prepareStatement(sql)) {

            st.setString(1, cus_id);

            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    // Phương thức tìm kiếm khách hàng theo số điện thoại
    public static List<Customer> searchByPhone(String phone) {
        List<Customer> ds = new ArrayList<>();
        String sql = "SELECT * FROM tbCustomer WHERE phone = ? AND deleted = 0";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement st = cn.prepareStatement(sql)) {

            st.setString(1, phone);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    // Assuming Customer class has a constructor or setters for these fields
                    Customer customer = new Customer();
                    customer.setCus_id(rs.getString("cus_id"));
                    customer.setName(rs.getString("name"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setEmail(rs.getString("email"));
                    customer.setDiscount(rs.getInt("discount"));
                    customer.setDeleted(rs.getInt("deleted"));
                    customer.setMembershipType(rs.getString("membershipType"));
                    // Set other fields as necessary

                    ds.add(customer);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ds;
    }

    // Phương thức tính toán mức giảm giá dựa trên tổng chi tiêu
    private static int calculateDiscount(double totalSpending) {
        int discountPercentage;
        int maxDiscount;

        if (totalSpending >= SILVER_MIN && totalSpending < GOLD_MIN) {
            discountPercentage = SILVER_DISCOUNT;
            maxDiscount = SILVER_MAX_DISCOUNT;
        } else if (totalSpending >= GOLD_MIN && totalSpending < PLATINUM_MIN) {
            discountPercentage = GOLD_DISCOUNT;
            maxDiscount = GOLD_MAX_DISCOUNT;
        } else if (totalSpending >= PLATINUM_MIN) {
            discountPercentage = PLATINUM_DISCOUNT;
            maxDiscount = PLATINUM_MAX_DISCOUNT;
        } else {
            return 0; // Không giảm giá nếu doanh thu dưới ngưỡng tối thiểu
        }

        return Math.min((int) (totalSpending * discountPercentage / 100.0), maxDiscount);
    }

    // Phương thức cập nhật thẻ thành viên và giảm giá dựa trên tổng chi tiêu (không phân theo tháng)
    public static void updateCustomerMembership(String cus_id) {
        double totalSpending = getTotalSpending(cus_id); // Lấy tổng chi tiêu không phân theo thời gian
        int discount = calculateDiscount(totalSpending);

        String sql = "UPDATE tbCustomer SET discount = ? WHERE cus_id = ?";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement st = cn.prepareStatement(sql)) {
            st.setInt(1, discount);
            st.setString(2, cus_id);
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Phương thức lấy tổng chi tiêu của khách hàng (không phân theo tháng hay năm)
    private static double getTotalSpending(String cus_id) {
        double totalSpending = 0;
        String sql = "SELECT SUM(billSubTotal) FROM Bill WHERE cus_id = ?";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, cus_id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    totalSpending = rs.getDouble(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return totalSpending;
    }

    // Phương thức tạo ID mới
    private static String createid(String startid, String number_want_toset, int idsize) {
        StringBuilder str_result = new StringBuilder();
        int blank = idsize - (startid.length() + number_want_toset.length());
        str_result.append(startid);
        for (int i = 0; i < blank; i++) {
            str_result.append("0");
        }
        str_result.append(number_want_toset);
        return str_result.toString();
    }

//    // Trích xuất thông tin khách hàng từ ResultSet
//    private static Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
//        Customer customer = new Customer();
//        customer.setCus_id(rs.getString("cus_id"));
//        customer.setName(rs.getString("name"));
//        customer.setPhone(rs.getString("phone"));
//        customer.setEmail(rs.getString("email"));
//        customer.setDiscount(rs.getInt("discount"));
//        customer.setDeleted(rs.getInt("deleted"));
//        customer.setTotalSpending(rs.getDouble("totalSpending")); 
//        customer.setMembershipType(rs.getString("membershipType"));
//        return customer;
//    }


    public static ArrayList<Customer> updateCustomerFromBill() {
        ArrayList<Customer> cusList=new ArrayList<>();
        // SQL để truy vấn thông tin hóa đơn với trạng thái "paid"
        String selectBillSQL = "SELECT cus_id, billId, billSubTotal, billDate, billStatus FROM Bill WHERE billStatus = 'PAID'";

        // SQL để cập nhật thông tin khách hàng
        String updateCustomerSQL = "UPDATE tbCustomer "
                + "SET billId = ?, billSubTotal = ?, billDate = ?, billStatus = ?, "
                + "totalSpending = totalSpending+ ?, membershipType = ?, discount = ? "
                + "WHERE cus_id = ?";

        try (Connection cn = new ConnectDB().GetConnectDB();
             PreparedStatement selectBillStmt = cn.prepareStatement(selectBillSQL);
             PreparedStatement updateCustomerStmt = cn.prepareStatement(updateCustomerSQL)) {

            // Thực hiện truy vấn thông tin hóa đơn
            ResultSet rs = selectBillStmt.executeQuery();

            // Xử lý từng hóa đơn một
            while (rs.next()) {
                String cusId = rs.getString("cus_id");
                int billId = rs.getInt("billId");
                double billSubTotal = rs.getDouble("billSubTotal");
                Date billDate = rs.getDate("billDate");
                String billStatus = rs.getString("billStatus");

                // Lấy thông tin khách hàng hiện tại
                Customer customer = getCustomerById(cusId);
                if (customer != null) {
                    // Cập nhật thông tin hóa đơn cho khách hàng
                    customer.setBillId(billId);
                    customer.setBillSubTotal(billSubTotal);
                    customer.setBillDate(billDate);
                    customer.setBillStatus(billStatus);

                    // Tính tổng chi tiêu mới
                    double newTotalSpending = customer.getTotalSpending() + billSubTotal;

                    // Tính loại thành viên mới
                    String membershipType = calculateMembershipType(newTotalSpending);

                    // Tính mức giảm giá dựa trên loại thành viên
                    int discount = calculateDiscount(membershipType);

                    // Cập nhật khách hàng
                    customer.setTotalSpending(newTotalSpending);
                    customer.setMembershipType(membershipType);
                    customer.setDiscount(discount);

                    // Cập nhật cơ sở dữ liệu cho từng khách hàng
                    updateCustomerStmt.setInt(1, customer.getBillId());
                    updateCustomerStmt.setDouble(2, customer.getBillSubTotal());
                    updateCustomerStmt.setDate(3, new java.sql.Date(customer.getBillDate().getTime())); // Chuyển đổi
                    updateCustomerStmt.setString(4, customer.getBillStatus());
                    updateCustomerStmt.setDouble(5, customer.getTotalSpending());
                    updateCustomerStmt.setString(6, customer.getMembershipType());
                    updateCustomerStmt.setInt(7, customer.getDiscount());
                    updateCustomerStmt.setString(8, customer.getCus_id());

                    // Thực hiện cập nhật từng khách hàng một
                    int rowsAffected = updateCustomerStmt.executeUpdate();
                    System.out.println("Updated customer with ID " + customer.getCus_id() + ". Rows affected: " + rowsAffected);
               cusList.add(customer);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cusList;
    }

    // Phương thức để tính toán loại thành viên dựa trên tổng chi tiêu
    private static String calculateMembershipType(double totalSpending) {
        if (totalSpending > 50000) {
            return "Platinum";
        } else if (totalSpending > 30000) {
            return "Gold";
        } else if (totalSpending > 10000) {
            return "Silver";
        } else {
            return "Member";
        }
    }

    // Phương thức để tính toán mức giảm giá dựa trên loại thành viên
    private static int calculateDiscount(String membershipType) {
        switch (membershipType) {
            case "Platinum":
                return 15;
            case "Gold":
                return 10;
            case "Silver":
                return 5;
            default:
                return 0;
        }
    }

    // Phương thức để lấy thông tin khách hàng từ cơ sở dữ liệu
    private static Customer getCustomerById(String cus_id) {
        String sql = "SELECT * FROM tbCustomer WHERE cus_id = ?";
        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement stmt = cn.prepareStatement(sql)) {

            stmt.setString(1, cus_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCus_id(rs.getString("cus_id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setTotalSpending(rs.getDouble("totalSpending"));
                customer.setMembershipType(rs.getString("membershipType"));
                customer.setDiscount(rs.getInt("discount"));
                return customer;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
  
    // Phương thức lấy tất cả khách hàng cùng với hóa đơn
 public static ArrayList<Customer> getAllCustomersWithBills() {
        ArrayList<Customer> customers = new ArrayList<>();
         String sql = "SELECT c.cus_id, c.name, c.phone, c.email, "
               + "COALESCE(SUM(b.billSubTotal), 0) AS totalSpending, "
               + "c.membershipType, c.discount, "
               + "b.billId, b.billSubTotal, b.billDate, b.billStatus "
               + "FROM tbCustomer c "
               + "LEFT JOIN Bill b ON c.cus_id = b.cus_id AND b.billStatus = 'PAID' "
               + "GROUP BY c.cus_id, c.name, c.phone, c.email, c.membershipType, c.discount, b.billId, b.billSubTotal, b.billDate, b.billStatus "
               + "ORDER BY c.cus_id";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement stmt = cn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String cusId = rs.getString("cus_id");
                Customer customer = findCustomerById(customers, cusId);

                if (customer == null) {
                    customer = new Customer();
                    customer.setCus_id(cusId);
                    customer.setName(rs.getString("name"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setEmail(rs.getString("email"));
                    customer.setTotalSpending(rs.getDouble("totalSpending"));
                    // Thay đổi thông tin hóa đơn nếu có
                    Integer billId = rs.getObject("billId") != null ? rs.getInt("billId") : null;
                    Double billSubTotal = rs.getObject("billSubTotal") != null ? rs.getDouble("billSubTotal") : null;
                    java.sql.Date billDate = rs.getObject("billDate") != null ? rs.getDate("billDate") : null;
                    String billStatus = rs.getString("billStatus");

                    if (billId != null) {
                        customer.setBillId(billId);
                        customer.setBillSubTotal(billSubTotal);
                        customer.setBillDate(billDate);
                        customer.setBillStatus(billStatus);
                    }

                    // Tính toán loại thành viên và mức giảm giá
                    String membershipType = calculateMembershipType(customer.getTotalSpending());
                    int discount = calculateDiscount(membershipType);

                    // Cập nhật thông tin thành viên và giảm giá
                    customer.setMembershipType(membershipType);
                    customer.setDiscount(discount);

                    customers.add(customer);
                } else {
                    // Cập nhật thông tin hóa đơn nếu có
                    Integer billId = rs.getObject("billId") != null ? rs.getInt("billId") : null;
                    Double billSubTotal = rs.getObject("billSubTotal") != null ? rs.getDouble("billSubTotal") : null;
                    java.sql.Date billDate = rs.getObject("billDate") != null ? rs.getDate("billDate") : null;
                    String billStatus = rs.getString("billStatus");

                    if (billId != null) {
                        customer.setBillId(billId);
                        customer.setBillSubTotal(billSubTotal);
                        customer.setBillDate(billDate);
                        customer.setBillStatus(billStatus);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customers;
    }

    private static Customer findCustomerById(ArrayList<Customer> customers, String cusId) {
        for (Customer c : customers) {
            if (c.getCus_id().equals(cusId)) {
                return c;
            }
        }
        return null;
    }



   
}





