/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package managementproject;

import Database.BillDAO;
import Database.CustomerDAO;
import Database.ProductDAO;
import Models.Bill;
import Models.Customer;
import Models.Product;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ProductItemController implements Initializable {

    @FXML
    private AnchorPane productItem;
    @FXML
    private Label proItem_Name;
    @FXML
    private Label proItem_Price;
    @FXML
    private ImageView proItem_Image;
    @FXML
    private Spinner<Integer> proItem_Spinner;
    @FXML
    private Button proItem_btnAdd;

    private SpinnerValueFactory<Integer> spin;
    private Alert alert;
    private Product pro;
    private Image image;
    private String prodId;
    private Integer pro_cateId;
    private String pro_cateName;
    private String pro_date;

    private MenuController menuController;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setQuantity();
    }

    public void setProductItem(Product pro) {
        this.pro = pro;
        pro_cateId = pro.getCateId();
        if (pro_cateId == 1) {
            pro_cateName = "Food";
        } else {
            pro_cateName = "Drink";
        }
        prodId = pro.getProId();
        proItem_Name.setText(pro.getProName());
        proItem_Price.setText(String.valueOf(pro.getProPrice()));
        String path = pro.getProImage();
        if (path != null) {
            image = new Image("file:"+path, 250, 161, false, true);
            proItem_Image.setImage(image);
        } else {
            proItem_Image.setImage(null);
        }

    }

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        proItem_Spinner.setValueFactory(spin);
    }

    ProductDAO pdao = new ProductDAO();
    private BillDAO billDAO = new BillDAO();

    @FXML
    private void Menu_AddProItem(ActionEvent event) {
        if (menuController.getCustomerId() != null) {
            data.customerId = menuController.getCustomerId();
        } else {
            data.customerId = "New Customer";
        }
        System.out.println("ham add ProductItem");
        int qty = proItem_Spinner.getValue();
        if (qty == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setContentText("Please choose quantity");
            alert.showAndWait();
            return;
        }
        // Kiểm tra và trừ hàng tồn kho
        int stock = pdao.CheckStock(prodId);
        if (stock != 0 && qty <= stock) {
            stock -= qty;

            // Lấy thông tin sản phẩm để thêm vào hóa đơn
            Product product = pdao.getProductById(prodId);
            // Tạo đối tượng Bill
            Bill bill = new Bill();
            bill.setTableNo("null");
            bill.setCus_id(data.customerId);
            bill.setUserName(data.username);
            bill.setProId(product.getProId());
            bill.setProName(product.getProName());
            bill.setCateId(product.getCateId());
            bill.setQuantity(proItem_Spinner.getValue());
            bill.setProPrice(product.getProPrice());
            bill.setStatus(product.getStatus());
            bill.setProImage(product.getProImage());
            bill.setBillTotal(product.getProPrice() * proItem_Spinner.getValue());
            bill.setBillTax(bill.getBillTotal() * data.tax);
            bill.setBillService(bill.getBillTotal() * data.tax * data.service);
            bill.setBillSubTotal(bill.getBillTotal() + bill.getBillTax() + bill.getBillService());
            bill.setBillStatus("Unpay");

           // Add the Bill object to a list
        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(bill);

        // Thêm sản phẩm vào hóa đơn
        int billId = billDAO.insertOrder(billList);

            // Cập nhật hàng tồn kho & cap nhat hien thi
            pdao.UpdateStock(prodId, stock);
            setQuantity();

            // Update the order data in the existing MenuController instance
            if (menuController != null) {
                menuController.menuShowOrderData();
                menuController.menuDisplayTotal();
                menuController.ShowProducts();

            }

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setContentText("Not Enough stock available");
            alert.showAndWait();
        }
    }

}
