package Database;

import Models.Order;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {

    public static void insertOrder(Order o) {
        String sql = "INSERT INTO Orderlist (proId, proName,cateId, quantity, proPrice, note) values (?,?,?,?,?,?)";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement pStm = cn.prepareStatement(sql);) {
            pStm.setString(1, o.getProId());
            pStm.setString(2, o.getProName());
            pStm.setInt(3, o.getCateId());
            pStm.setInt(4, o.getQuantity());
            pStm.setDouble(5, o.getProPrice());
            pStm.setString(6, o.getNote());

            pStm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("inserorder list" + o.toString());
    }

    public ArrayList<Order> showOrderDB() {
        ArrayList<Order> showList = new ArrayList<>();
        String sql = "SELECT proId, proName, cateId, quantity, proPrice, note FROM Orderlist ";

        try (Connection cn = new ConnectDB().GetConnectDB(); Statement stm = cn.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                Order order = new Order();
                // Assuming Order class has appropriate setter methods
                order.setProId(rs.getString("proId"));
                order.setProName(rs.getString("proName"));
                order.setCateId(rs.getInt("cateId"));
                order.setQuantity(rs.getInt("quantity"));
                order.setProPrice(rs.getInt("proPrice"));
                order.setNote(rs.getString("note"));

                boolean checkProId = false;
                for (Order o : showList) {
                    if (order.getNote().isEmpty()) {
                        if (o.getProId().equals(order.getProId()) && o.getNote().isEmpty()) {
                            o.setQuantity(o.getQuantity() + order.getQuantity());
                            checkProId = true;
                            break;
                        }
                    }
                }
                if (!checkProId) {
                    showList.add(order);
                }

            }
        } catch (SQLException e) {
            // Log or handle the exception as appropriate
            e.printStackTrace();
        }

        return showList;
    }

    public void archiveAndDeleteOrders(Connection cn) throws SQLException {
        String selectSql = "SELECT * FROM Orderlist";
        String insertSql = "INSERT INTO OrderArchive (billId, proId, proName, cateId, quantity, proPrice, note, archiveDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String deleteSql = "DELETE FROM Orderlist";
        try {
            cn.setAutoCommit(false); // Begin transaction

            try (Statement stmt = cn.createStatement(); ResultSet rs = stmt.executeQuery(selectSql); PreparedStatement insertStmt = cn.prepareStatement(insertSql)) {

                while (rs.next()) {
                    String billId = rs.getString("billId");
                    if (billId == null) {
                        // Skip records with null billId
                        continue;
                    }
                    // Thêm bản ghi vào batch
                    insertStmt.setString(1, billId);
                    insertStmt.setString(2, rs.getString("proId"));
                    insertStmt.setString(3, rs.getString("proName"));
                    insertStmt.setInt(4, rs.getInt("cateId"));
                    insertStmt.setInt(5, rs.getInt("quantity"));
                    insertStmt.setDouble(6, rs.getDouble("proPrice"));
                    insertStmt.setString(7, rs.getString("note"));
                    insertStmt.setDate(8, new java.sql.Date(System.currentTimeMillis()));

                    insertStmt.addBatch();
                }

                // Thực hiện thêm tất cả bản ghi vào OrderArchive
                insertStmt.executeBatch();

                // Xóa tất cả các bản ghi trong Orderlist
                stmt.executeUpdate(deleteSql);

                cn.commit(); // Commit transaction

            } catch (SQLException e) {
                cn.rollback(); // Rollback transaction on error
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            cn.setAutoCommit(true); // Reset auto-commit
        }
    }

    //SUM Orderlist
    public ArrayList<Order> getbillTotalDB() {
        ArrayList<Order> billTotalList = new ArrayList();
        String sql = "SELECT SUM(proPrice*quantity) as billSubTotal FROM Orderlist";
        try (Connection cn = new ConnectDB().GetConnectDB(); Statement stm = cn.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

            if (rs.next()) {
                Order o = new Order();
                o.setBillSubTotal(rs.getDouble("billSubTotal"));
                billTotalList.add(o);
            }

        } catch (SQLException e) {
            // Log or handle the exception as appropriate
            e.printStackTrace();
        }
        return billTotalList;
    }

    public void DeleteProIdDB(String id, int quantity) {
        Connection cn = null;
        PreparedStatement pStm = null;
        try {
            cn = new ConnectDB().GetConnectDB();
            cn.setAutoCommit(false); // Bắt đầu giao dịch

            String updateSql = "UPDATE Orderlist SET quantity = quantity - ? WHERE proId = ? AND quantity > 0";
            pStm = cn.prepareStatement(updateSql);
            pStm.setInt(1, quantity);
            pStm.setString(2, id);
            int rowsAffected = pStm.executeUpdate();

            if (rowsAffected > 0) {
                String checkSql = "DELETE FROM Orderlist WHERE proId = ? AND quantity = 0";
                pStm = cn.prepareStatement(checkSql);
                pStm.setString(1, id);
                pStm.executeUpdate();

                // Cam kết giao dịch
                cn.commit();
            } else {
                // Nếu không có hàng nào bị ảnh hưởng, rollback giao dịch
                cn.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void CancelOrder() {
        try {
            Connection cn = new ConnectDB().GetConnectDB();
            String sql = "DELETE FROM Orderlist where status='PENDING'";
            PreparedStatement pStm = cn.prepareStatement(sql);
            pStm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // SAVED BIll
    public ArrayList<Order> showBillDB(int billId) {
        ArrayList<Order> showList = new ArrayList<>();
        String sql = "SELECT proId, proName, cateId, quantity, proPrice, note FROM OrderArchive where billId=?";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement pStm = cn.prepareStatement(sql)) {

            pStm.setInt(1, billId);
            try (ResultSet rs = pStm.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setProId(rs.getString("proId"));
                    order.setProName(rs.getString("proName"));
                    order.setCateId(rs.getInt("cateId"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setProPrice(rs.getDouble("proPrice"));
                    order.setNote(rs.getString("note"));

                    boolean checkProId = false;
                    for (Order o : showList) {
                        if (order.getNote().isEmpty()) {
                            if (o.getProId().equals(order.getProId()) && o.getNote().isEmpty()) {
                                o.setQuantity(o.getQuantity() + order.getQuantity());
                                checkProId = true;
                                break;
                            }
                        }
                    }
                    if (!checkProId) {
                        showList.add(order);
                        System.out.println("show List" + order.toString());

                    }

                }
            }
        } catch (SQLException e) {
            // Log or handle the exception as appropriate
            e.printStackTrace();
        }

        return showList;
    }

    public void archiveAndDeleteBillOrder(Connection cn, int billId) throws SQLException {
        ArrayList<Order> list=new ArrayList();
        String selectSql = "SELECT * FROM Orderlist WHERE billId IS NULL";
        String insertSql = "INSERT INTO OrderArchive (billId, proId, proName, cateId, quantity, proPrice, note, archiveDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String deleteSql = "DELETE FROM Orderlist WHERE billId IS NULL";

        // Bắt đầu giao dịch
        cn.setAutoCommit(false);

        try (PreparedStatement selectStmt = cn.prepareStatement(selectSql); PreparedStatement insertStmt = cn.prepareStatement(insertSql); PreparedStatement deleteStmt = cn.prepareStatement(deleteSql); ResultSet rs = selectStmt.executeQuery()) {

            while (rs.next()) {
                // Thêm sản phẩm vào OrderArchive
                insertStmt.setInt(1, billId);  // Đặt billId vào câu lệnh INSERT
                insertStmt.setString(2, rs.getString("proId"));
                insertStmt.setString(3, rs.getString("proName"));
                insertStmt.setInt(4, rs.getInt("cateId"));
                insertStmt.setInt(5, rs.getInt("quantity"));
                insertStmt.setDouble(6, rs.getDouble("proPrice"));
                insertStmt.setString(7, rs.getString("note"));
                insertStmt.setDate(8, new java.sql.Date(System.currentTimeMillis()));

                insertStmt.addBatch();
            }

            // Thực hiện batch thêm vào OrderArchive
            insertStmt.executeBatch();

            // Xóa tất cả các bản ghi trong Orderlist không có billId
            deleteStmt.executeUpdate();

            // Commit giao dịch
            cn.commit();

        } catch (SQLException e) {
            // Rollback giao dịch nếu có lỗi
            cn.rollback();
            throw e;
        } finally {
            // Đặt lại chế độ auto-commit
            cn.setAutoCommit(true);
        }
    }

    public ArrayList<Order> getSumTotalDB(int resultedBillId) {
        ArrayList<Order> billTotalList = new ArrayList();
        String sql = "SELECT SUM(proPrice * quantity) AS billSubTotal FROM OrderArchive WHERE billId = ?";

        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, resultedBillId); // Thiết lập tham số cho PreparedStatement

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order o = new Order();
                    o.setBillSubTotal(rs.getDouble("billSubTotal"));
                    billTotalList.add(o);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billTotalList;
    }

    public void deletedProArchive(int billId,String proId, int quantity) {
        Connection cn = null;
        PreparedStatement pStm = null;
        try {
            cn = new ConnectDB().GetConnectDB();
            cn.setAutoCommit(false);
            // Giảm số lượng sản phẩm trong bảng OrderArchive
        String updateSql = "UPDATE OrderArchive SET quantity = quantity - ? "
                + "WHERE proId = ? AND billId = ? AND quantity > 0";
        pStm = cn.prepareStatement(updateSql);
        pStm.setInt(1, quantity);
        pStm.setString(2, proId);
        pStm.setInt(3, billId);
        int rowsAffected = pStm.executeUpdate();

        if (rowsAffected > 0) {
            // Xóa bản ghi nếu số lượng còn lại là 0
            String checkSql = "DELETE FROM OrderArchive"
                    + " WHERE proId = ? AND billId = ? AND quantity = 0";
            pStm = cn.prepareStatement(checkSql);
            pStm.setString(1, proId);
            pStm.setInt(2, billId);
            pStm.executeUpdate();

            cn.commit();
            } else {
                cn.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
