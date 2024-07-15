package managementproject;

import Database.ProductDAO;
import Models.Product;
import Validation.errorMessage;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InventoryController implements Initializable {

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
    @FXML
    private Text textNotice;
    @FXML
    private ImageView ImageView;
    private Image image;
    private ProductDAO dao = new ProductDAO();
    private Product proSelected;
    private int indexSelected;
    private ObservableList<Product> productList;
    private ObservableList<String> categoryList;
    private ObservableList<String> statusList;
    private FilteredList<Product> filteredList;
    private SortedList<Product> sortedList;
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

    private String imagePath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productList = FXCollections.observableArrayList(dao.listDB());
        categoryList = FXCollections.observableArrayList("food", "drink", "other");
        statusList = FXCollections.observableArrayList("Available", "Unavailable");

        filteredList = new FilteredList<>(productList, p -> true);
        sortedList = new SortedList<>(filteredList);
        sortedList.setComparator((p1, p2) -> p1.getProId().compareTo(p2.getProId()));
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

        tvProduct.setItems(sortedList);
    }

    private String getCategoryName(int cateId) {
        switch (cateId) {
            case 1:
                return "food";
            case 2:
                return "drink";
            case 3:
                return "other";
            default:
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
        imagePath = null;
    }

    @FXML
    private void ProductSelected(MouseEvent event) {
        proSelected = tvProduct.getSelectionModel().getSelectedItem();
        if (proSelected != null) {
            indexSelected = tvProduct.getSelectionModel().getSelectedIndex();
            txtProId.setText(proSelected.getProId());
            txtProName.setText(proSelected.getProName());
            txtStock.setText(String.valueOf(proSelected.getStock()));
            txtProPrice.setText(String.valueOf(proSelected.getProPrice()));
            boxName.setValue(getCategoryName(proSelected.getCateId()));
            boxStatus.setValue(proSelected.getStatus());

            if (proSelected.getProImage() != null && !proSelected.getProImage().isEmpty()) {
                String path = proSelected.getProImage();
                image = new Image("file:" + path, 250, 161, false, true);
                ImageView.setImage(image);
            } else {
                ImageView.setImage(null);
            }
        }
    }

    private boolean validateInput() {
        boolean isValid = true;
        errorMessage validate = new errorMessage();

        if (txtProId.getText().trim().isEmpty()) {
            textNotice.setText("ProId " + validate.getErrorMsg1());
            isValid = false;
        } else if (txtProName.getText().trim().isEmpty()) {
            textNotice.setText("ProName " + validate.getErrorMsg1());
            isValid = false;
        } else if (!txtProPrice.getText().matches("\\d+(\\.\\d{1,2})?")) {
            textNotice.setText("ProPrice " + validate.getErrorMsg2());
            isValid = false;
        } else if (!txtStock.getText().matches("\\d+")) {
            textNotice.setText("ProStock " + validate.getErrorMsg2());
            isValid = false;
        } else if (boxName.getSelectionModel().isEmpty()) {
            textNotice.setText("CateName " + validate.getErrorMsg1());
            isValid = false;
        } else if (boxStatus.getSelectionModel().isEmpty()) {
            textNotice.setText("Status " + validate.getErrorMsg1());
            isValid = false;
        } else {
            textNotice.setText(null);
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
            imagePath = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 168, 158, false, true);
            ImageView.setImage(image);
        }
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        if (validateInput()) {
            Product newProduct = new Product();
            newProduct.setProId(txtProId.getText());
            newProduct.setProName(txtProName.getText());
            newProduct.setStock(Integer.parseInt(txtStock.getText()));
            newProduct.setProPrice(Double.parseDouble(txtProPrice.getText()));
            newProduct.setCateId(boxName.getSelectionModel().getSelectedIndex() + 1);
            newProduct.setStatus(boxStatus.getValue());

            newProduct.setProImage(imagePath != null ? imagePath.replace("\\", "\\\\") : null);
            newProduct.setProDate(new Date());

            if (dao.AddDB(newProduct) != null) {
                productList.add(newProduct);
                clearFields();
                textNotice.setText("Product added successfully.");
            } else {
                textNotice.setText("Product ID already exists.");
            }
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        if (proSelected != null) {
            dao.DeleteDB(proSelected.getProId());
            productList.remove(indexSelected);
            clearFields();
            tvProduct.refresh();
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

            proSelected.setProImage(imagePath != null ? imagePath.replace("\\", "\\\\") : null);
            proSelected.setProDate(new Date());

            dao.UpdateDB(proSelected);
            tvProduct.refresh();
            clearFields();
            textNotice.setText("Product updated successfully.");
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

    @FXML
    private void LogOut(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                mainform.getScene().getWindow().hide();
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
