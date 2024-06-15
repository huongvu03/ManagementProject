package Database;

import Models.Category;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductDAO {

    static ConnectDB connect = new ConnectDB();
    static Connection cn = null;
    static Statement stm = null;// de chay cau lenh ko co bien
    static ResultSet rs = null; // hung ket qua tra ve
    static Scanner sc;
    static PreparedStatement pStm = null;// de chay cau lenh co bien

    ArrayList<Product> list = new ArrayList<>();

    public ArrayList<Product> listDB() {
        String sql = "SELECT p.proId, p.proName, p.proPrice, p.cateId, p.stock, p.status, p.proImage, p.proDate," + "c.cateName FROM Product p JOIN Category c ON p.cateId = c.cateId";
        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {

                Product pro = new Product();

                pro.setProId(rs.getString("proId"));
                pro.setProName(rs.getString("proName"));
                pro.setProPrice(rs.getDouble("proPrice"));
                pro.setCateId(rs.getInt("cateId"));
                pro.setStock(rs.getInt("stock"));
                pro.setStatus(rs.getString("status"));
                pro.setProImage(rs.getString("proImage"));
                pro.setProDate(rs.getDate("proDate"));

                Category category = new Category();

                category.setCateId(rs.getInt("cateId"));
                category.setCateName(rs.getString("cateName"));
                pro.setCategory(category);

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

    public Product AddDB(Product pro) {
        try {
            cn = connect.GetConnectDB();
            String sql = "INSERT INTO Product (proId, proName, cateId, stock, proPrice, status, proImage, proDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, pro.getProId());
            pStm.setString(2, pro.getProName());
            pStm.setInt(3, pro.getCateId());
            pStm.setInt(4, pro.getStock());
            pStm.setDouble(5, pro.getProPrice());
            pStm.setString(6, pro.getStatus());
            pStm.setString(7, pro.getProImage());
            pStm.setDate(8, new java.sql.Date(pro.getProDate().getTime()));

            pStm.execute();

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

        return pro;

    }

    public void UpdateDB(Product pro) {
        try {
            cn = connect.GetConnectDB();
            String sql = "UPDATE Product SET proName = ?, cateId = ?, stock = ?, proPrice = ?, status = ?, proImage = ?, proDate = ? WHERE proId = ?";
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, pro.getProName());
            pStm.setInt(2, pro.getCateId());
            pStm.setInt(3, pro.getStock());
            pStm.setDouble(4, pro.getProPrice());
            pStm.setString(5, pro.getStatus());
            pStm.setString(6, pro.getProImage());
            pStm.setDate(7, new java.sql.Date(pro.getProDate().getTime()));
            pStm.setString(8, pro.getProId());

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

    public void DeleteDB(String id) {
        try {
            cn = connect.GetConnectDB();
            String sql = "DELETE FROM Product WHERE proId = ?";
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, id);
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

}
