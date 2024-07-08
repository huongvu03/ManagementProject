
package managementproject;

import Database.ProductDAO;
import Models.Product;
import Validation.errorMessage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InventoryController implements Initializable  {

    private Label label;
    @FXML
    private AnchorPane mainform;
    @FXML
    private TableView<Product> tvProduct;
    @FXML
    private TableColumn<Product, String> tcProId;
    @FXML
    private TableColumn<Product, String> tcProName;
    @FXML
    private TableColumn<Product, String> tcCateName;
    @FXML
    private TableColumn<Product, Integer> tcStock;
    @FXML
    private TableColumn<Product, Double> tcProPrice;
    @FXML
    private TableColumn<Product, String> tcStatus;
    @FXML
    private TableColumn<Product, String> tcDate;

    //FIELDS
    @FXML
    private TextField txtProId;
    @FXML
    private TextField txtProName;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtProPrice;
    @FXML
    private ComboBox<String> boxName;
    @FXML
    private ComboBox<String> boxStatus;
    @FXML
    private TextField txtSearch;

    //BUTTON
    @FXML
    private Button btnImport;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnClear;

    //NOTICE
    public Alert alert;
    @FXML
    private Text textNotice;
    @FXML
    private ImageView ImageView;
   //TODO
    private Image image;
    private ProductDAO dao = new ProductDAO();
    Product proSelected;
    int indexSelected;

    private ObservableList<Product> productList;
    private ObservableList<String> categoryList;
    private ObservableList<String> statusList;

   FilteredList<Product> filteredList;
    @FXML
    private Text proIdMsg;
    @FXML
    private Text proNameMsg;
    @FXML
    private Text stockMsg;
    @FXML
    private Text proPriceMsg;
    @FXML
    private Text cateNameMsg;
    @FXML
    private Text statusMsg;
    private Text txt_DisPlayName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productList = FXCollections.observableArrayList(dao.listDB());
        categoryList = FXCollections.observableArrayList("food", "drink");
        statusList = FXCollections.observableArrayList("Available", "OutOfStock");

        filteredList = new FilteredList<>(productList, p -> true);
        boxName.setItems(categoryList);
        boxStatus.setItems(statusList);
        ShowProducts();
    }

    public void ShowProducts() {

        tcProId.setCellValueFactory(new PropertyValueFactory<>("proId"));
        tcProName.setCellValueFactory(new PropertyValueFactory<>("proName"));
        tcCateName.setCellValueFactory(new PropertyValueFactory<>("cateName"));
        tcStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tcProPrice.setCellValueFactory(new PropertyValueFactory<>("proPrice"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("proDate"));

        tcCateName.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            String categoryName = getCategoryName(product.getCateId());
            return new SimpleStringProperty(categoryName);
        });

        tvProduct.setItems(filteredList);

    }

    private String getCategoryName(int cateId) {
        if (cateId == 1) {
            return "food";
        } else if (cateId == 2) {
            return "drink";
        } else {
            return "Unknown";
        }
    }

    private void clearFields() {
        txtProId.clear();
        txtProName.clear();
        txtStock.clear();
        txtProPrice.clear();
        boxName.getSelectionModel().clearSelection();
        boxStatus.getSelectionModel().clearSelection();
        textNotice.setText("");
        ImageView.setImage(null);
    }

    @FXML
    private void ProductSelected(MouseEvent event) {
        proSelected = tvProduct.getSelectionModel().getSelectedItem();

        // nếu có product được chọn
        if (proSelected != null) {
            // lấy index của pro được click
            indexSelected = tvProduct.getSelectionModel().getSelectedIndex();

            //gán giá trị của textfield tương ứng với product được selected
            txtProId.setText(proSelected.getProId());
            txtProName.setText(proSelected.getProName());
            txtStock.setText(String.valueOf(proSelected.getStock()));
            txtProPrice.setText(String.valueOf(proSelected.getProPrice()));
            boxName.setValue(getCategoryName(proSelected.getCateId()));
            boxStatus.setValue(proSelected.getStatus());
            
            
             if (proSelected.getProImage() != null && !proSelected.getProImage().isEmpty()) {
                image = new Image(proSelected.getProImage(), 168, 158, false, true);
                ImageView.setImage(image);
            } else {
                ImageView.setImage(null);
            }

        }
    }
    // validate input , nếu isValid trả về false nghĩa là báo lỗi, ko chạy tiếp, true thì tiếp tục
    private boolean validateInput() {
    boolean isValid = true;
    errorMessage validate = new errorMessage();

    if (txtProId.getText().trim().isEmpty()) {
        proIdMsg.setText(validate.getErrorMsg1());
        isValid = false;
    } else {
        proIdMsg.setText(null);
    }

    if (txtProName.getText().trim().isEmpty()) {
        proNameMsg.setText(validate.getErrorMsg1());
        isValid = false;
    } else {
        proNameMsg.setText(null);
    }

    if (!txtStock.getText().matches("\\d+")) {
        stockMsg.setText(validate.getErrorMsg2());
        isValid = false;
    } else {
        stockMsg.setText(null);
    }

    if (!txtProPrice.getText().matches("\\d+(\\.\\d{1,2})?")) { // Allows for decimal prices
        proPriceMsg.setText(validate.getErrorMsg2());
        isValid = false;
    } else {
        proPriceMsg.setText(null);
    }

    if (boxName.getSelectionModel().isEmpty()) {
        cateNameMsg.setText(validate.getErrorMsg1());
        isValid = false;
    } else {
        cateNameMsg.setText(null);
    }

    if (boxStatus.getSelectionModel().isEmpty()) {
        statusMsg.setText(validate.getErrorMsg1());
        isValid = false;
    } else {
        statusMsg.setText(null);
    }

    return isValid;
}


    @FXML
    private void handleImport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(mainform.getScene().getWindow());
        if (file != null) {
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 168, 158, false, true);
            ImageView.setImage(image);
        }
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        Product newProduct = new Product();
         if (validateInput()) {
        String path = null;
        newProduct.setProId(txtProId.getText());
        newProduct.setProName(txtProName.getText());
        newProduct.setStock(Integer.parseInt(txtStock.getText()));
        newProduct.setProPrice(Double.parseDouble(txtProPrice.getText()));
        newProduct.setCateId(boxName.getSelectionModel().getSelectedIndex() + 1);
        newProduct.setStatus(boxStatus.getValue());
        //newProduct.setProImage(image.getUrl());
        if(data.path == null){
            newProduct.setProImage(null);    
        }else{
            path = data.path.replace("\\","\\\\");
            newProduct.setProImage(path);    
        }
           
        newProduct.setProDate(new Date());
        

        dao.AddDB(newProduct);
        
        productList.add(newProduct);
        clearFields();
        
        textNotice.setText("Product added successfully.");
    }else {
        textNotice.setText("Please correct the errors and try again.");
    }
    
    
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        if (proSelected != null) {
            dao.DeleteDB(proSelected.getProId());
            productList.remove(indexSelected);
            clearFields();
            textNotice.setText("Product deleted successfully.");
        } else {
            textNotice.setText("No product selected for deletion.");
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
    if (proSelected != null && validateInput()) {
        proSelected.setProName(txtProName.getText());
        proSelected.setStock(Integer.parseInt(txtStock.getText()));
        proSelected.setProPrice(Double.parseDouble(txtProPrice.getText()));
        proSelected.setCateId(boxName.getSelectionModel().getSelectedIndex() + 1);
        proSelected.setStatus(boxStatus.getValue());
        proSelected.setProImage(image != null ? image.getUrl() : null);
        proSelected.setProDate(new Date());

        dao.UpdateDB(proSelected);
        tvProduct.refresh();
        clearFields();
        textNotice.setText("Product updated successfully.");
    } else {
        textNotice.setText("Please correct the errors and try again.");
    }
}

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchValue = txtSearch.getText().toLowerCase().trim();
        filteredList.setPredicate(p -> p.getProName().toLowerCase().contains(searchValue));
        textNotice.setText("Search successfully.");
    }

    @FXML
    private void ClearrAllFields(ActionEvent event) {
        clearFields();
    }

    //User
    private void LogOut(ActionEvent event) {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM 
                mainform.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT 
                URL url = new File("src/managementproject/LogPage.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Log in Page");

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}