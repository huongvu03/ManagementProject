package managementproject;

import Database.OrderDAO;
import Database.ProductDAO;
import Models.Order;
import Models.Product;
import java.net.URL;
import java.util.ResourceBundle;
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
    private int stock;

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
        stock = pro.getStock();
        String path = pro.getProImage();

        if (stock > 0) {
            if (pro.getStatus().equals("Available")) {
                if (path!=null) {
                    image = new Image("file:" + path, 250, 161, false, true);
                } else {
                    image = null;
                }
            }
            if (pro.getStatus().equals("UnAvailable")) {
                URL imageUrl = getClass().getResource("/managementproject/resources/image/unavaiable.png");
                image = new Image(imageUrl.toString(), 250, 161, false, true);
            }
        } else {
            URL imageUrl = getClass().getResource("/managementproject/resources/image/soldOut.png");
            image = new Image(imageUrl.toString(), 250, 161, false, true);
        }
        proItem_Image.setImage(image);

    }

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        proItem_Spinner.setValueFactory(spin);
    }

    private ProductDAO pdao = new ProductDAO();
    private OrderDAO oDAO = new OrderDAO();

    @FXML
    private void Menu_AddProItem(ActionEvent event) {

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
            // Tạo đối tượng 
            Order o = new Order();
            o.setProId(product.getProId());
            o.setProName(product.getProName());
            o.setCateId(product.getCateId());
            o.setQuantity(proItem_Spinner.getValue());
            o.setProPrice(product.getProPrice());
            o.setNote(menuController.getNote());

            // Thêm sản phẩm vào hóa đơn
            oDAO.insertOrder(o);
            System.out.println("insert info"+o.toString());

            // Cập nhật hàng tồn kho & cap nhat hien thi
            pdao.UpdateStock(prodId, stock);
            setQuantity();
            
            // Update the order data in the existing MenuController instance
            if (menuController != null) {
                menuController.menuShowOrderData();
                menuController.menuDisplayTotal();
                menuController.ShowProducts();
                menuController.menuDisplayCard();

            }
             menuController.clearNote();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setContentText("Not Enough stock available");
            alert.showAndWait();
        }
    }

}
