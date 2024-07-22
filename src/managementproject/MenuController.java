package managementproject;

import Database.BillDAO;
import Database.ConnectDB;
import Database.CustomerDAO;
import Database.ProductDAO;
import Database.UserDAO;
import Models.Bill;
import Models.Customer;
import Models.Product;
import Models.UserInfo;
import Validation.errorMessage;
import java.beans.Statement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    @FXML
    private BorderPane PageView;
    @FXML
    private Text txt_DisPlayName;
//button swith form
    @FXML
    private Button inventory_btn;
    @FXML
    private Button customer_btn;
    @FXML
    private Button staff_btn;
    @FXML
    private Button menu_btn;
    @FXML
    private Button dashboard_btn;
    //DashBoard_Anchor
    @FXML
    private AnchorPane DashBoard_Anchor;
    @FXML
    private Label totalSale;
    @FXML
    private Label totalCustomer;
    @FXML
    private Label totalRevenue;
    @FXML
    private AreaChart<String, Number> areaChart1;
    @FXML
    private CategoryAxis xAxis1;
    @FXML
    private RadioButton ratioDay;
    @FXML
    private RadioButton ratioMonth;
    @FXML
    private RadioButton ratioYear;
    //inventory_Anchor
    @FXML
    private AnchorPane Inventory_Anchor;
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
    private ImageView ImageView;
    //Menu_Anchor
    @FXML
    private AnchorPane Menu_Anchor;
    @FXML
    private GridPane menu_gridPaneDrink;
    @FXML
    private GridPane menu_gridPaneFood;
    @FXML
    private TableView<Bill> menu_TbView;
    @FXML
    private TableColumn<Bill, String> menu_Col_ProName;
    @FXML
    private TableColumn<Bill, Integer> menu_Col_Quantity;
    @FXML
    private TableColumn<Bill, Double> menu_Col_Price;
    @FXML
    private Label menu_Total;
    @FXML
    private Label menu_Tax;
    @FXML
    private Label menu_Service;
    @FXML
    private Label menu_Subtotal;
    @FXML
    private Label menu_Change;
    @FXML
    private TextField menu_txtAmount;
    @FXML
    private Text textNotice;
    @FXML
    private Text textNotice1;
    //Menu-bill
    @FXML
    private TextField menu_TextNote;
    @FXML
    private Button menu_btnPAY;
    @FXML
    private Button menu_btnREMOVE;
    @FXML
    private Button menu_btnRECEIPT;
    @FXML
    private Text txt_displayCusName;
    //Customer_Anchor
    @FXML
    private AnchorPane Customer_Anchor;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> cusIdColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Customer, Integer> discountColumn;
    @FXML
    private TableColumn<Customer, Integer> deletedColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField discountField;
    @FXML
    private TextField deletedField;
    @FXML
    private TextField searchField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button searchButton;
    //staff_Anchor
    @FXML
    private AnchorPane staff_Anchor;
    @FXML
    private TextField txt_ReUserId;
    @FXML
    private TextField txt_ReUserName;
    @FXML
    private TextField txt_RePass;
    @FXML
    private ComboBox<String> userPosition;
    @FXML
    private ComboBox<String> userQuestion;
    @FXML
    private TextField txt_ReAnswer;
    @FXML
    private TableView<UserInfo> tvUserInfo;
    @FXML
    private TableColumn<UserInfo, String> tc_userId;
    @FXML
    private TableColumn<UserInfo, String> tc_userName;
    @FXML
    private TableColumn<UserInfo, String> tc_userPassWord;
    @FXML
    private TableColumn<UserInfo, String> tc_userPosition;
    @FXML
    private TableColumn<UserInfo, String> tc_question;
    @FXML
    private TableColumn<UserInfo, String> tc_answer;
    @FXML
    private TableColumn<UserInfo, String> tc_userDate;
    @FXML
    private Text Staff_textNotice;

//dashboard
    private ConnectDB db = new ConnectDB();
    private Connection connectDb;
