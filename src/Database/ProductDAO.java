package Database;

import Models.Category;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class ProductDAO {

    public Alert alert;
    static ConnectDB connect = new ConnectDB();
    static Connection cn = null;
    static Statement stm = null;// de chay cau lenh ko co bien
    static ResultSet rs = null; // hung ket qua tra ve
    static Scanner sc;
    static PreparedStatement pStm = null;// de chay cau lenh co bien

    ArrayList<Product> list = new ArrayList<>();
    ArrayList<Product> menulist = new ArrayList<>();
    ArrayList<Product> menuFoodlist = new ArrayList<>();

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

    public ArrayList<Product> menuListDB() {
        menulist.clear();
        String sql = "SELECT * from Product where CateId=2";
        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Product pro = new Product();
                pro.setProId(rs.getString("proId"));
                pro.setProName(rs.getString("proName"));
                pro.setProPrice(rs.getDouble("proPrice"));
                pro.setProImage(rs.getString("proImage"));
                pro.setCateId(rs.getInt("cateId"));

                menulist.add(pro);
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

        return menulist;
    }

    public ArrayList<Product> menuListFoodDB() {
        menuFoodlist.clear();
        String sql = "SELECT * from Product where CateId=1 ";
        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Product pro = new Product();
                pro.setProId(rs.getString("proId"));
                pro.setProName(rs.getString("proName"));
                pro.setProPrice(rs.getDouble("proPrice"));
                pro.setProImage(rs.getString("proImage"));
                pro.setCateId(rs.getInt("cateId"));

                menuFoodlist.add(pro);

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
        return menuFoodlist;
    }

    public Product AddDB(Product pro) {
        String checksql = "SELECT ProId FROM Product WHERE ProId =?";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(checksql);
            pStm.setString(1, pro.getProId());
            rs = pStm.executeQuery();

            if (rs.next()) {
                return null;
            } else {
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
                return pro;
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
        return null;
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

    public Integer CheckStock(String proId) {
        int stock = 0;
        String checksql = "SELECT status,stock FROM Product WHERE proId =?";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(checksql);
            pStm.setString(1, proId);
            rs = pStm.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                stock = rs.getInt("stock");

                if (!status.equals("Available") || stock == 0) {
                    stock = 0; 
                }
            }
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

        return stock;
    }

    public void UpdateStock(String proId, int newStock) {
        try {
            cn = connect.GetConnectDB();
            String sql = "UPDATE Product SET stock =?, status=? WHERE proId = ?";
            pStm = cn.prepareStatement(sql);
            pStm.setInt(1, newStock);
            if (newStock == 0) {
                pStm.setString(2, "OutOfStock");
            } else {
                pStm.setString(2, "Available");
            }
            pStm.setString(3, proId);
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

    // lấy thông tin sản phẩm  prodId
    public Product getProductById(String prodId) {
        Product product = null;
        String sql = "SELECT * FROM Product WHERE proId = ?";

        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, prodId);
            rs = pStm.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setProId(rs.getString("ProId"));
                product.setProName(rs.getString("proName"));
                product.setProPrice(rs.getDouble("proPrice"));
                product.setStock(rs.getInt("stock"));
                product.setCateId(rs.getInt("cateId"));
                product.setProImage(rs.getString("proImage"));
                product.setStatus(rs.getString("status"));
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

        return product;
    }

}
