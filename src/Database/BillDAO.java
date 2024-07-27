package Database;

import Models.Bill;
import Models.Customer;
import Models.Order;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDAO {

    // lay billId
    public int insertBillAndGetId(Bill bill, Connection cn) throws SQLException {
        String sql = "INSERT INTO Bill (tableNo,guestNo, cus_id, userName, billTotal, billTax,"
                + " billService, billDiscount, billSubTotal, billDate, billStatus) "
                + "VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pStm = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pStm.setString(1, bill.getTableNo());
            pStm.setInt(2, bill.getGuestNo());
            pStm.setString(3, bill.getCus_id());
            pStm.setString(4, bill.getUserName());
            pStm.setDouble(5, bill.getBillTotal());
            pStm.setDouble(6, bill.getBillTax());
            pStm.setDouble(7, bill.getBillService());
            pStm.setDouble(8, bill.getBillDiscount());
            pStm.setDouble(9, bill.getBillSubTotal());
            pStm.setDate(10, new java.sql.Date(bill.getBillDate().getTime()));
            pStm.setString(11, bill.getBillStatus());

            pStm.executeUpdate();

            try (ResultSet rs = pStm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve generated bill ID.");
                }
            }
        }
    }

    // add san pham vao chung billId
    public void insertOrderDetails(int billId, List<Order> orderList, Connection cn) throws SQLException {
        String sql = "INSERT INTO Orderlist (billId, proId, proName, cateId, quantity, proPrice, note) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pStm = cn.prepareStatement(sql)) {
            for (Order order : orderList) {
                pStm.setInt(1, billId);
                pStm.setString(2, order.getProId());
                pStm.setString(3, order.getProName());
                pStm.setInt(4, order.getCateId());
                pStm.setInt(5, order.getQuantity());
                pStm.setDouble(6, order.getProPrice());
                pStm.setString(7, order.getNote());
                pStm.addBatch();
            }
            pStm.executeBatch();
        }
    }

    // show bang bill   
}
