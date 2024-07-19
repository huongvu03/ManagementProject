package Database;

import Models.Bill;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import managementproject.data;

public class BillDAO {

    static ConnectDB connect = new ConnectDB();
    static Connection cn = null;
    static Statement stm = null;// de chay cau lenh ko co bien
    static ResultSet rs = null; // hung ket qua tra ve
    static Scanner sc;
    static PreparedStatement pStm = null;// de chay cau lenh co bien

    ArrayList<Bill> billOrderList = new ArrayList<>();

    public ArrayList<Bill> insertBillDB(Bill bill) {
        String sql = "INSERT INTO Bill (cus_id, billTotal, billTax, billService, billSubTotal, billDate, userName, proId, proName, cateId, quantity, proPrice, status, proImage)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, bill.getCus_id());
            pStm.setDouble(2, bill.getBillTotal());
            pStm.setDouble(3, bill.getBillTax());
            pStm.setDouble(4, bill.getBillService());
            pStm.setDouble(5, bill.getBillSubTotal());
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(bill.getBillDate().getTime());
            pStm.setDate(6, sqlDate);
            pStm.setString(7, bill.getUserName());
            pStm.setString(8, bill.getProId());
            pStm.setString(9, bill.getProName());
            pStm.setInt(10, bill.getCateId());
            pStm.setInt(11, bill.getQuantity());
            pStm.setDouble(12, bill.getProPrice());
            pStm.setString(13, bill.getStatus());
            pStm.setString(14, bill.getProImage());

            pStm.executeUpdate();

            boolean customerExists = false;
            for (Bill oldbill : billOrderList) {
                if (oldbill.getCus_id().equals(bill.getCus_id())) {
                    // Set the same billId for the same cus_id
                    if (oldbill.getBillId() == null) {
                        oldbill.setBillId(bill.getBillId());
                    }
                    // Update quantity if the same proId exists
                    if (oldbill.getProId().equals(bill.getProId())) {
                        oldbill.setQuantity(oldbill.getQuantity() + bill.getQuantity());
                    }
                    customerExists = true;
                    break;
                }
            }

            // If the customer does not exist in billOrderList, add the new bill
            if (!customerExists) {
                billOrderList.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return billOrderList;
    }

    public ArrayList<Bill> billOrder = new ArrayList<>();

    public ArrayList<Bill> getOrderDB() {
        billOrder.clear();
        String sql = "SELECT proId, proName, quantity, proPrice FROM Bill WHERE cus_id=?";

        try (Connection cn = connect.GetConnectDB(); PreparedStatement pStm = cn.prepareStatement(sql)) {
            pStm.setString(1, data.customerId);

            try (ResultSet rs = pStm.executeQuery()) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setProId(rs.getString("proId"));
                    bill.setProName(rs.getString("proName"));
                    bill.setQuantity(rs.getInt("quantity"));
                    bill.setProPrice(rs.getDouble("proPrice"));

                    boolean found = false;
                    for (Bill old : billOrder) {
                        if (old.getProId().equals(bill.getProId())) {
                            old.setQuantity(old.getQuantity() + bill.getQuantity());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {

                        billOrder.add(bill);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Order list: " + billOrder);
        return billOrder;
    }
    public ArrayList<Bill> billSubtotalList = new ArrayList<>();

    public ArrayList<Bill> getbillSubTotalDB() {
        System.out.println("ham getbill duoc goi");
        String sql = "SELECT SUM(billTotal) as billTotal, SUM(billTax) as billTax,"
                + "SUM(billService) as billService,  SUM(billSubTotal) as billSubTotal  FROM Bill WHERE cus_id =?";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, data.customerId);
            rs = pStm.executeQuery();
            billSubtotalList.clear();
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillTotal(rs.getDouble("billTotal"));
                bill.setBillTax(rs.getDouble("billTax"));
                bill.setBillService(rs.getDouble("billService"));
                bill.setBillSubTotal(rs.getDouble("billSubTotal"));
                billSubtotalList.add(bill);

            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
        System.out.println("billsub info" + billSubtotalList);
        return billSubtotalList;
    }
}
