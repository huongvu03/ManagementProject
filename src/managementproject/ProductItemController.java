package managementproject;

import Models.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private Product pro;
    private Image image;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setQuantity();
    }

    public void setProductItem(Product pro) {
        this.pro = pro;
        proItem_Name.setText(pro.getProName());
        proItem_Price.setText(String.valueOf(pro.getProPrice()));
        image = new Image(pro.getProImage(), 250, 161, false, true);
        proItem_Image.setImage(image);

    }
    
        public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        proItem_Spinner.setValueFactory(spin);
    }
}