//menu    
    @FXML
    private AnchorPane MainForm;
    @FXML
    private TabPane MenuTab_View;
    @FXML
    private TextField txt_SeachStaff;
    @FXML
    private AreaChart<?, ?> areaChart2;
    @FXML
    private CategoryAxis xAxis2;
    @FXML
    private TextField invent_txtSearch;

    public Alert alert;
    private ProductDAO dao = new ProductDAO();
    @FXML
    private TextField menu_txtPhoneSearching;
    @FXML
    private Button menu_btnStore;
    @FXML
    private TextField txt_menuTableNo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        alert.setHeaderText(null);

        ShowUserName();
        //Dashboard
        connectDb = db.GetConnectDB();
        if (connectDb != null) {
            getTotalData();
            ratioDay.setSelected(true);
            getDataByDay();
        } else {
            System.err.println("Database connection failed.");
        }
        // inventory
        productList = FXCollections.observableArrayList(dao.listDB());
        categoryList = FXCollections.observableArrayList("food", "drink");
        statusList = FXCollections.observableArrayList("Available", "OutOfStock");

        filteredList = new FilteredList<>(productList, p -> true);
        sortedList = new SortedList<>(filteredList);
        sortedList.setComparator((p1, p2) -> p1.getProId().compareTo(p2.getProId()));
        boxName.setItems(categoryList);
        boxStatus.setItems(statusList);
        ShowProducts();
        //Menu
        menu_Col_ProName.setCellValueFactory(new PropertyValueFactory<>("proName"));
        menu_Col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_Col_Price.setCellValueFactory(new PropertyValueFactory<>("proPrice"));
        menuShowOrderData();
        menuDisplayCard();
        menuShowOrderData();
        menuDisplayTotal();

        //Customer
        // Initialize TableView columns
        cusIdColumn.setCellValueFactory(new PropertyValueFactory<>("cus_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        deletedColumn.setCellValueFactory(new PropertyValueFactory<>("deleted"));

        // Load data into TableView
        loadData();

        // Handle row click event
        customerTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) { // single click
                handleTableRowClicked();
            }
        });

        //STAFF
        uList.setAll(udao.listDB());
        positionList();
        questionList();
        showUser();
    }

// NAVBAR CONTROL
    public void ShowUserName() {
        String user = data.username;
        txt_DisPlayName.setText(user);
    }

    @FXML
    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            DashBoard_Anchor.setVisible(true);
            Inventory_Anchor.setVisible(false);
            Menu_Anchor.setVisible(false);
            Customer_Anchor.setVisible(false);
            staff_Anchor.setVisible(false);

        } else if (event.getSource() == inventory_btn) {
            DashBoard_Anchor.setVisible(false);
            Inventory_Anchor.setVisible(true);
            Menu_Anchor.setVisible(false);
            Customer_Anchor.setVisible(false);
            staff_Anchor.setVisible(false);
            ShowProducts();

        } else if (event.getSource() == menu_btn) {
            DashBoard_Anchor.setVisible(false);
            Inventory_Anchor.setVisible(false);
            Menu_Anchor.setVisible(true);
            Customer_Anchor.setVisible(false);
            staff_Anchor.setVisible(false);
            menuDisplayCard();
            menuShowOrderData();
            menuDisplayTotal();
            menu_DisplayChange();

        } else if (event.getSource() == customer_btn) {
            DashBoard_Anchor.setVisible(false);
            Inventory_Anchor.setVisible(false);
            Menu_Anchor.setVisible(false);
            Customer_Anchor.setVisible(true);
            staff_Anchor.setVisible(false);

//            customersShowData();
        } else if (event.getSource() == staff_btn) {
            DashBoard_Anchor.setVisible(false);
            Inventory_Anchor.setVisible(false);
            Menu_Anchor.setVisible(false);
            Customer_Anchor.setVisible(false);
            staff_Anchor.setVisible(true);

//            customersShowData();
        }
    }

    public void Clear() {
        menu_gridPaneDrink.getChildren().clear();
        menu_gridPaneDrink.getRowConstraints().clear();
        menu_gridPaneDrink.getColumnConstraints().clear();
        menu_gridPaneFood.getChildren().clear();
        menu_gridPaneFood.getRowConstraints().clear();
        menu_gridPaneFood.getColumnConstraints().clear();

    }

    @FXML
    private void LogOut(ActionEvent event) {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Notice:");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM 
                PageView.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT 
                URL url = new File("src/managementproject/LogPage.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Log in Page");
                stage.setScene(scene);
                stage.setFullScreen(true);
                stage.show();

            } else if (option.isPresent() && option.get() == ButtonType.CANCEL) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notice:");
                alert.setHeaderText(null);
                alert.setContentText("Log out operation was cancelled.");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
