package Database;

import Models.Bill;
import Models.Bill_Table1;
import Models.Bill_Table2;
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
import java.util.Scanner;
import javafx.scene.control.Alert;
import managementproject.data;

public class BillDAO {

    // lay billId
    public int insertBillAndGetId(Bill bill, Connection cn) throws SQLException {
        String sql = "INSERT INTO Bill (tableNo,guestNo, cus_id, userName, billSubTotal, billTax,"
                + " billService, billDiscount, billTotal, billDate, billStatus) "
                + "VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pStm = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pStm.setString(1, bill.getTableNo());
            pStm.setInt(2, bill.getGuestNo());
            pStm.setString(3, bill.getCus_id());
            pStm.setString(4, bill.getUserName());
            pStm.setDouble(5, bill.getBillSubTotal());
            pStm.setDouble(6, bill.getBillTax());
            pStm.setDouble(7, bill.getBillService());
            pStm.setDouble(8, bill.getBillDiscount());
            pStm.setDouble(9, bill.getBillTotal());
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
    public Alert alert;
    static ConnectDB connect = new ConnectDB();
    static Connection cn = null;
    static Statement stm = null;// de chay cau lenh ko co bien
    static ResultSet rs = null; // hung ket qua tra ve
    static Scanner sc;
    static PreparedStatement pStm = null;// de chay cau lenh co bien

    public ArrayList<Bill> listDB() {
        ArrayList<Bill> list = new ArrayList<>();
        String sql = "SELECT DISTINCT b.*, \n"
                + "                c.*,\n"
                + "                o.archiveDate\n"
                + "FROM Bill b\n"
                + "LEFT JOIN tbCustomer c ON b.cus_id = c.cus_id\n"
                + "LEFT JOIN OrderArchive o ON b.billId = o.billId; ";
        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {

                Bill pro = new Bill();
                pro.setBillId(rs.getInt("billId"));
                pro.setTableNo(rs.getString("tableNo"));
                pro.setGuestNo(rs.getInt("guestNo"));

                pro.setCus_id(rs.getString("cus_id"));

                pro.setUserName(rs.getString("userName"));
                pro.setBillTotal(rs.getDouble("billSubTotal"));
                pro.setBillDiscount(rs.getDouble("billDiscount"));
                pro.setBillTax(rs.getDouble("billTax"));
                pro.setBillService(rs.getDouble("billService"));
                pro.setBillSubTotal(rs.getDouble("billTotal"));
                pro.setBillDate(rs.getDate("billDate"));
                pro.setBillStatus(rs.getString("billStatus"));
                pro.setArchiveDate(rs.getDate("archiveDate"));

                Customer cus = new Customer();
                cus.setCus_id(rs.getString("cus_id"));
                cus.setName(rs.getString("name"));
                cus.setPhone(rs.getString("phone"));
                cus.setDiscount(rs.getInt("discount"));
                pro.setCustomer(cus);
                list.add(pro);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                cn.close();
                stm.close();

            } catch (Exception e) {
                e.getMessage();
            }

        }
        return list;
    }

    public ArrayList<Bill> listDB(String from, String to, String status, String sBillId) {
        ArrayList<Bill> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT b.*, \n"
                + "                c.*,\n"
                + "                o.archiveDate\n"
                + "FROM Bill b\n"
                + "LEFT JOIN tbCustomer c ON b.cus_id = c.cus_id\n"
                + "LEFT JOIN OrderArchive o ON b.billId = o.billId\n"
                + "WHERE b.billStatus = ? \n");

        if (sBillId != null && !sBillId.isEmpty()) {
            sql.append("  AND b.billId = ?\n");
        }

        sql.append("  AND b.billDate BETWEEN ? AND ?;");

