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
                    if (o.getProId().equals(order.getProId())) {
                        o.setQuantity(o.getQuantity() + order.getQuantity());
                        checkProId = true;
                        break;
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
    
    try (Statement stmt = cn.createStatement();
         ResultSet rs = stmt.executeQuery(selectSql);
         PreparedStatement insertStmt = cn.prepareStatement(insertSql)) {

        while (rs.next()) {
            insertStmt.setInt(1, rs.getInt("billId"));
            insertStmt.setString(2, rs.getString("proId"));
            insertStmt.setString(3, rs.getString("proName"));
            insertStmt.setInt(4, rs.getInt("cateId"));
            insertStmt.setInt(5, rs.getInt("quantity"));
            insertStmt.setDouble(6, rs.getDouble("proPrice"));
            insertStmt.setString(7, rs.getString("note"));
            insertStmt.setDate(8, new java.sql.Date(System.currentTimeMillis()));
            insertStmt.addBatch();
        }
        insertStmt.executeBatch();
        stmt.executeUpdate(deleteSql);
    }
}
public void markOrdersAsProcessed(Connection cn) throws SQLException {
    String sql = "UPDATE Orderlist SET status = 'PROCESSED' WHERE status IS NULL";
    try (Statement stmt = cn.createStatement()) {
        stmt.executeUpdate(sql);
    }
}
    
    public ArrayList<Order> getbillTotalDB() {
        ArrayList<Order> billTotal = new ArrayList<>();
        String sql = "SELECT SUM(proPrice*quantity) as billTotal FROM Orderlist";
        try (Connection cn = new ConnectDB().GetConnectDB(); Statement stm = cn.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

            if (rs.next()) {
                Order c = new Order();
                c.setBillTotal(rs.getDouble("billTotal"));

                billTotal.add(c);
                System.out.println("thong tin bill sub" + c.getBillTotal());

                // Add the order to the list
                billTotal.add(c);
            }

        } catch (SQLException e) {
            // Log or handle the exception as appropriate
            e.printStackTrace();
        }

        return billTotal;
    }

    public void DeleteProIdDB(String id) {
        try {
            Connection cn = new ConnectDB().GetConnectDB();
            String sql = "DELETE FROM Orderlist WHERE proId = ?";
            PreparedStatement pStm = cn.prepareStatement(sql);
            pStm.setString(1, id);
            pStm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
}