// DASHBOARD

    private void getTotalData() {
        try (java.sql.Statement statement = connectDb.createStatement()) {
            String getTotalSaleQuery = "SELECT COUNT(proId) AS total_sale FROM Product;";
            ResultSet queryResult1 = statement.executeQuery(getTotalSaleQuery);
            if (queryResult1.next()) {
                totalSale.setText(queryResult1.getString("total_sale"));
            }
            queryResult1.close();

            String getTotalCustomerQuery = "SELECT COUNT(cateId) AS total_customer FROM Category;";
            ResultSet queryResult2 = statement.executeQuery(getTotalCustomerQuery);
            if (queryResult2.next()) {
                totalCustomer.setText(queryResult2.getString("total_customer"));
            }
            queryResult2.close();

            String getTotalRevenueQuery = "SELECT COUNT(userId) AS total_earn FROM UserInfo;";
            ResultSet queryResult3 = statement.executeQuery(getTotalRevenueQuery);
            if (queryResult3.next()) {
                totalRevenue.setText(queryResult3.getString("total_earn"));
            }
            queryResult3.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getDataByDay() {
        loadData("Day of the Month", "Earning Per Day", "Sales Per Day", LocalDate.now().getMonth().name(), 0, LocalDate.now().getDayOfMonth(), "day", String.valueOf(LocalDate.now().getYear()) + "-07-", "");
    }

    private void getDataByMonth() {
        loadData("Month of the Year", "Earning Per Month", "Sales Per Month", String.valueOf(LocalDate.now().getYear()), 0, LocalDate.now().getMonthValue(), "month", String.valueOf(LocalDate.now().getYear()) + "-", "-%");
    }

    private void getDataByYear() {
        loadData("Year", "Earning Per Year", "Sales Per Year", "per Year", 2020, LocalDate.now().getYear(), "year", "", "-%");
    }

    private void loadData(String xAxisLabel, String series1Name, String series2Name, String title, int minPeriod, int maxPeriod, String periodType, String dateFormat1, String dateFormat2) {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        //XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        areaChart1.getData().clear();
        areaChart1.setTitle("Sales in " + title);
        series1.setName(series1Name);
        //series2.setName(series2Name);
        xAxis1.setLabel(xAxisLabel);
        //xAxis1.autosize();
        xAxis1.setAutoRanging(false);
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (int period = minPeriod; period <= maxPeriod; period++) {
            String formattedPeriod = (period < 10 ? "0" : "") + period;
            categories.add(formattedPeriod);
            try (java.sql.Statement statement = connectDb.createStatement()) {
                String getSaleQuery = "SELECT SUM(proPrice) AS sale FROM Product WHERE proDate LIKE '" + dateFormat1 + formattedPeriod + dateFormat2 + "';";
                ResultSet queryResult1 = statement.executeQuery(getSaleQuery);
                double sale = 0;
                if (queryResult1.next()) {
                    sale = queryResult1.getDouble("sale");
                }
                queryResult1.close();

//                String getCupsQuery = "SELECT COUNT(userId) AS cups FROM UserInfo WHERE userDate LIKE '" + dateFormat + formattedPeriod + "%';";
//                ResultSet queryResult2 = statement.executeQuery(getCupsQuery);
//                int cups = 0;
//                if (queryResult2.next()) {
//                    cups = queryResult2.getInt("cups");
//                }
//                queryResult2.close();
                //series1.getData().add(new XYChart.Data<>(String.valueOf(period), sale));
                series1.getData().add(new XYChart.Data<>(formattedPeriod, sale));
//                series2.getData().add(new XYChart.Data<>(String.valueOf(period), cups));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        areaChart1.getData().add(series1);
        xAxis1.setCategories(categories);
        //      areaChart1.getData().add(series2);
    }

    @FXML
    private void HandleSortByDay(ActionEvent event) {
        ratioDay.setSelected(true);
        ratioMonth.setSelected(false);
        ratioYear.setSelected(false);
        resetAreaChart();
        getDataByDay();
    }

    @FXML
    private void HandleSortByMonth(ActionEvent event) {
        ratioDay.setSelected(false);
        ratioMonth.setSelected(true);
        ratioYear.setSelected(false);
        resetAreaChart();
        getDataByMonth();
    }

    @FXML
    private void HandleSortByYear(ActionEvent event) {
        ratioDay.setSelected(false);
        ratioMonth.setSelected(false);
        ratioYear.setSelected(true);
        resetAreaChart();
        getDataByYear();
    }

    private void resetAreaChart() {
        areaChart1.getData().clear();  // Clear all series data
        xAxis1.setLabel(null);         // Reset the x-axis label
        xAxis1.setAutoRanging(true);   // Enable auto-ranging
        areaChart1.setTitle(null);     // Reset the chart title
    }

//INVENTORY
    Product proSelected;
    int indexSelected;
    private Image image;
    private ObservableList<Product> productList;
    private ObservableList<String> categoryList;
    private ObservableList<String> statusList;
    private SortedList<Product> sortedList;
    FilteredList<Product> filteredList;

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

    // validate input , nếu isValid trả về false nghĩa là báo lỗi, ko chạy tiếp, true thì tiếp tục
    private boolean validateInput() {
        boolean isValid = true;
        errorMessage validate = new errorMessage();

        if (txtProId.getText().trim().isEmpty()) {
            textNotice.setText("ProId " + validate.getErrorMsg1());
            isValid = false;
        } else if (txtProName.getText().trim().isEmpty()) {
            textNotice.setText("ProName " + validate.getErrorMsg1());
            isValid = false;
        } else if (!txtProPrice.getText().matches("\\d+(\\.\\d{1,2})?")) { // Allows for decimal prices
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
    private void HanleImportImage(ActionEvent event) {
     FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(MainForm.getScene().getWindow());
        if (file != null) {
            data.path = file.getAbsolutePath();
            //image = new Image(file.toURI().toString(), 168, 158, false, true);
            //image = new Image(file.toURI().toString(), 250, 161, false, true);
            image = new Image(file.toURI().toString(), 200, 200, false, true);

            ImageView.setImage(image);
        }
    }

    @FXML
    private void inventory_proSlected(MouseEvent event) {
       //        proSelected = tvProduct.getSelectionModel().getSelectedItem();
//        // nếu có product được chọn
//        if (proSelected != null) {
//            // lấy index của pro được click
//            indexSelected = tvProduct.getSelectionModel().getSelectedIndex();
//
//            //gán giá trị của textfield tương ứng với product được selected
//            txtProId.setText(proSelected.getProId());
//            txtProName.setText(proSelected.getProName());
//            txtStock.setText(String.valueOf(proSelected.getStock()));
//            txtProPrice.setText(String.valueOf(proSelected.getProPrice()));
//            boxName.setValue(getCategoryName(proSelected.getCateId()));
//            boxStatus.setValue(proSelected.getStatus());
//
//            if (proSelected.getProImage() != null && !proSelected.getProImage().isEmpty()) {
//                //File file = new File(proSelected.getProImage());
//
//                //image = new Image(proSelected.getProImage(), 168, 158, false, true);
//                String path = proSelected.getProImage();
//                image = new Image("file:" + path, 250, 161, false, true);
//                //ImageView.setImage(image);
//                ImageView.setImage(image);
//                System.out.println("có tìm thấy hình");
//            } else {
//                ImageView.setImage(null);
//                System.out.println("không tìm thấy hình");
//            }
//
//        }

        //test
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
                //image = new Image("file:" + path, 250, 161, false, true);
                image = new Image("file:" + path, 200, 200, false, true);

                ImageView.setImage(image);
                System.out.println("Image found");
            } else {
                ImageView.setImage(null);
                System.out.println("Image not found");
            }
        }
    }

    @FXML
    private void inven_Add(ActionEvent event) {
       //        Product newProduct = new Product();
//        if (validateInput()) {
//            String path = null;
//            newProduct.setProId(txtProId.getText());
//            newProduct.setProName(txtProName.getText());
//            newProduct.setStock(Integer.parseInt(txtStock.getText()));
//            newProduct.setProPrice(Double.parseDouble(txtProPrice.getText()));
//            newProduct.setCateId(boxName.getSelectionModel().getSelectedIndex() + 1);
//            newProduct.setStatus(boxStatus.getValue());
//
//            if (data.path == null) {
//                newProduct.setProImage(null);
//            } else {
//                path = data.path.replace("\\", "\\\\");
//                newProduct.setProImage(path);
//            }
//            newProduct.setProDate(new Date());
//
//            if (dao.AddDB(newProduct) != null) {
//                productList.add(newProduct);
//                data.path = null;
//                clearFields();
//                textNotice.setText("Product added successfully.");
//            } else {
//
//                textNotice.setText("trung id");
//
//            }
//        } 
        //test
        Product newProduct = new Product();
        if (validateInput()) {
            String path = null;
            newProduct.setProId(txtProId.getText());
            newProduct.setProName(txtProName.getText());
            newProduct.setStock(Integer.parseInt(txtStock.getText()));
            newProduct.setProPrice(Double.parseDouble(txtProPrice.getText()));
            newProduct.setCateId(boxName.getSelectionModel().getSelectedIndex() + 1);
            newProduct.setStatus(boxStatus.getValue());

            if (data.path == null) {
                newProduct.setProImage(null);
            } else {
                path = data.path.replace("\\", "\\\\");
                newProduct.setProImage(path);
            }
            newProduct.setProDate(new Date());

            if (dao.AddDB(newProduct) != null) {
                productList.add(newProduct);
                data.path = null;
                clearFields();
                textNotice.setText("Product added successfully.");
            } else {
                textNotice.setText("Duplicate ID");
            }
        }
    }

    @FXML
    private void Inven_Delete(ActionEvent event) {
       //        if (proSelected != null) {
//            dao.DeleteDB(proSelected.getProId());
//            productList.remove(indexSelected);
//            clearFields();
//            textNotice.setText("Product deleted successfully.");
//        } else {
//            textNotice.setText("No product selected for deletion.");
//        }

        //test
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
    private void inven_Update(ActionEvent event) {
       //      if (proSelected != null && validateInput()) {
////            String path = null;
//            proSelected.setProName(txtProName.getText());
//            proSelected.setStock(Integer.parseInt(txtStock.getText()));
//            proSelected.setProPrice(Double.parseDouble(txtProPrice.getText()));
//            proSelected.setCateId(boxName.getSelectionModel().getSelectedIndex() + 1);
//            proSelected.setStatus(boxStatus.getValue());
////            proSelected.setProImage(image != null ? image.getUrl() : null);
//            if (data.path == null) {
//                proSelected.setProImage(null);
//            } else {
//               String path = data.path.replace("\\", "\\\\");
//                proSelected.setProImage(path);
//            }
//            
//            proSelected.setProDate(new Date());
//
//            dao.UpdateDB(proSelected);
//            proSelected.setProImage(null);
//            tvProduct.refresh();
//            clearFields();
//            textNotice.setText("Product updated successfully.");
//        } else {
//
//        }

        //test
        if (proSelected != null && validateInput()) {
            proSelected.setProName(txtProName.getText());
            proSelected.setStock(Integer.parseInt(txtStock.getText()));
            proSelected.setProPrice(Double.parseDouble(txtProPrice.getText()));
            proSelected.setCateId(boxName.getSelectionModel().getSelectedIndex() + 1);
            proSelected.setStatus(boxStatus.getValue());

            // Only update the image path if a new image was imported
            if (data.path != null) {
                String path = data.path.replace("\\", "\\\\");
                proSelected.setProImage(path);
            }

            proSelected.setProDate(new Date());

            dao.UpdateDB(proSelected);
            tvProduct.refresh();
            clearFields();
            textNotice.setText("Product updated successfully.");

            // Reset data.path to null after update to prevent incorrect path usage in subsequent operations
            data.path = null;
        }
    }

    @FXML
    private void inven_Search(ActionEvent event) {
        String searchValue = invent_txtSearch.getText().toLowerCase().trim();
        filteredList.setPredicate(p -> p.getProName().toLowerCase().contains(searchValue));
        textNotice.setText("Search successfully.");
    }

    @FXML
    private void inven_ClearFields(ActionEvent event) {
        clearFields();
    }

    //MENU 
    private ObservableList<Product> cardListDrinkData = FXCollections.observableArrayList();
    private ObservableList<Product> cardListFoodData = FXCollections.observableArrayList();

    public void menuDisplayCard() {
        menu_gridPaneDrink.getChildren().clear();
        menu_gridPaneDrink.getRowConstraints().clear();
        menu_gridPaneDrink.getColumnConstraints().clear();
        cardListDrinkData.clear();
        cardListDrinkData.setAll(dao.menuListDB());
        int row = 0;
        int column = 0;
        for (int q = 0; q < cardListDrinkData.size(); q++) {
            try {
                URL fxmlLocation = getClass().getResource("/managementproject/ProductItem.fxml");
                if (fxmlLocation == null) {
                    System.err.println("Cannot find ProductItem.fxml");
                    return;
                }

                FXMLLoader load = new FXMLLoader(fxmlLocation);
                AnchorPane pane = load.load();
                ProductItemController cardC = load.getController();
                cardC.setProductItem(cardListDrinkData.get(q));
                // Pass the MenuController instance to ProductItemController
                cardC.setMenuController(this);

                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                menu_gridPaneDrink.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //food   
        // Reset row and column for the next grid        
        menu_gridPaneFood.getChildren().clear();
        menu_gridPaneFood.getRowConstraints().clear();
        menu_gridPaneFood.getColumnConstraints().clear();
        cardListFoodData.clear();
        cardListFoodData.setAll(dao.menuListFoodDB());
        row = 0;
        column = 0;
        for (int q = 0; q < cardListFoodData.size(); q++) {
            try {
                URL fxmlLocation = getClass().getResource("/managementproject/ProductItem.fxml");
                if (fxmlLocation == null) {
                    System.err.println("Cannot find ProductItem.fxml");
                    return;
                }

                FXMLLoader load = new FXMLLoader(fxmlLocation);
                AnchorPane pane = load.load();
                ProductItemController cardC = load.getController();
                cardC.setProductItem(cardListFoodData.get(q));
                // Pass the MenuController instance to ProductItemController
                cardC.setMenuController(this);

                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                menu_gridPaneFood.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public BillDAO billDAO = new BillDAO();
    public ObservableList<Bill> orderList = FXCollections.observableArrayList();
    private int currentBillId=1;

    public void menuShowOrderData() {
        if (currentBillId != 0) { // Ensure you have a valid billId
            orderList.clear();
            orderList.addAll(billDAO.getOrderDB(currentBillId));
            menu_TbView.setItems(orderList);
        } else {
            System.out.println("Invalid billId. Cannot fetch orders.");
        }
    }

    public String getCustomerId() {
        String cusPhoneSearching = menu_txtPhoneSearching.getText();
        if (!cusPhoneSearching.isEmpty() && !CustomerDAO.searchByPhone(cusPhoneSearching).isEmpty()) {
            for (Customer c : CustomerDAO.searchByPhone(cusPhoneSearching)) {
                data.customerId = c.getCus_id();
                txt_displayCusName.setText(c.getName());
                return data.customerId;
            }
        } else {
//            data.customerId = "New Customer" + count++;
//            txt_displayCusName.setText("null" + data.customerId);
            txt_displayCusName.setText("null");
        }
        System.out.println("Ham getCusId" + data.customerId);
        return data.customerId;

    }

    private double subtotal = 0;
    private double amount = 0;
    private double change = 0;
    private double total = 0;
    private double billtax = 0;
    private double billservice = 0;

    public void menuGetSubtotal() {
        ObservableList<Bill> menuSubList = FXCollections.observableArrayList();
        menuSubList.setAll(billDAO.getbillSubTotalDB(currentBillId));
        if (!menuSubList.isEmpty()) {
            Bill c = menuSubList.get(0);
            total = c.getBillTotal();
            billtax = c.getBillTax();
            billservice = c.getBillService();
            subtotal = c.getBillSubTotal();
        } else {
            total = 0.0;
            billtax = 0.0;
            billservice = 0.0;
            subtotal = 0.0;
        }
    }

    public void menuDisplayTotal() {
        menuGetSubtotal();
        menu_Total.setText(total + " VND ");
        menu_Tax.setText(billtax + " VND ");
        menu_Service.setText(billservice + " VND ");
        menu_Subtotal.setText(subtotal + " VND ");
        if (subtotal != 0) {
            menu_Subtotal.setText(subtotal + "VND ");
        } else {
            menu_Subtotal.setText("");
        }
    }

    @FXML
    private void menu_SearchCus_id(ActionEvent event) {
        getCustomerId();
    }

    @FXML
    private void menu_DisplayChange() {
        try {
            String amountStr = menu_txtAmount.getText().trim();
            if (amountStr.isEmpty()) {
                return;
            }
            amountStr = amountStr.replaceAll("[^\\d.]", "");
            amount = Double.parseDouble(amountStr);
            if (amount > 0) {
                if (amount < subtotal) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error:");
                    alert.setHeaderText(null);
                    alert.setContentText("Amount must greater than subtotal");
                    alert.showAndWait();
                } else {
                    change = amount - subtotal;
                    menu_Change.setText(String.format("%,.3f", change) + "VND ");
                }
            }
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setContentText("Invalid amount entered. Please enter a valid number.");
            alert.showAndWait();
        }
    }

    @FXML
    private void menu_Pay(ActionEvent event) {
        if (subtotal == 0 || menu_txtAmount.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setContentText("Subtotal or Amount cant be blank");
            alert.showAndWait();
        } else {
            getCustomerId();
            menuGetSubtotal();
            String tableNo = "null";
            if (txt_menuTableNo.getText() != null) {
                tableNo = txt_menuTableNo.getText();
            }
            Bill bill = new Bill();
            for (Bill c : billDAO.getOrderDB(currentBillId)) {
                bill.setBillId(c.getBillId());
                bill.setTableNo(tableNo);
                bill.setBillStatus("Pay");
            }
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            bill.setBillDate(sqlDate);

            billDAO.UpdateDB(bill);
            menuShowOrderData();
            menuRestart();

        }
    }

    public void menuRestart() {
        total = 0;
        billtax = 0;
        billservice = 0;
        subtotal = 0;
        amount = 0;
        change = 0;
        menu_Total.setText("0.0");
        menu_Tax.setText(" 0.0");
        menu_Service.setText("0.0");
        menu_Subtotal.setText(" 0.0");
        menu_txtAmount.setText("");
        menu_Change.setText("0.0");
        menu_TbView.getItems().clear();
    }
    private String getProId;

    @FXML
    private void menu_productSelected(MouseEvent event) {
        Bill proId = menu_TbView.getSelectionModel().getSelectedItem();
        int num = menu_TbView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        getProId = proId.getProId();
    }

    @FXML
    private void menu_RemoveItem(ActionEvent event) {
        if (getProId == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please select the order you want to remove");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete ?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                billDAO.DeleteDB(getProId);
                menuShowOrderData();
            }
        }
    }

    @FXML
    private void menu_Receipt(ActionEvent event) {
    }

    @FXML
    private void menu_StoreBill(ActionEvent event) {
        getCustomerId();
        menuGetSubtotal();
        String tableNo = "null";
        if (txt_menuTableNo.getText() != null) {
            tableNo = txt_menuTableNo.getText();
        }
        Bill bill = new Bill();
        for (Bill c : billDAO.getOrderDB(currentBillId)) {
            bill.setBillId(c.getBillId());
            bill.setTableNo(tableNo);
            bill.setBillStatus("UnPay");
        }
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        bill.setBillDate(sqlDate);
        billDAO.UpdateDB(bill);
        menuShowOrderData();
        menuRestart();
    }

//CUSTOMER
    private void loadData() {
        // Load data from database using CustomerDAO
        List<Customer> customers = CustomerDAO.getList();
        customerTable.getItems().setAll(customers);
    }

    private void handleTableRowClicked() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            nameField.setText(selectedCustomer.getName());
            phoneField.setText(selectedCustomer.getPhone());
            emailField.setText(selectedCustomer.getEmail());
            discountField.setText(String.valueOf(selectedCustomer.getDiscount()));
            deletedField.setText(String.valueOf(selectedCustomer.getDeleted()));
        }
    }

    private void clearCustomerFields() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        discountField.clear();
        deletedField.clear();
    }

    @FXML
    private void handleAddCustomer(ActionEvent event) {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        int discount = Integer.parseInt(discountField.getText());
        int deleted = Integer.parseInt(deletedField.getText());

        Customer newCustomer = new Customer(null, name, phone, email, discount, deleted);
        int inserted = CustomerDAO.insert(newCustomer);
        if (inserted > 0) {
            customerTable.getItems().add(newCustomer);
            clearCustomerFields();
        }
    }

    @FXML
    private void handleUpdateCustomer(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            int discount = Integer.parseInt(discountField.getText());
            int deleted = Integer.parseInt(deletedField.getText());

            int updated = CustomerDAO.update(selectedCustomer, name, phone, email, discount, deleted);
            if (updated > 0) {
                selectedCustomer.setName(name);
                selectedCustomer.setPhone(phone);
                selectedCustomer.setEmail(email);
                selectedCustomer.setDiscount(discount);
                selectedCustomer.setDeleted(deleted);
                customerTable.refresh();
                clearFields();
            }
        }
    }

    @FXML
    private void handleDeleteCustomer(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            int deleted = CustomerDAO.delete(selectedCustomer.getCus_id());
            if (deleted > 0) {
                customerTable.getItems().remove(selectedCustomer);
                clearFields();
            }
        }
    }

    @FXML
    private void handleSearchByPhone(ActionEvent event) {
        String phone = searchField.getText();
        List<Customer> searchResults = CustomerDAO.searchByPhone(phone);

        if (searchResults.isEmpty()) {
            loadData();
        } else {
            customerTable.getItems().setAll(searchResults);
        }
    }

    //STAFF
    static ObservableList<String> positionList;
    static ObservableList<String> questionList;

    public UserDAO udao = new UserDAO();
    public ObservableList<UserInfo> uList = FXCollections.observableArrayList();
    FilteredList<UserInfo> userfilteredList = new FilteredList<>(uList, p -> true);
    UserInfo userSelected;
    int userindexSelected;

    private void positionList() {
        positionList = FXCollections.observableArrayList("Staff", "Supervisor", "Admin");
        userPosition.setItems(positionList);
    }

    private void questionList() {
        questionList = FXCollections.observableArrayList("What is your favorite Color?", "What is your favorite food?", "what is your  favorite Place?");
        userQuestion.setItems(questionList);
    }

    public void showUser() {
        tc_userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tc_userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tc_userPassWord.setCellValueFactory(new PropertyValueFactory<>("userPassWord"));
        tc_userPosition.setCellValueFactory(new PropertyValueFactory<>("userPosition"));
        tc_question.setCellValueFactory(new PropertyValueFactory<>("question"));
        tc_answer.setCellValueFactory(new PropertyValueFactory<>("answer"));
        tc_userDate.setCellValueFactory(new PropertyValueFactory<>("userDate"));
        tvUserInfo.setItems(userfilteredList);
    }

    @FXML
    private void userSelected(MouseEvent event) {
        userSelected = tvUserInfo.getSelectionModel().getSelectedItem();

        // nếu có product được chọn
        if (userSelected != null) {
            // lấy index của pro được click
            userindexSelected = tvUserInfo.getSelectionModel().getSelectedIndex();

            //gán giá trị của textfield tương ứng với product được selected
            txt_ReUserId.setText(userSelected.getUserId());
            txt_ReUserName.setText(userSelected.getUserName());
            txt_RePass.setText(String.valueOf(userSelected.getUserPassWord()));
            txt_ReAnswer.setText(String.valueOf(userSelected.getAnswer()));
            userPosition.setValue(userSelected.getUserPosition());
            userQuestion.setValue(userSelected.getQuestion());

        } else {

        }
    }

    @FXML
    private void Staff_SignUp(ActionEvent event) {
        if (txt_ReUserId.getText().isEmpty() || txt_ReUserName.getText().isEmpty()
                || txt_RePass.getText().isEmpty() || txt_ReAnswer.getText().isEmpty()
                || userPosition.getSelectionModel().getSelectedItem().isEmpty()
                || userQuestion.getSelectionModel().getSelectedItem().isEmpty()) {
            Staff_textNotice.setText("Please Fill All Blank");
        } else {
            String userId = txt_ReUserId.getText();
            String userName = txt_ReUserName.getText();
            String userPassWord = txt_RePass.getText();
            String userPostion = userPosition.getSelectionModel().getSelectedItem();
            String question = userQuestion.getSelectionModel().getSelectedItem();
            String anwser = txt_ReAnswer.getText();
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            UserInfo u = new UserInfo(userId, userName, userPassWord, userPostion, question, anwser, sqlDate);

            if (udao.addDB(u) != null) {
                uList.add(u);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information:");
                alert.setHeaderText(null);
                alert.setContentText("Add successful !");
                alert.showAndWait();
                txt_ReUserId.clear();
                txt_ReUserName.clear();
                txt_RePass.clear();
                txt_ReAnswer.clear();
            } else {
                alert.setContentText("Add failed, User might already exist");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void Staff_Delete(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notice:");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete?");

        if (userSelected != null) {
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                String id = userSelected.getUserId();
                udao.deleteDB(id);
                uList.remove(userindexSelected);
                userSelected = null;
                userindexSelected = 0;
            } else if (option.isPresent() && option.get() == ButtonType.CANCEL) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notice:");
                alert.setHeaderText(null);
                alert.setContentText("Delete operation was cancelled.");
                alert.showAndWait();
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Notice:");
            alert.setHeaderText(null);
            alert.setContentText("Please choose a user to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void Staff_Update(ActionEvent event) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        alert.setHeaderText(null);

        if (userSelected != null) {
            String rId = userSelected.getUserId();
            if (!txt_ReUserId.getText().equals(rId)) {
                Staff_textNotice.setText("ProId cant changed");
                txt_ReUserId.setText(rId);
                return;
            }

            String rName = txt_ReUserName.getText();
            if (rName.isEmpty()) {
                Staff_textNotice.setText("UserName cant blank");
                return;
            }

            String rPass = txt_RePass.getText();
            if (rPass.isEmpty()) {
                Staff_textNotice.setText("PassWord  cant blank");
                return;
            }

            String rPosition = String.valueOf(userPosition.getValue());
            String rQuestion = String.valueOf(userQuestion.getValue());
            String rAnswer = txt_ReAnswer.getText();
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            UserInfo u = new UserInfo(rId, rName, rPass, rPosition, rQuestion, rAnswer, sqlDate);
            udao.AdminEditDB(u);
            uList.set(userindexSelected, u);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Update successful");
            alert.showAndWait();
            Clear();
        } else {
            alert.setContentText("Please choose updated product");
            alert.showAndWait();
        }
    }

    @FXML
    private void Staff_Search(ActionEvent event) {
        String sname = txt_SeachStaff.getText();
        userfilteredList.setPredicate(p -> p.getUserName().contains(sname));
        if (userfilteredList.isEmpty()) {
            txt_SeachStaff.setText("No result. Please input key words");
        }
    }

    @FXML
    private void Staff_ClearAllFields(ActionEvent event) {
        StaffClear();
    }

    public void StaffClear() {
        txt_ReUserId.clear();
        txt_ReUserName.clear();
        txt_RePass.clear();
        txt_ReAnswer.clear();
        userPosition.getValue();
        userQuestion.getValue();
        Staff_textNotice.setText("");
    }

}
