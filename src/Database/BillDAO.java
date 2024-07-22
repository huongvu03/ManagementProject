package Database;

import Models.Bill;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import managementproject.data;

public class BillDAO {

    ArrayList<Bill> billOrderList = new ArrayList<>();
   private int billId = -1; // Biến để lưu trữ billId hiện tại

    public int insertOrder(ArrayList<Bill> billItems) {
        if (billItems.isEmpty()) {
            return -1; // Không có sản phẩm để thêm
        }

        try {
            String sql = "INSERT INTO Bill (tableNo, cus_id, userName, proId, proName, cateId, quantity, proPrice, status, proImage, billTotal, billTax, billService, billSubTotal, billDate, billStatus) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection cn = new ConnectDB().GetConnectDB();
            PreparedStatement pStm = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (Bill bill : billItems) {
                // Set parameters for each product item
                pStm.setString(1, bill.getTableNo());
                pStm.setString(2, bill.getCus_id());
                pStm.setString(3, bill.getUserName());
                pStm.setString(4, bill.getProId());
                pStm.setString(5, bill.getProName());
                pStm.setInt(6, bill.getCateId());
                pStm.setInt(7, bill.getQuantity());
                pStm.setDouble(8, bill.getProPrice());
                pStm.setString(9, bill.getStatus());
                pStm.setString(10, bill.getProImage());
                pStm.setDouble(11, bill.getBillTotal());
                pStm.setDouble(12, bill.getBillTax());
                pStm.setDouble(13, bill.getBillService());
                pStm.setDouble(14, bill.getBillSubTotal());
                java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
                pStm.setDate(15, sqlDate);
                pStm.setString(16, bill.getBillStatus());

                pStm.addBatch(); // Add to batch
            }

            // Execute batch insert
            int[] updateCounts = pStm.executeBatch();

            // Get generated keys for each inserted row
            ResultSet generatedKeys = pStm.getGeneratedKeys();
            int index = 0;
            while (generatedKeys.next()) {
                int generatedBillId = generatedKeys.getInt(1); // Get the generated billId
                billItems.get(index).setBillId(generatedBillId); // Set generated billId to each Bill object
                index++;
            }

            generatedKeys.close();
            pStm.close();
            cn.close();
        } catch (SQLException ex) {
        ex.printStackTrace();
        Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        return -1; // Return -1 to indicate failure
    }

    return billId; // Return the billId if necessary
}
  
    public ArrayList<Bill> getOrderDB(int billId) {
           ArrayList<Bill> billOrderInfo = new ArrayList<>();
        billOrderInfo.clear();
        String sql = "SELECT proId, proName,cateId, quantity, proPrice,status,proImage,billStatus FROM Bill where billId=? ";
        try (Connection cn = new ConnectDB().GetConnectDB(); PreparedStatement pstmt = cn.prepareStatement(sql)) {

            pstmt.setInt(1, billId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Bill c = new Bill();
                c.setBillId(billId);
                c.setProId(rs.getString("proId"));
                c.setProName(rs.getString("proName"));
                c.setCateId(rs.getInt("cateId"));
                c.setQuantity(rs.getInt("quantity"));
                c.setProPrice(rs.getDouble("proPrice"));
                c.setStatus(rs.getString("status"));
                c.setProImage(rs.getString("proImage"));
                c.setBillStatus(rs.getString("billStatus"));

                // Check if this proId already exists in billOrderInfo
                boolean found = false;
                for (Bill old : billOrderInfo) {
                    if (old.getProId().equals(c.getProId())) {
                        // Update quantity
                        old.setQuantity(old.getQuantity() + c.getQuantity());
                        found = true;
                        break;
                    }else{
                         old.setQuantity(c.getQuantity());
                    }
                }
                // If not found, add new Bill to billOrderInfo
                if (!found) {
                    billOrderInfo.add(c);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("order list: " + billOrderInfo);
        return billOrderInfo;
    }

    public ArrayList<Bill> billSubtotalList = new ArrayList<>();

    public ArrayList<Bill> getbillSubTotalDB(int billId) {
        System.out.println("ham getbill duoc goi");
        String sql = "SELECT SUM(billTotal) as billTotal, SUM(billTax) as billTax,"
                + "SUM(billService) as billService,  SUM(billSubTotal) as billSubTotal  FROM Bill WHERE billId =?";
        try {
            Connection cn = new ConnectDB().GetConnectDB();
            PreparedStatement pStm = cn.prepareStatement(sql);
            pStm.setInt(1, billId);
            ResultSet rs = pStm.executeQuery();
            billSubtotalList.clear();
            if (rs.next()) {
                Bill c = new Bill();
                c.setBillTotal(rs.getDouble("billTotal"));
                c.setBillTax(rs.getDouble("billTax"));
                c.setBillService(rs.getDouble("billService"));
                c.setBillSubTotal(rs.getDouble("billSubTotal"));
                billSubtotalList.add(c);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("billsub info" + billSubtotalList);
        return billSubtotalList;
    }

    public void UpdateDB(Bill bill) {
        try {
            Connection cn = new ConnectDB().GetConnectDB();
            String sql = "UPDATE Bill SET tableNo=?, billStatus=? ,billDate=?  WHERE billId = ?";
            PreparedStatement pStm = cn.prepareStatement(sql);
            pStm.setString(1, bill.getTableNo());

            pStm.setString(2, bill.getStatus());
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            pStm.setDate(3, sqlDate);
            pStm.setInt(4, bill.getBillId());
            pStm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DeleteDB(String id) {
        try {
            Connection cn = new ConnectDB().GetConnectDB();
            String sql = "DELETE FROM Bill WHERE proId = ?";
            PreparedStatement pStm = cn.prepareStatement(sql);
            pStm.setString(1, id);
            pStm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ClearDB(int billId) {
        try {
            Connection cn = new ConnectDB().GetConnectDB();
            String sql = "DELETE FROM tbBill WHERE billId = ?";
            PreparedStatement pStm = cn.prepareStatement(sql);
            pStm.setInt(1, billId);
            pStm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