        try (Connection cn = connect.GetConnectDB(); PreparedStatement pstmt = cn.prepareStatement(sql.toString())) {

            // Set parameters for the query
            int paramIndex = 1;
            pstmt.setString(paramIndex++, status);

            if (sBillId != null && !sBillId.isEmpty()) {
                pstmt.setString(paramIndex++, sBillId);
            }

            pstmt.setDate(paramIndex++, java.sql.Date.valueOf(from));
            pstmt.setDate(paramIndex++, java.sql.Date.valueOf(to));

            try (ResultSet rs = pstmt.executeQuery()) {
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
                    pro.setArchiveDate(rs.getDate("archiveDate"));

                    Customer cus = new Customer();
                    cus.setCus_id(rs.getString("cus_id"));
                    cus.setName(rs.getString("name"));
                    cus.setPhone(rs.getString("phone"));
                    cus.setDiscount(rs.getInt("discount"));
                    pro.setCustomer(cus);

                    list.add(pro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the exception
        }

        return list;
    }

    public ArrayList<Bill_Table1> Addtable1(Integer billId) {
        ArrayList<Bill_Table1> tablelist1 = new ArrayList<>();
        String checksql = "SELECT * FROM OrderArchive WHERE billId = ?";

        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(checksql);
            pStm.setInt(1, billId);
            rs = pStm.executeQuery();

            while (rs.next()) {
                Bill_Table1 btb1 = new Bill_Table1();
                btb1.setBillId(rs.getInt("billId"));
                btb1.setProId(rs.getString("proId"));
                btb1.setProName(rs.getString("proName"));
                btb1.setCateId(rs.getInt("cateId"));
                btb1.setQuantity(rs.getInt("quantity"));
                btb1.setProPrice(rs.getDouble("proPrice") * rs.getInt("quantity"));
                tablelist1.add(btb1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return tablelist1;
    }

    public ArrayList<Bill_Table2> Addtable2(Integer billId) {
        ArrayList<Bill_Table2> tablelist2 = new ArrayList<>();
        String checksql = "SELECT * FROM OrderArchive WHERE billId = ?";

        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(checksql);
            pStm.setInt(1, billId);
            rs = pStm.executeQuery();

            while (rs.next()) {
                Bill_Table2 btb1 = new Bill_Table2();
                btb1.setBillId(rs.getInt("billId"));
                btb1.setProId(rs.getString("proId"));
                btb1.setProName(rs.getString("proName"));
                btb1.setCateId(rs.getInt("cateId"));
                btb1.setQuantity(rs.getInt("quantity"));
                btb1.setProPrice(rs.getDouble("proPrice") * rs.getInt("quantity"));
                tablelist2.add(btb1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return tablelist2;
    }

    public void UpdateBillStatus(Bill pro) {
        try {
            cn = connect.GetConnectDB();
            String sql = "update Bill set billStatus ='PAID' where billId=?;update OrderArchive set archiveDate =? where billId=?;";
            pStm = cn.prepareStatement(sql);
            //pStm.setString(1, pro.getStatus());
            pStm.setInt(1, pro.getBillId());
            pStm.setDate(2, new java.sql.Date(pro.getArchiveDate().getTime()));
            pStm.setInt(3, pro.getBillId());
            pStm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    tiếp tục phần lưu bill khi slit
    public void UpdateBillSplit(Bill pro) {
        try {
            cn = connect.GetConnectDB();
            String sql = "Update Bill set tableNo=?, guestNo=?,cus_id=?,userName=?,billTotal=?,BillTax=?,billService=?,billDiscount=?,billSubTotal=? Where billId =?;";
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, pro.getTableNo());
            pStm.setInt(2, pro.getGuestNo());
            pStm.setString(3, pro.getCus_id());
            pStm.setString(4, pro.getUserName());
            pStm.setDouble(5, pro.getBillTotal());
            pStm.setDouble(6, pro.getBillTax());
            pStm.setDouble(7, pro.getBillService());
            pStm.setDouble(8, pro.getBillDiscount());
            pStm.setDouble(9, pro.getBillSubTotal());
            pStm.setDouble(10, pro.getBillId());
            pStm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void DeleteDB(int id) {
        String sql = "delete OrderArchive where billId =?;";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setInt(1, id);
            pStm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bill AddBillSlit(Bill pro) {
        if (pro == null) {
            throw new IllegalArgumentException("Bill object cannot be null");
        }

        String sql = "insert into Bill (tableNo,guestNo,cus_id,userName,billTotal,billTax,billService,billDiscount,billSubTotal,billDate,billStatus) values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pStm.setString(1, pro.getTableNo());
            pStm.setInt(2, pro.getGuestNo());
            pStm.setString(3, pro.getCus_id());
            pStm.setString(4, pro.getUserName());
            pStm.setDouble(5, pro.getBillTotal());
            pStm.setDouble(6, pro.getBillTax());
            pStm.setDouble(7, pro.getBillService());
            pStm.setDouble(8, pro.getBillDiscount());
            pStm.setDouble(9, pro.getBillSubTotal());

            // Sử dụng thời gian hiện tại
            pStm.setDate(10, new java.sql.Date(System.currentTimeMillis()));

            pStm.setString(11, pro.getBillStatus());
            pStm.execute();

            rs = pStm.getGeneratedKeys();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pro;
    }
//    public Bill AddBillSlit(Bill pro) {
//        String sql = "INSERT INTO Product (name, description, price, imagePath) VALUES (?, ?, ?, ?)";
//        try {
//            cn = connect.GetConnectDB();
//            pStm = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//            pStm.setInt(1, pro.getBillId());
//            pStm.setString(1, pro.getTableNo());
//            pStm.setInt(2, pro.getGuestNo());
//            pStm.execute();
//
//            rs = pStm.getGeneratedKeys();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (cn != null) {
//                    cn.close();
//                }
//                if (pStm != null) {
//                    pStm.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return pro;
//    }

    //CHI
    public ArrayList<Bill> billInfoDB(int billId) {
        ArrayList<Bill> list = new ArrayList<>();
        String sql = "SELECT b.billId, b.tableNo, b.guestNo, b.cus_id, b.userName, "
                + "b.billTotal, b.billDiscount, b.billTax, b.billService, b.billSubTotal, "
                + "b.billDate, b.billStatus, c.name, c.phone, c.discount "
                + "FROM Bill b "
                + "LEFT JOIN tbCustomer c ON b.cus_id = c.cus_id "
                + "WHERE b.billId = ?";
        try (Connection cn = connect.GetConnectDB(); PreparedStatement pStm = cn.prepareStatement(sql)) {
            pStm.setInt(1, billId);
            try (ResultSet rs = pStm.executeQuery()) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("billId"));
                    bill.setTableNo(rs.getString("tableNo"));
                    bill.setGuestNo(rs.getInt("guestNo"));
                    bill.setCus_id(rs.getString("cus_id"));
                    bill.setUserName(rs.getString("userName"));
                    bill.setBillTotal(rs.getDouble("billTotal"));
                    bill.setBillDiscount(rs.getDouble("billDiscount"));
                    bill.setBillTax(rs.getDouble("billTax"));
                    bill.setBillService(rs.getDouble("billService"));
                    bill.setBillSubTotal(rs.getDouble("billSubTotal"));
                    bill.setBillDate(rs.getDate("billDate"));
                    bill.setBillStatus(rs.getString("billStatus"));

                    Customer customer = new Customer();
                    customer.setCus_id(rs.getString("cus_id"));
                    customer.setName(rs.getString("name"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setDiscount(rs.getInt("discount"));

                    bill.setCustomer(customer);
                    list.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

// for SAVED Bill -> PAID
    public void UpdateStatus_SavedBill(int billId) {
        try {
            cn = connect.GetConnectDB();
            String sql = "update Bill set billStatus ='PAID' where billId=?";
            pStm = cn.prepareStatement(sql);
            //pStm.setString(1, pro.getStatus());
            pStm.setInt(1, billId);
            pStm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean UpdateBill(int billId, Bill bill) throws SQLException {
        Connection cn = null;
        PreparedStatement pStm = null;
        boolean isUpdated = false;

        try {
            cn = connect.GetConnectDB();
            String updateBillSql = "UPDATE Bill SET tableNo=?, guestNo=?, cus_id=?, userName=?, "
                    + "billTotal=?, billService=?, billTax=?, billDiscount=?, billSubTotal=?, billDate=?, billStatus=? WHERE billId=?";
            pStm = cn.prepareStatement(updateBillSql);
            pStm.setString(1, bill.getTableNo());
            pStm.setInt(2, bill.getGuestNo());
            pStm.setString(3, bill.getCus_id());
            pStm.setString(4, bill.getUserName());
            pStm.setDouble(5, bill.getBillTotal());
            pStm.setDouble(6, bill.getBillService());
            pStm.setDouble(7, bill.getBillTax());
            pStm.setDouble(8, bill.getBillDiscount());
            pStm.setDouble(9, bill.getBillSubTotal());
            pStm.setDate(10, new java.sql.Date(System.currentTimeMillis()));
            pStm.setString(11, bill.getBillStatus());
            pStm.setInt(12, billId);
            int rowsAffected = pStm.executeUpdate();

            if (rowsAffected > 0) {
                isUpdated = true;
                System.out.println("Information updated successfully: " + bill.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pStm != null) {
                pStm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return isUpdated;
    }

    public void DeleteBillIdDB(int id) throws SQLException {
        String sql = "delete from Bill where billId =?;";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setInt(1, id);
            pStm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
            pStm.close();
        }
    }
//BILL

    public List<String> getProIdsForBill(int billId) {
        List<String> proIds = new ArrayList<>();
        String selectSql = "select proId from OrderArchive where billId = ?;";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(selectSql);
            pStm.setInt(1, billId);
            rs = pStm.executeQuery();
            while (rs.next()) {
                proIds.add(rs.getString("proId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cn != null) {
                    cn.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return proIds;
    }

    public void UpdateOrderArchiveSplit1(Bill_Table1 pro) {
        try {
            cn = connect.GetConnectDB();
            String sql = "update OrderArchive set quantity =? where billId =? and proId =?;";
            pStm = cn.prepareStatement(sql);
            pStm.setInt(1, pro.getQuantity());
            pStm.setInt(2, pro.getBillId());
            pStm.setString(3, pro.getProId());
            pStm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteProIdFromBill(int billId, String proId) {
        String sql = "delete from OrderArchive where billId = ? and proId = ?;";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setInt(1, billId);
            pStm.setString(2, proId);
            pStm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bill_Table2 AddBillOrderArchive(Bill_Table2 pro) {
        int maxBillId = 0;

        // Bước 1: Lấy billId lớn nhất từ bảng Bill
        String selectMaxBillIdSql = "SELECT MAX(billId) FROM Bill";
        try {
            cn = connect.GetConnectDB();

            // Thực hiện truy vấn để lấy billId lớn nhất
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(selectMaxBillIdSql);
            if (rs.next()) {
                maxBillId = rs.getInt(1); // Lấy giá trị của billId lớn nhất
            }

            // Bước 2: Kiểm tra sự tồn tại của bản ghi trong bảng OrderArchive
            String checkExistSql = "SELECT COUNT(*) FROM OrderArchive WHERE billId = ? AND proId = ?";
            pStm = cn.prepareStatement(checkExistSql);
            pStm.setInt(1, maxBillId);
            pStm.setString(2, pro.getProId());
            rs = pStm.executeQuery();

            boolean exists = false;
            if (rs.next()) {
                exists = rs.getInt(1) > 0; // Kiểm tra số lượng bản ghi trả về
            }

            if (exists) {
                // Bước 3: Cập nhật số lượng nếu bản ghi đã tồn tại
                String updateSql = "UPDATE OrderArchive SET quantity = quantity + ? WHERE billId = ? AND proId = ?";
                pStm = cn.prepareStatement(updateSql);
                pStm.setInt(1, pro.getQuantity());
                pStm.setInt(2, maxBillId);
                pStm.setString(3, pro.getProId());
                pStm.executeUpdate();
            } else {
                // Bước 4: Chèn dữ liệu mới nếu bản ghi không tồn tại
                String insertSql = "INSERT INTO OrderArchive (billId, proId, proName, cateId, quantity, proPrice, archiveDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pStm = cn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
                pStm.setInt(1, maxBillId);
                pStm.setString(2, pro.getProId());
                pStm.setString(3, pro.getProName());
                pStm.setInt(4, pro.getCateId());
                pStm.setInt(5, pro.getQuantity());
                pStm.setDouble(6, pro.getProPrice());
                pStm.setDate(7, new java.sql.Date(System.currentTimeMillis()));
                pStm.execute();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pro;
    }

    public void DeleteDB_tableBill(int id) {
        String sql = "delete Bill where billId =?;";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setInt(1, id);
            pStm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void UpdateOrderArchiveMerge(Bill_Table2 pro) {
        try {
            cn = connect.GetConnectDB();
            String sql = "update OrderArchive set quantity =? where billId =? and proId =?;";
            pStm = cn.prepareStatement(sql);
            pStm.setInt(1, pro.getQuantity());
            pStm.setInt(2, pro.getBillId());
            pStm.setString(3, pro.getProId());
            pStm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bill_Table2 AddOrderArchiveMerge(Bill_Table2 pro) {
        String insertSql = "INSERT INTO OrderArchive (billId, proId, proName, cateId, quantity, proPrice, archiveDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            cn = connect.GetConnectDB();

            pStm = cn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            pStm.setInt(1, pro.getBillId());
            pStm.setString(2, pro.getProId());
            pStm.setString(3, pro.getProName());
            pStm.setInt(4, pro.getCateId());
            pStm.setInt(5, pro.getQuantity());
            pStm.setDouble(6, pro.getProPrice());
            pStm.setDate(7, new java.sql.Date(System.currentTimeMillis()));
            pStm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return pro;
    }

    public Integer Next_BillId() {
        int maxBillId = 0;

        // Bước 1: Lấy billId lớn nhất từ bảng Bill
        String selectMaxBillIdSql = "SELECT MAX(billId) FROM Bill";
        try {
            cn = connect.GetConnectDB();

            // Thực hiện truy vấn để lấy billId lớn nhất
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(selectMaxBillIdSql);
            if (rs.next()) {
                maxBillId = (rs.getInt(1)) + 1; // Lấy giá trị của billId lớn nhất +1
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (pStm != null) {
                    pStm.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return maxBillId;

    }
}
