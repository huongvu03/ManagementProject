package managementproject;

import Database.BillDAO;
import Database.ConnectDB;
import Database.CustomerDAO;
import Database.DashBoardDAO;
import Database.OrderDAO;
import Database.ProductDAO;
import Database.UserDAO;
import Models.*;
import Validation.errorMessage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.beans.Statement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
    private ComboBox<String> boxCateSort;
    @FXML
    private ComboBox<String> boxStatusSort;
    @FXML
    private Button searchButton;
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
    private TableView<Order> menu_TbView;
    @FXML
    private TableColumn<Order, String> menu_Col_ProName;
    @FXML
    private TableColumn<Order, Integer> menu_Col_Quantity;
    @FXML
    private TableColumn<Order, Double> menu_Col_Price;
    @FXML
    private TableColumn<Order, String> menu_ColNote;
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
    @FXML
    private TextField menu_TextNote;
    @FXML
    private FontAwesomeIcon menu_btnPAY;
    @FXML
    private FontAwesomeIcon menu_btnRECEIPT;
    @FXML
    private Label menu_Discount;

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
    private Button clearallButton;
    @FXML
    private Text textNotice2;
    @FXML
    private Text nameFieldMsg;
    @FXML
    private Text phoneFieldMsg;
    @FXML
    private Text emailFieldMsg;
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
    private TextField invent_txtSearch;

    public Alert alert;
    private ProductDAO dao = new ProductDAO();
    private TextField menu_phoneInput;
    @FXML
    private TextField txt_menuTableNo;

    //bill fxml
//    @FXML
//    private Button searchButton;
    @FXML
    private Button bill_btn;
    @FXML
    private Button bill_mergeBillButton;
    @FXML
    private Button bill_splitBillButton;
    @FXML
    private Button bill_saveButton;
    @FXML
    private Button bill_payButton;
    @FXML
    private DatePicker bill_from;
    @FXML
    private DatePicker bill_to;
    @FXML
    private Button bill_searchDate;
    @FXML
    private Button bill_cancelButton1;
    @FXML
    private TextField menu_InputDiscount;
    @FXML
    private TextField menu_inputPhone;
    @FXML
    private TextField txt_menuNoGuest;

    //tb main
    @FXML
    private AnchorPane Bill_Anchor;
    @FXML
    private TableView<Bill> bill_tbView_main;
    @FXML
    private TableColumn<Bill, Integer> tc_bill_billId;
    @FXML
    private TableColumn<Bill, String> tc_bill_tableNo;
    @FXML
    private TableColumn<Bill, String> tc_bill_cusId;
    @FXML
    private TableColumn<Bill, String> tc_bill_cusName;
    @FXML
    private TableColumn<Bill, Double> tc_bill_billTotal;
    @FXML
    private TableColumn<Bill, Double> tc_bill_billTax;
    @FXML
    private TableColumn<Bill, Double> tc_bill_billService;
    @FXML
    private TableColumn<Bill, Double> tc_bill_billSub;
    @FXML
    private TableColumn<Bill, LocalDate> tc_bill_billDate;
    @FXML
    private TableColumn<Bill, String> tc_bill_billStatus;

    private BillDAO billDao = new BillDAO();

    //tb1
    @FXML
    private AnchorPane bill_view1;
    @FXML
    private TableView<Bill_Table1> bill_tbView_1;
    @FXML
    private TableColumn<Bill_Table1, String> tb1_tc_bill_proName;
    @FXML
    private TableColumn<Bill_Table1, Integer> tb1_tc_bill_quantity;
    @FXML
    private TableColumn<Bill_Table1, Float> tb1_tc_bill_price;
    @FXML
    private Label bill_Discount1;
    @FXML
    private Label bill_Tax1;
    @FXML
    private Label bill_Service1;
    @FXML
    private Label bill_Subtotal1;
    @FXML
    private Label bill_cusName1;
    @FXML
    private Label bill_Total1;

    @FXML
    private TableView<Bill_Table2> bill_tbView_2;
    @FXML
    private TableColumn<Bill_Table2, String> tb2_tc_bill_proName;
    @FXML
    private TableColumn<Bill_Table2, Integer> tb2_tc_bill_quantity;
    @FXML
    private TableColumn<Bill_Table2, Float> tb2_tc_bill_price;
    @FXML
    private Label bill_Discount2;
    @FXML
    private Label bill_Tax2;
    @FXML
    private Label bill_Service2;
    @FXML
    private Label bill_Subtotal2;
    @FXML
    private Label bill_cusName2;
    @FXML
    private Label bill_Total2;
    @FXML
    private TextField bill_inputPhone1;
    @FXML
    private TextField bill_TableNo1;
    @FXML
    private TextField bill_InputDiscount1;
    @FXML
    private TextField bill_NoGuest1;
    @FXML
    private FontAwesomeIcon bill_buttonSphone1;
    @FXML
    private TextField bill_searchBillId;
    @FXML
    private Button bill_searchBillIdButton;
    @FXML
    private AnchorPane bill_view11;
    @FXML
    private TextField bill_inputPhone2;
    @FXML
    private FontAwesomeIcon bill_buttonSphone2;
    @FXML
    private TextField bill_TableNo2;
    @FXML
    private TextField bill_InputDiscount2;
    @FXML
    private TextField bill_NoGuest2;
    @FXML
    private ComboBox<?> bill_sortBillStatus;
    @FXML
    private DatePicker DashBoard_datefrom;
    @FXML
    private DatePicker DashBoard_dateto;
    @FXML
    private Button DashBoard_exportButton;
    @FXML
    private Label totalGuests;
    private TableView<Bill> DB_TableView;
    private TableColumn<Bill, Double> DB_Table_Total;
    private TableColumn<Bill, Double> DB_Table_discount;
    private TableColumn<Bill, Double> DB_Table_service;
    private TableColumn<Bill, Double> DB_Table_tax;
    private TableColumn<Bill, Double> DB_Table_subTotal;
    private TableColumn<Bill, Integer> DB_Table_billId;
    private TableColumn<Bill, Integer> DB_Table_guestNo;
    private TableColumn<Bill, String> DB_Table_status;
    private DashBoardDAO DBDao = new DashBoardDAO();
    @FXML
    private Label DB_sum_BillId;
    @FXML
    private Label DB_sum_GuestNo;
    @FXML
    private Label DB_sum_Total;
    @FXML
    private Label DB_sum_Discount;
    @FXML
    private Label DB_sum_Service;
    @FXML
    private Label DB_sum_SubTotal;
    @FXML
    private Label DB_sum_Tax;
    @FXML
    private FontAwesomeIcon menu_btn_SearchCus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        alert.setHeaderText(null);

        ShowUserName();
        //Dashboard
        connectDb = db.GetConnectDB();

        // inventory
//        productList = FXCollections.observableArrayList(dao.listDB());
//        filteredList = new FilteredList<>(productList, p -> true);
//        sortedList = new SortedList<>(filteredList);
//        sortedList.setComparator((p1, p2) -> p1.getProId().compareTo(p2.getProId()));
        categoryList = FXCollections.observableArrayList("food", "drink");
        statusList = FXCollections.observableArrayList("Available", "UnAvailable", "OutOfStock");

        boxName.setItems(categoryList);
        boxStatus.setItems(statusList);
        boxCateSort.setItems(categoryList);
        boxStatusSort.setItems(statusList);
        ShowProducts();
        //check
//        System.out.println("Category List: " + categoryList);
//        System.out.println("Status List: " + statusList);

        //Menu
        menu_Col_ProName.setCellValueFactory(new PropertyValueFactory<>("proName"));
        menu_Col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_Col_Price.setCellValueFactory(new PropertyValueFactory<>("proPrice"));
        menu_ColNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        menuShowOrderData();
        menuDisplayCard();
        menuDisplayTotal();
        // Đăng ký listener cho trường nhập liệu amount
        menu_txtAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            menu_DisplayChange();
        });
        menu_InputDiscount.textProperty().addListener((observable, oldValue, newValue) -> {
            menuDiscount();
        });

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

//        billList = FXCollections.observableArrayList(billDao.listDB());
//        filteredBillList = new FilteredList<>(billList, p -> true);
//        sortedBillList = new SortedList<>(filteredBillList);
        //sortedBillList.setComparator((p1, p2) -> p1.getBillId().compareTo(p2.getBillId()));     
        // Initialize DatePickers
        bill_from.setValue(LocalDate.now());
        bill_to.setValue(LocalDate.now());
        billShowData();
        bill_from.valueProperty().addListener((observable, oldValue, newValue) -> filterBillList());
        bill_to.valueProperty().addListener((observable, oldValue, newValue) -> filterBillList());
        //filterBillList();
        // Set up TableView
        bill_InputDiscount1.setEditable(true); // Make sure the TextField is editable

        bill_InputDiscount1.textProperty().addListener((observable, oldValue, newValue) -> {
            updateDiscountAndTotals(newValue);
        });
        bill_InputDiscount2.setEditable(true); // Make sure the TextField is editable

        bill_InputDiscount2.textProperty().addListener((observable, oldValue, newValue) -> {
            updateDiscountAndTotals2(newValue);
        });

//        DashBoard
        DashBoard_datefrom.setValue(LocalDate.now());
        DashBoard_dateto.setValue(LocalDate.now());
        // Add listeners to date pickers
        DashBoard_datefrom.valueProperty().addListener((observable, oldValue, newValue) -> DB_updateTable());
        DashBoard_dateto.valueProperty().addListener((observable, oldValue, newValue) -> DB_updateTable());
        //DB_updateTable();
        // Initial table view load

    }

    //ALERT
    public void AlertError(String error) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void AlertInfor(String infor) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information:");
        alert.setHeaderText(null);
        alert.setContentText(infor);
        alert.showAndWait();
    }

    public void AlertConfirm(String confirm) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notice:");
        alert.setHeaderText(null);
        alert.setContentText(confirm);
    }

// NAVBAR CONTROL
    public void ShowUserName() {
        String user = data.username;
        txt_DisPlayName.setText(user);
    }

    @FXML
    public void switchForm(ActionEvent event) {

        if (data.position.equals("Staff")) {
            dashboard_btn.setDisable(true);
            inventory_btn.setDisable(true);
            staff_btn.setDisable(true);
        } else {
            dashboard_btn.setDisable(false);
            inventory_btn.setDisable(false);
            staff_btn.setDisable(false);

        }
        if (event.getSource() == dashboard_btn) {
            DashBoard_Anchor.setVisible(true);
            Inventory_Anchor.setVisible(false);
            Menu_Anchor.setVisible(false);
            Customer_Anchor.setVisible(false);
            staff_Anchor.setVisible(false);
            Bill_Anchor.setVisible(false);
            if (connectDb != null) {
                getTotalData();
                ratioDay.setSelected(true);
                getDataByDay();
            } else {
                System.err.println("Database connection failed.");
            }

        } else if (event.getSource() == inventory_btn) {
            DashBoard_Anchor.setVisible(false);
            Inventory_Anchor.setVisible(true);
            Menu_Anchor.setVisible(false);
            Customer_Anchor.setVisible(false);
            staff_Anchor.setVisible(false);
            Bill_Anchor.setVisible(false);
            ShowProducts();

        } else if (event.getSource() == menu_btn) {
            DashBoard_Anchor.setVisible(false);
            Inventory_Anchor.setVisible(false);
            Menu_Anchor.setVisible(true);
            Customer_Anchor.setVisible(false);
            staff_Anchor.setVisible(false);
            Bill_Anchor.setVisible(false);
            menuDisplayCard();
            menuShowOrderData();
            menuDisplayTotal();

        } else if (event.getSource() == customer_btn) {
            DashBoard_Anchor.setVisible(false);
            Inventory_Anchor.setVisible(false);
            Menu_Anchor.setVisible(false);
            Customer_Anchor.setVisible(true);
            staff_Anchor.setVisible(false);
            Bill_Anchor.setVisible(false);

//            customersShowData();
        } else if (event.getSource() == staff_btn) {
            DashBoard_Anchor.setVisible(false);
            Inventory_Anchor.setVisible(false);
            Menu_Anchor.setVisible(false);
            Customer_Anchor.setVisible(false);
            staff_Anchor.setVisible(true);
            Bill_Anchor.setVisible(false);

//            customersShowData();
        } else if (event.getSource() == bill_btn) {
            DashBoard_Anchor.setVisible(false);
            Inventory_Anchor.setVisible(false);
            Menu_Anchor.setVisible(false);
            Customer_Anchor.setVisible(false);
            staff_Anchor.setVisible(false);
            Bill_Anchor.setVisible(true);

            refreshBillTableView();
            System.out.println("vị trí 4:" + data.position);
            if (data.position.equals("Staff")) {
                bill_splitBillButton.setDisable(true);
                bill_mergeBillButton.setDisable(true);;

            } else {
                bill_splitBillButton.setDisable(false);
                bill_mergeBillButton.setDisable(false);;

            }

            //billShowData();
            if (billList.isEmpty() || billList == null) {
                System.out.println("bill trong 2");
            }
            for (Bill b : billList) {
                System.out.println(b.toString());
            }
            if (sortedBillList.isEmpty() || sortedBillList == null) {
                System.out.println("sortedBillList trong 2");
            }
            System.out.println("moiw 2");
            for (Bill b : sortedBillList) {
                System.out.println(b.toString());
            }

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

    private void DB_updateTable() {
        String from = null;
        String to = null;

        if (DashBoard_datefrom.getValue() != null) {
            from = DashBoard_datefrom.getValue().toString();
        }
        if (DashBoard_dateto.getValue() != null) {
            to = DashBoard_dateto.getValue().toString();
        }

        DB_tableView(from, to);
    }

    private void DB_tableView(String from, String to) {
        billList = FXCollections.observableArrayList(DBDao.listDB(from, to));
        DB_Table_billId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        DB_Table_guestNo.setCellValueFactory(new PropertyValueFactory<>("guestNo"));
        DB_Table_Total.setCellValueFactory(new PropertyValueFactory<>("billTotal"));
        DB_Table_discount.setCellValueFactory(new PropertyValueFactory<>("billDiscount"));
        DB_Table_service.setCellValueFactory(new PropertyValueFactory<>("billService"));
        DB_Table_tax.setCellValueFactory(new PropertyValueFactory<>("billTax"));
        DB_Table_subTotal.setCellValueFactory(new PropertyValueFactory<>("billSubTotal"));
        DB_Table_status.setCellValueFactory(new PropertyValueFactory<>("billStatus"));

        // Calculate totals
        int billCount = 0;
        int totalGuestNo = 0;
        double totalBill = 0;
        double totalDiscount = 0;
        double totalService = 0;
        double totalTax = 0;
        double totalSubTotal = 0;

        for (Bill bill : billList) {
            billCount += 1;
            totalGuestNo += bill.getGuestNo();
            totalBill += bill.getBillTotal();
            totalDiscount += bill.getBillDiscount();
            totalService += bill.getBillService();
            totalTax += bill.getBillTax();
            totalSubTotal += bill.getBillSubTotal();
        }

        // Set the data to the table
        DB_TableView.setItems(billList);

        DB_sum_BillId.setText(String.valueOf(billCount));
        DB_sum_GuestNo.setText(String.valueOf(totalGuestNo));
        DB_sum_Total.setText(String.format("%.2f", totalBill));
        DB_sum_Discount.setText(String.format("%.2f", totalDiscount));
        DB_sum_Service.setText(String.format("%.2f", totalService));
        DB_sum_Tax.setText(String.format("%.2f", totalTax));
        DB_sum_SubTotal.setText(String.format("%.2f", totalSubTotal));

    }

    private void getTotalData() {
        try (java.sql.Statement statement = connectDb.createStatement()) {
            String getTotalSaleQuery = "SELECT COUNT(BillId) AS total_sale FROM Bill;";
            ResultSet queryResult1 = statement.executeQuery(getTotalSaleQuery);
            if (queryResult1.next()) {
                totalSale.setText(queryResult1.getString("total_sale"));
            }
            queryResult1.close();

            String getTotalCustomerQuery = "SELECT COUNT(cus_id) AS total_customer FROM tbCustomer;";
            ResultSet queryResult2 = statement.executeQuery(getTotalCustomerQuery);
            if (queryResult2.next()) {
                totalCustomer.setText(queryResult2.getString("total_customer"));
            }
            queryResult2.close();

            String getTotalRevenueQuery = "SELECT sum(billSubTotal) AS total_earn FROM Bill;";
            ResultSet queryResult3 = statement.executeQuery(getTotalRevenueQuery);
            if (queryResult3.next()) {
                totalRevenue.setText(queryResult3.getString("total_earn"));
            }
            queryResult3.close();
            String getTotalGuestQuery = "SELECT sum(guestNo) AS total_guest FROM Bill;";
            ResultSet queryResult4 = statement.executeQuery(getTotalGuestQuery);
            if (queryResult4.next()) {
                totalGuests.setText(queryResult4.getString("total_guest"));
            }
            queryResult4.close();
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
                String getSaleQuery = "select sum(billSubTotal) as sale from Bill where billDate LIKE '" + dateFormat1 + formattedPeriod + dateFormat2 + "';";
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
        productList = FXCollections.observableArrayList(dao.listDB());
        filteredList = new FilteredList<>(productList, p -> true);
        sortedList = new SortedList<>(filteredList);
        sortedList.setComparator((p1, p2) -> p1.getProId().compareTo(p2.getProId()));

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

//        tvProduct.setItems(sortedList);
//        tvProduct.refresh();
    }

    private String getCategoryName(int cateId) {
        String categoryName;
        switch (cateId) {
            case 1:
                categoryName = "food";
                break;
            case 2:
                categoryName = "drink";
                break;
            default:
                categoryName = "Unknown";
                break;
        }

        return categoryName;
    }

    private void clearFields() {
        txtProId.setDisable(false);
        txtProId.clear();
        txtProName.clear();
        txtStock.clear();
        txtProPrice.clear();
        boxName.setValue("Please choose Category option");
        boxStatus.setValue("Please choose Status option");
        textNotice.setText("");
        ImageView.setImage(null);
    }

    @FXML
    private void inven_ClearSort(ActionEvent event) {
        boxCateSort.setValue("Please choose Category option");
        boxStatusSort.setValue("Please choose Status option");
        invent_txtSearch.clear();
        filteredList.setPredicate(p -> true); // Reset to show all products
        textNotice.setText("Sorting and Search cleared.");
    }

    @FXML
    private void inven_ClearFields(ActionEvent event) {
        clearFields();
    }

    private boolean validateInput() {
        boolean isValid = true;
        errorMessage validate = new errorMessage();

        // Clear previous error message
        textNotice.setText(null);

        // Validate ProId
        if (txtProId.getText().trim().isEmpty()) {
            textNotice.setText("ProId " + validate.getErrorMsg1());
            isValid = false;
        } else if (!txtProId.getText().matches("P\\d+")) { // Check format P followed by numbers
            textNotice.setText("ProId must start with P followed by numbers.");
            isValid = false;
        } // Validate CategoryName
        else if (boxName == null || boxName.getSelectionModel().isEmpty()) {
            textNotice.setText("CateName " + validate.getErrorMsg1());
            isValid = false;
        } // Validate Status
        else if (boxStatus == null || boxStatus.getSelectionModel().isEmpty()) {
            textNotice.setText("Status " + validate.getErrorMsg1());
            isValid = false;
        } // Validate ProName
        else if (txtProName.getText().trim().isEmpty()) {
            textNotice.setText("ProName " + validate.getErrorMsg1());
            isValid = false;
        } // Validate ProPrice
        else if (txtProPrice.getText().trim().isEmpty()) {
            textNotice.setText("ProPrice " + validate.getErrorMsg1());
            isValid = false;
        } else if (!txtProPrice.getText().matches("\\d+(\\.\\d{1,2})?")) { // Allows for decimal prices
            textNotice.setText("ProPrice must be a positive number with up to two decimal places.");
            isValid = false;
        } // Validate Stock
        else if (txtStock.getText().trim().isEmpty()) {
            textNotice.setText("ProStock " + validate.getErrorMsg1());
            isValid = false;
        } else if (!txtStock.getText().matches("\\d+")) {
            textNotice.setText("ProStock must be number >= 0 ");
            isValid = false;
        } else {
            int stock = Integer.parseInt(txtStock.getText());
            // Check for negative stock
            if (stock <= 0) {
                // Ensure status is OutOfStock if stock is negative
                if (boxStatus.getSelectionModel().isEmpty() || !boxStatus.getValue().equals("OutOfStock")) {
                    textNotice.setText("If Stock <= 0, Status must be OutOfStock.");
                    isValid = false;
                }
            } else {
                // Check if status is set to OutOfStock if stock is not negative
                if (boxStatus.getSelectionModel().isEmpty() || boxStatus.getValue().equals("OutOfStock")) {
                    textNotice.setText("If Stock > 0 , Status must be Available or UnAvailable");
                    isValid = false;
                }
            }
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
            image = new Image(file.toURI().toString(), 200, 200, false, true);

            ImageView.setImage(image);
        }
    }
// Khi chọn sản phẩm, vô hiệu hóa trường proId để không thể chỉnh sửa

    @FXML
    private void inventory_proSlected(MouseEvent event) {
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
                image = new Image("file:" + path, 200, 200, false, true);

                ImageView.setImage(image);
            } else {
                ImageView.setImage(null);
            }

            // Vô hiệu hóa trường proId để không thể chỉnh sửa khi cập nhật
            txtProId.setDisable(true);
        }
    }

    private boolean isProductIdDuplicate(String proId) {
        for (Product product : productList) {
            if (product.getProId().equals(proId)) {
                return true; // ID đã tồn tại
            }
        }
        return false; // ID chưa tồn tại
    }

    // Khi thêm sản phẩm mới, kích hoạt lại trường proId
    // Khi thêm sản phẩm mới, kích hoạt lại trường proId
    @FXML
    private void inven_Add(ActionEvent event) {

        if (validateInput()) {
            // Vô hiệu hóa trường proId trước khi thêm sản phẩm mới
            txtProId.setDisable(false);

            String proId = txtProId.getText().trim();

            if (isProductIdDuplicate(proId)) {
                textNotice.setText("Product ID already exists.");
                return; // Ngừng thực hiện thêm sản phẩm
            }

            Product newProduct = new Product();
            int stock = Integer.parseInt(txtStock.getText());
            String status = (stock <= 0) ? "OutOfStock" : boxStatus.getValue();

            newProduct.setProId(proId);
            newProduct.setProName(txtProName.getText());
            newProduct.setStock(stock);
            newProduct.setProPrice(Double.parseDouble(txtProPrice.getText()));
            newProduct.setCateId(boxName.getSelectionModel().getSelectedIndex() + 1);
            newProduct.setStatus(status);

            if (data.path == null) {
                newProduct.setProImage(null);
            } else {
                String path = data.path.replace("\\", "\\\\");
                newProduct.setProImage(path);
            }
            newProduct.setProDate(new Date());

            if (dao.AddDB(newProduct) != null) {
                productList.add(newProduct);
                data.path = null;
                filteredList.setPredicate(p -> true); // Cập nhật tất cả các sản phẩm
                tvProduct.refresh();
                clearFields();
                showAlert(AlertType.INFORMATION, "Alert", "Mesage", "ADD successfully");
                //textNotice.setText("Product added successfully.");
            } else {
//                textNotice.setText("Error adding product to the database.");
                showAlert(AlertType.WARNING, "Alert", "Mesage", "Error adding product to the database.");
            }
        }
    }

    @FXML
    private void inven_Update(ActionEvent event) {
        if (proSelected != null && validateInput()) {
            int stock = Integer.parseInt(txtStock.getText());
            String status = (stock <= 0) ? "OutOfStock" : boxStatus.getValue();

            // Cập nhật thông tin sản phẩm đã chọn
            proSelected.setProName(txtProName.getText());
            proSelected.setStock(stock);
            proSelected.setProPrice(Double.parseDouble(txtProPrice.getText()));
            proSelected.setCateId(boxName.getSelectionModel().getSelectedIndex() + 1);
            proSelected.setStatus(status);

            // Chỉ cập nhật đường dẫn hình ảnh nếu có hình ảnh mới
            if (data.path != null) {
                String path = data.path.replace("\\", "\\\\");
                proSelected.setProImage(path);
            }

            proSelected.setProDate(new Date());

            // Cập nhật sản phẩm trong cơ sở dữ liệu
            if (dao.UpdateDB(proSelected)) {
                // Cập nhật danh sách sản phẩm
                tvProduct.refresh();
                clearFields();
                showAlert(AlertType.INFORMATION, "Alert", "Mesage", "UPDATE successfully");
                //textNotice.setText("Product updated successfully.");
            } else {
                //textNotice.setText("Error updating product in the database.");
                showAlert(AlertType.WARNING, "Alert", "Mesage", "Error updating product in the database.");
            }

            // Reset đường dẫn hình ảnh sau khi cập nhật
            data.path = null;
        } else {
            //textNotice.setText("Please select a product and ensure all fields are valid.");
            showAlert(AlertType.WARNING, "Alert", "Mesage", "Please select a product and ensure all fields are valid.");
        }
    }

    @FXML
    private void Inven_Delete(ActionEvent event) {
        if (showConfirmation("DELETE " + proSelected.getProId())) {
            if (proSelected != null) {

                dao.DeleteDB(proSelected.getProId());
                productList.remove(proSelected);
                filteredList.setPredicate(p -> true);
                tvProduct.refresh();
                clearFields();
                showAlert(AlertType.INFORMATION, "Alert", "Mesage", "DELETE " + proSelected.getProId() + " successfully");
                //textNotice.setText("Product deleted" + proSelected.getProId() + "successfully.");
            } else {
                showAlert(AlertType.WARNING, "Warning", "Mesage", "No product selected for deletion.");
                //textNotice.setText("No product selected for deletion.");
            }
        } else {
            //showAlert(AlertType.INFORMATION, "Alert", "Deletion Cancelled", "Deletion cancelled.");
            //textNotice.setText("Deletion canceled.");
        }
    }

    private boolean showConfirmation(String header) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to " + header);
//        alert.setContentText("Bạn có chắc muốn xóa không?");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonYes;
    }

@FXML
    private void inven_Search(ActionEvent event) {
        applySearchFilter();
    }

    @FXML
    private void inven_Sort(ActionEvent event) {
        applySearchAndSortFilters();
    }

    private void applySearchFilter() {
        String searchValue = invent_txtSearch.getText().toLowerCase().trim();

        filteredList.setPredicate(product
                -> searchValue.isEmpty() || product.getProName().toLowerCase().contains(searchValue)
        );

        tvProduct.setItems(filteredList);
        textNotice.setText("Search successfully applied.");
    }

    private void applySearchAndSortFilters() {
        String searchValue = invent_txtSearch.getText().toLowerCase().trim();
        String selectedCategory = boxCateSort.getValue();
        String selectedStatus = boxStatusSort.getValue();

        filteredList.setPredicate(product -> {
            boolean matchesSearch = searchValue.isEmpty() || product.getProName().toLowerCase().contains(searchValue);

            String categoryName = getCategoryName(product.getCateId());
            boolean matchesCategory = (selectedCategory == null) || categoryName.equals(selectedCategory);
            boolean matchesStatus = (selectedStatus == null) || product.getStatus().equals(selectedStatus);

            return matchesSearch && matchesCategory && matchesStatus;
        });

        tvProduct.setItems(filteredList);
        textNotice.setText("Search and sort successfully applied.");
    }
    
//    @FXML
//    private void inven_Sort(ActionEvent event) {
//        String selectedCategory = boxCateSort.getValue();
//        String selectedStatus = boxStatusSort.getValue();
//
//        filteredList.setPredicate(product -> {
//            String categoryName = getCategoryName(product.getCateId());
//            boolean matchesCategory = (selectedCategory == null) || categoryName.equals(selectedCategory);
//            boolean matchesStatus = (selectedStatus == null) || product.getStatus().equals(selectedStatus);
//
//            return matchesCategory && matchesStatus;
//        });
//
//        // Apply sortedList to TableView
//        tvProduct.setItems(sortedList);
//        textNotice.setText("Products sorted successfully.");
//    }
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
public OrderDAO orderDAO = new OrderDAO();
    public ObservableList<Order> menuOrderList = FXCollections.observableArrayList();
    public BillDAO billDAO = new BillDAO();

    public void menuShowOrderData() {
        menuOrderList.clear();
        menuOrderList.addAll(orderDAO.showOrderDB());
        if (!menuOrderList.isEmpty()) {
            menu_TbView.setItems(menuOrderList);
        } else {
            menu_TbView.getItems().clear();
        }
    }

    public String getCustomerId() {
        String cusPhoneSearching = menu_inputPhone.getText();
        List<Customer> cusList = CustomerDAO.searchByPhone(cusPhoneSearching);
        if (!cusList.isEmpty()) {
            Customer c = cusList.get(0);
            menu_inputPhone.setText("Name: " + c.getName() + "/Discount:" + c.getDiscount());
            data.customerId = c.getCus_id();
        } else {
            data.customerId = "New ";
            menu_inputPhone.setText("No Result");
        }
        System.out.println("Customer ID tu getcusid: " + data.customerId);
        return data.customerId;
    }

    //ProductItem use
    private String note;

    public String getNote() {
        note = menu_TextNote.getText();
        return note;
    }

    public void clearNote() {
        if (menu_TextNote != null) {
            menu_TextNote.clear();
        }
    }

    private double discountvalue = 0;

    public void menuDiscount() {
        String discountText = menu_InputDiscount.getText();
        if (discountText != null && !discountText.isEmpty()) {
            try {
                discountvalue = Double.parseDouble(discountText);
            } catch (NumberFormatException e) {
                discountvalue = 0;
            }
        } else {
            discountvalue = 0;
        }
        // Cập nhật tổng số tiền và hiển thị
        menuGetSubtotal();
        menuDisplayTotal();
    }

    private double subtotal = 0;
    private double amount = 0;
    private double change = 0;
    private double total = 0;
    private double billtax = 0;
    private double billservice = 0;
    private double discount = 0;

    public void menuGetSubtotal() {
        List<Order> menuSubList = orderDAO.getbillTotalDB();
        if (!menuSubList.isEmpty()) {
            Order c = menuSubList.get(0);
            total = c.getBillTotal();
            if (discountvalue != 0) {
                discount = (total) * (discountvalue / 100);
            } else {
                discount = 0;
            }
            billservice = total * data.service;
            billtax = (total + billservice) * data.tax;

            subtotal = total - discount + billtax + billservice;
        } else {
            total = 0.0;
            billtax = 0.0;
            billservice = 0.0;
            subtotal = 0.0;
            discount = 0.0;
        }
    }

    public void menuDisplayTotal() {
        menuGetSubtotal();
        menu_Total.setText("$ " + String.format("%,.2f", total));
menu_Tax.setText("$ " + String.format("%,.2f", billtax));
        menu_Service.setText("$ " + String.format("%,.2f", billservice));
        menu_Discount.setText("$ " + "-" + String.format("%,.2f", discount));
        if (subtotal != 0) {
            menu_Subtotal.setText("$ " + String.format("%,.2f", subtotal));
        } else {
            menu_Subtotal.setText("");
        }
    }

    @FXML
    private void menu_SearchCus(MouseEvent event) {
        if (event.getSource() == menu_btn_SearchCus) {
            if (!menu_inputPhone.getText().isEmpty()) {
                getCustomerId();
            } else {
                AlertError("Please insert phone number");
            }
        }
    }

    private void menu_DisplayChange() {
        try {
            String amountStr = menu_txtAmount.getText().trim();
            if (amountStr.isEmpty()) {
                return;
            }
            amountStr = amountStr.replaceAll("[^\\d.]", "");
            amount = Double.parseDouble(amountStr);
            if (amount >= subtotal) {
                change = amount - subtotal;
            } else {
                change = 0;
            }
        } catch (NumberFormatException e) {
            AlertError("Invalid amount entered. Please enter a valid number.");
        }
        menu_Change.setText(String.format("%,.2f", change) + "VND ");

    }
    public CustomerDAO customerDAO = new CustomerDAO();

    @FXML
    private void menu_Pay(MouseEvent event) throws SQLException {
        if (subtotal == 0 || menu_txtAmount.getText().isEmpty()) {
            AlertError("Subtotal or Amount cant be blank");
        } else {
            Connection cn = null;
            try {
                cn = new ConnectDB().GetConnectDB();
                cn.setAutoCommit(false); // Begin transaction
                menuGetSubtotal();
                String tableNo = "null";
                if (txt_menuTableNo.getText() != null) {
                    tableNo = txt_menuTableNo.getText();
                }
                int guestNo = 0;
                try {
                    if (txt_menuNoGuest.getText().isEmpty()) {
                        guestNo = 0;
                    } else {
                        guestNo = Integer.parseInt(txt_menuNoGuest.getText());

                    }
                } catch (NumberFormatException e) {
                    AlertError("Please enter a valid number for the number of guests.");
                }
                Bill bill = new Bill();
                bill.setTableNo(tableNo);
                bill.setGuestNo(guestNo);
                bill.setCus_id(data.customerId);
                bill.setUserName(data.username);
                bill.setBillTotal(total);
                bill.setBillTax(billtax);
                bill.setBillService(billservice);
                bill.setBillDiscount(discount);
                bill.setBillSubTotal(subtotal);
java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
                bill.setBillDate(sqlDate);
                bill.setBillStatus("PAID");
                int billId = billDAO.insertBillAndGetId(bill, cn);
                List<Order> orderList = orderDAO.showOrderDB();
                billDAO.insertOrderDetails(billId, orderList, cn);
                orderDAO.archiveAndDeleteOrders(cn);
                // Commit the transaction
                cn.commit();
                            menu_DisplayChange();
                menuShowOrderData();
                menuRestart();
                AlertInfor("Paid successful");

            } catch (SQLException ex) {
                if (cn != null) {
                    try {
                        cn.rollback();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                ex.printStackTrace();
            } finally {
                if (cn != null) {
                    try {
                        cn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

    }

    @FXML
    private void menu_StoreBill(MouseEvent event) throws SQLException {
        Connection cn = null;
        try {
            cn = new ConnectDB().GetConnectDB();
            cn.setAutoCommit(false); // Begin transaction
            menuGetSubtotal();
            String tableNo = "null";
            if (txt_menuTableNo.getText() != null) {
                tableNo = txt_menuTableNo.getText();
            }
            int guestNo = 0;
            try {
                if (txt_menuNoGuest.getText().isEmpty()) {
                    guestNo = 0;
                } else {
                    guestNo = Integer.parseInt(txt_menuNoGuest.getText());

                }
            } catch (NumberFormatException e) {
                AlertError("Please enter a valid number");
            }
            Bill bill = new Bill();
            bill.setTableNo(tableNo);
            bill.setGuestNo(guestNo);
            bill.setCus_id(data.customerId);
            bill.setUserName(data.username);
            bill.setBillTotal(total);
            bill.setBillTax(billtax);
            bill.setBillService(billservice);
            bill.setBillDiscount(discount);
            bill.setBillSubTotal(subtotal);
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            bill.setBillDate(sqlDate);
            bill.setBillStatus("UNPAID");
            // Insert bill and get the generated bill ID
            int billId = billDAO.insertBillAndGetId(bill, cn);
            List<Order> orderList = orderDAO.showOrderDB();
            billDAO.insertOrderDetails(billId, orderList, cn);
            orderDAO.archiveAndDeleteOrders(cn);
            cn.commit();
            menuShowOrderData();
            menuRestart();
AlertInfor("Save successful");

        } catch (SQLException ex) {
            if (cn != null) {
                try {
                    cn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void menu_clear(ActionEvent event) {
        menu_inputPhone.clear();
        menu_TextNote.clear();
        data.customerId = "New ";
//        getCustomerId();
    }

    @FXML
    private void menu_ClearBillInput(ActionEvent event) {
        txt_menuTableNo.clear();
        menu_InputDiscount.clear();
        txt_menuNoGuest.clear();
        discountvalue = 0;
        menuGetSubtotal();
        menuDisplayTotal();
    }

    public void menuRestart() {
          menu_txtAmount.clear();
          menu_Change.setText("0.0");
        total = 0;
        billtax = 0;
        billservice = 0;
        subtotal = 0;
        amount = 0;
        change = 0;
        discount = 0;
        
        txt_menuNoGuest.clear();
        txt_menuTableNo.clear();
        menu_inputPhone.clear();
        menu_InputDiscount.clear();
      
        
        menu_Total.setText("0.0");
        menu_Tax.setText(" 0.0");
        menu_Service.setText("0.0");
        menu_Discount.setText("0.0");
        menu_Subtotal.setText(" 0.0");
        
    }

    private String getProId;
    private int getQuantity;

    @FXML
    private void menu_productSelected(MouseEvent event) {
        Order proId = menu_TbView.getSelectionModel().getSelectedItem();
        int num = menu_TbView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        getProId = proId.getProId();
        getQuantity = proId.getQuantity();
    }

    @FXML
    private void menu_RemoveItem(MouseEvent event) {
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
                int quantity = menu_TbView.getSelectionModel().getSelectedItem().getQuantity();
                orderDAO.DeleteProIdDB(getProId);
                dao.UpdateStockDeleted(getProId, quantity);
                menuShowOrderData();
                menuDisplayCard();

            }
        }
    }
@FXML
    private void menu_Receipt(MouseEvent event) throws SQLException {}

//CUSTOMER
    //CUSTOMER
    private void loadData() {
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

    @FXML
    private void handleAddCustomer(ActionEvent event) {
        if (validateCustomerInput()) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            int discount = Integer.parseInt(discountField.getText());
            int deleted = Integer.parseInt(deletedField.getText());

            Customer newCustomer = new Customer(null, name, phone, email, discount, deleted);
            int inserted = CustomerDAO.insert(newCustomer);

            if (inserted > 0) {
                customerTable.getItems().add(newCustomer);
                clearAllFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer added successfully");
            }
        }
    }

    @FXML
    private void handleUpdateCustomer(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null && validateCustomerInput()) {
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
                clearAllFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer updated successfully");
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
                clearAllFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer deleted successfully");
            }
        }
    }

    @FXML
    private void handleSearchByPhone(ActionEvent event) {
        String phone = searchField.getText().trim();
        List<Customer> searchResults = CustomerDAO.searchByPhone(phone);

        if (searchResults.isEmpty()) {
            loadData();
        } else {
            customerTable.getItems().setAll(searchResults);
        }
    }

    @FXML
    private void handleClearAllCustomer(ActionEvent event) {
        clearAllFields();
    }

    private void clearAllFields() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        discountField.clear();
        deletedField.clear();

    }

    private boolean validateCustomerInput() {
        boolean isValid = true;

        if (nameField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Name is required");
            isValid = false;
        }

        String phone = phoneField.getText().trim();
        if (phone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Phone number is required");
            isValid = false;
        } else if (!phone.matches("\\d{10}")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Phone must be exactly 10 digits");
            isValid = false;
        }

        String email = emailField.getText().trim();
        if (!email.isEmpty() && !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Email is not valid");
            isValid = false;
        }

        return isValid;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    //STAFF
    static ObservableList<String> positionList;
    static ObservableList<String> questionList;

    public UserDAO udao = new UserDAO();
    public ObservableList<UserInfo> uList = FXCollections.observableArrayList();
    FilteredList<UserInfo> userfilteredList = new FilteredList<>(uList, p -> true);
    UserInfo userSelected = null;
    int userindexSelected = -1;

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
                userindexSelected = -1;
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
                Staff_textNotice.setText("UserId cant changed");
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
            StaffClear();
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
        userPosition.setValue("Please choose position");
        userQuestion.setValue("Please choose question");
        Staff_textNotice.setText("");

    }

    //Bill page
    // ===================================================================================================  BILL =======================================
//    private BillDAO billDao = new BillDAO();
    private ObservableList<Bill> billList;
    private FilteredList<Bill> filteredBillList;
    private SortedList<Bill> sortedBillList;
    private ObservableList<Bill_Table1> billtable1;
    private ObservableList<Bill_Table2> billtable2_merge;
    private Bill billSelected;
    private Bill_Table1 bill_table1_Selected;
    private int indexbillSelected;
    private int indexbill_table1Selected;
    private ArrayList<Bill_Table1> tlist1 = new ArrayList<Bill_Table1>();
    private ArrayList<Bill_Table2> tlist2 = new ArrayList<Bill_Table2>();

    public void billShowData() {
        billList = FXCollections.observableArrayList(billDao.listDB());
        filteredBillList = new FilteredList<>(billList, p -> true);
        sortedBillList = new SortedList<>(filteredBillList);

        // Initialize table columns
        tc_bill_billId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        tc_bill_tableNo.setCellValueFactory(new PropertyValueFactory<>("tableNo"));
        tc_bill_cusId.setCellValueFactory(new PropertyValueFactory<>("cus_id"));
        tc_bill_cusName.setCellValueFactory(cellData -> {
            Bill bill = cellData.getValue();
            return new SimpleStringProperty(bill.getCustomer().getName());
        });
        tc_bill_billTotal.setCellValueFactory(new PropertyValueFactory<>("billTotal"));
        tc_bill_billTax.setCellValueFactory(new PropertyValueFactory<>("billTax"));
        tc_bill_billService.setCellValueFactory(new PropertyValueFactory<>("billService"));
        tc_bill_billSub.setCellValueFactory(new PropertyValueFactory<>("billSubTotal"));
        tc_bill_billDate.setCellValueFactory(new PropertyValueFactory<>("billDate"));
        tc_bill_billStatus.setCellValueFactory(new PropertyValueFactory<>("billStatus"));

        bill_tbView_main.setItems(sortedBillList);
        sortedBillList.comparatorProperty().bind(bill_tbView_main.comparatorProperty());
    }

    @FXML
    private void Bill_HandleSplitButton(ActionEvent event) {
        if (bill_table1_Selected == null
                || bill_table1_Selected.getProId() == null
                || bill_table1_Selected.getProId().isEmpty()) {
            // Show an alert to the user
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Split Button");
            alert.setContentText("Please select a product on the bill located on the left side of the screen to split the bill.");
            alert.showAndWait();
            return; // Exit the method
        }

        boolean validQuantity = false;
        int quantity = 1; // Default value
        while (!validQuantity) {
            // Prompt user to enter quantity
            TextInputDialog dialog = new TextInputDialog(String.valueOf(quantity)); // Default value is the last valid quantity
            dialog.setTitle("Enter Quantity");
            dialog.setHeaderText("Quantity Input");
            dialog.setContentText("Please enter the quantity:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                try {
                    quantity = Integer.parseInt(result.get());

                    // Validate the entered quantity
                    if (quantity < 1 || quantity > bill_table1_Selected.getQuantity()) {
                        throw new NumberFormatException();
                    }

                    validQuantity = true; // Quantity is valid, exit the loop

                } catch (NumberFormatException e) {
                    // Show an alert if the entered quantity is not valid
                    Alert numberAlert = new Alert(AlertType.ERROR);
                    numberAlert.setTitle("Error");
                    numberAlert.setHeaderText("Invalid Quantity");
                    numberAlert.setContentText("The entered quantity must be a valid number between 1 and " + bill_table1_Selected.getQuantity() + ".");
                    numberAlert.showAndWait();
                }
            } else {
                return; // Exit the method if the dialog is canceled or empty
            }
        }

        // Proceed with the valid quantity
        bill_cancelButton1.setDisable(false);
        bill_tbView_main.setDisable(true);

        Bill_Table2 tb2 = new Bill_Table2();
        tb2.setProId(bill_table1_Selected.getProId());
        tb2.setProName(bill_table1_Selected.getProName());
        tb2.setCateId(bill_table1_Selected.getCateId());
        tb2.setQuantity(quantity);

        tb2.setProPrice(((bill_table1_Selected.getProPrice()) * quantity) / (bill_table1_Selected.getQuantity()));

        tb2.setCus_id(bill_table1_Selected.getCus_id());
        tb2.setCus_name(bill_table1_Selected.getCus_name());

        if (bill_table1_Selected.getQuantity() == 1 || quantity == bill_table1_Selected.getQuantity()) {

            billtable1.remove(indexbill_table1Selected);

        } else if (bill_table1_Selected.getQuantity() > 1 && quantity < bill_table1_Selected.getQuantity()) {

            bill_table1_Selected.setQuantity(bill_table1_Selected.getQuantity() - quantity);
            bill_table1_Selected.setProPrice((bill_table1_Selected.getQuantity() * bill_table1_Selected.getProPrice())
                    / (bill_table1_Selected.getQuantity() + quantity));
            // Update the data in bill_tbView_1
            bill_tbView_1.refresh();
        }
        tlist2.add(tb2);
        bill_showtable1_total();
        bill_showtable2_total();
        double total = 0.0;
        for (Bill_Table2 tb : tlist2) {

            total += tb.getProPrice();
            System.out.println("bill_sumTotal_2" + total);
        }
        for (Bill_Table2 tb : tlist2) {
            System.out.println(tb.toString());
        }
        for (Bill_Table1 tb : billtable1) {
            System.out.println(tb.toString());
        }
        showtable2();
    }

    @FXML
    private void Bill_HandleSelected_main(MouseEvent event) {
        billSelected = bill_tbView_main.getSelectionModel().getSelectedItem();
        indexbillSelected = bill_tbView_main.getSelectionModel().getSelectedIndex();
//        showtable1();
        Bill_Table1 tb = new Bill_Table1();

        if (billSelected != null) {
            System.out.println("Selected bill ID: " + billSelected.getBillId());
            if (!checkMerge) {

                bill_view1.setDisable(true);

                if (billtable1.get(0).getBillId() == billSelected.getBillId()) {
                    showAlert(AlertType.WARNING, "Warning", "Error", "Trùng Bill để gộp");

                } else if (billSelected.getBillStatus().equals("PAID")) {
                    showAlert(AlertType.WARNING, "Warning", "Error", "Không thể chọn Bill đã thanh toán");
                } else {
                    showtable2_merge();
                    bill_showtable2_total();
                    showConfirmationMerge();

                }

            } else {
                tlist2.clear();
                Bill_clearFields(bill_view11);
                showtable1();
//                updateBillDetails();
                if (billSelected.getBillStatus().equals("PAID")) {
                    bill_splitBillButton.setDisable(true);
                    bill_mergeBillButton.setDisable(true);
                    bill_saveButton.setDisable(true);
                    bill_payButton.setDisable(true);
                } else {
                    if (data.position.equals("Staff")) {

                        bill_splitBillButton.setDisable(true);
                        bill_mergeBillButton.setDisable(true);;
                        bill_saveButton.setDisable(false);
                        bill_cancelButton1.setDisable(true);
                        bill_payButton.setDisable(false);
                    } else {
                        bill_saveButton.setDisable(false);
                        bill_cancelButton1.setDisable(true);
                        bill_splitBillButton.setDisable(false);
                        bill_mergeBillButton.setDisable(false);;
                        bill_payButton.setDisable(false);

                    }
                }
                bill_showtable1_total();
                if (billtable1.isEmpty() || billtable1 == null) {
                    System.out.println("billtable1 null");
                }
                for (Bill_Table1 b : billtable1) {
                    System.out.println(b.toString());
                }

            }
        } else {
            System.out.println("No bill selected in the main TableView.");
        }

    }

    private void showAlert(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showConfirmationMerge() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Merge");
        alert.setContentText("Bạn có chắc muốn gộp không?");

        ButtonType buttonYes = new ButtonType("Có");
        ButtonType buttonNo = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        alert.showAndWait().ifPresent(type -> {
            if (type == buttonYes) {

                mergeBills();
            } else {
                System.out.println("Không thực hiện gộp");
            }
        });
    }

    private void mergeBills() {
        // In số lượng sản phẩm trước khi gộp
        System.out.println("Trước khi gộp:");
        for (Bill_Table2 item2 : billtable2_merge) {
            System.out.println("ID sản phẩm: " + item2.getProId() + ", Số lượng: " + item2.getQuantity());
        }
        // Duyệt qua các sản phẩm trong billtable1

        for (Bill_Table1 item1 : billtable1) {
            boolean found = false;

            // Duyệt qua các sản phẩm trong billtable2_merge
            for (Bill_Table2 item2 : billtable2_merge) {
                if (item1.getProId().equals(item2.getProId())) {
                    // Nếu sản phẩm đã tồn tại, cộng số lượng
                    item2.setQuantity(item2.getQuantity() + item1.getQuantity());
                    item2.setProPrice(item2.getProPrice() + item1.getProPrice());
                    found = true;
                    break;
                }
            }

            // Nếu sản phẩm không tồn tại, thêm mới vào billtable2_merge
            if (!found) {
                Bill_Table2 newItem = new Bill_Table2(
                        item1.getProId(),
                        item1.getProName(),
                        item1.getCateId(),
                        item1.getQuantity(),
                        item1.getProPrice(),
                        item1.getStatus(),
                        item1.getProImage(),
                        item1.getBillTotal(),
                        item1.getBillTax(),
                        item1.getBillService(),
                        item1.getBillDiscount(),
                        item1.getBillSubTotal(),
                        item1.getCus_id(),
                        item1.getCus_name()
                );
                billtable2_merge.add(newItem);
            }
        }

        bill_tbView_2.setItems(FXCollections.observableArrayList(billtable2_merge));
        bill_tbView_2.refresh();
        bill_showtable2_total();
// In số lượng sản phẩm sau khi gộp
//    System.out.println("Sau khi gộp:");
//   for (Bill_Table2 item2 : billtable2_merge) {
//        System.out.println("ID sản phẩm: " + item2.getProId() + ", Số lượng: " + item2.getQuantity());
//   }
//   in tat ca san pham sau khi gop
//    for (Bill_Table2 item2 : billtable2_merge) {
//        System.out.println(item2);
//    }

    }

    private double bill_sumTotal_1() {

        double total = 0.0;
        for (Bill_Table1 bill : billtable1) {
            total += bill.getProPrice();
        }
        return total;
    }

    private void updateDiscountAndTotals(String discountText) {
        double discountPercentage;
        try {
            discountPercentage = Double.parseDouble(discountText);
        } catch (NumberFormatException e) {
            discountPercentage = 0;
        }
        double total = bill_sumTotal_1();
        double discountAmount = total * discountPercentage / 100;
        bill_Discount1.setText("- $ " + String.format("%.2f", discountAmount));

        double serviceAmount = (total - discountAmount) * 0.05;
        bill_Service1.setText("$ " + String.format("%.2f", serviceAmount));

        double taxAmount = (total - discountAmount + serviceAmount) * 0.08;
        bill_Tax1.setText("$ " + String.format("%.2f", taxAmount));

        double subtotal = total - discountAmount + serviceAmount + taxAmount;
        bill_Subtotal1.setText("$ " + String.format("%.2f", subtotal));
    }

// Initial call to set values on load
    private void bill_showtable1_total() {

        bill_Total1.setText("$ " + String.format("%.2f", bill_sumTotal_1()));
        double discountPercentage1 = (billSelected.getBillDiscount() * 100) / billSelected.getBillTotal();
        bill_InputDiscount1.setText(String.format("%.2f", discountPercentage1));
        updateDiscountAndTotals(bill_InputDiscount1.getText()); // Call the method to update values

        bill_cusName1.setText(billSelected.getCustomer().getName() + " " + billSelected.getCustomer().getPhone());
        bill_TableNo1.setText(billSelected.getTableNo());
        bill_NoGuest1.setText(String.valueOf(billSelected.getGuestNo()));
        bill_inputPhone1.setText(String.valueOf(billSelected.getCustomer().getPhone()));
    }

    private double bill_sumTotal_2() {

        double total = 0.0;
        if (!tlist2.isEmpty() || billtable2_merge == null) {
            for (Bill_Table2 bill : tlist2) {
                total += bill.getProPrice();
                System.out.println("tlist2: " + bill);
            }

        } else if (!billtable2_merge.isEmpty()) {
            for (Bill_Table2 bill : billtable2_merge) {
                total += bill.getProPrice();

            }
        } else {
            System.out.println(" lỗi lllll");

        }
        return total;
    }

    private void updateDiscountAndTotals2(String discountText) {
        double discountPercentage;
        try {
            discountPercentage = Double.parseDouble(discountText);
        } catch (NumberFormatException e) {
            discountPercentage = 0;
        }
        double total = bill_sumTotal_2();
        double discountAmount = total * discountPercentage / 100;
        bill_Discount2.setText("- $ " + String.format("%.2f", discountAmount));

        double serviceAmount = (total - discountAmount) * 0.05;
        bill_Service2.setText("$ " + String.format("%.2f", serviceAmount));

        double taxAmount = (total - discountAmount + serviceAmount) * 0.08;
        bill_Tax2.setText("$ " + String.format("%.2f", taxAmount));

        double subtotal = total - discountAmount + serviceAmount + taxAmount;
        bill_Subtotal2.setText("$ " + String.format("%.2f", subtotal));
    }

// Initial call to set values on load
    private void bill_showtable2_total() {

        bill_Total2.setText("$ " + String.format("%.2f", bill_sumTotal_2()));
        System.out.println("bill_sumTotal_2: " + bill_sumTotal_2());
        double discountPercentage1 = (billSelected.getBillDiscount() * 100) / billSelected.getBillTotal();
        bill_InputDiscount2.setText(String.format("%.2f", discountPercentage1));
        updateDiscountAndTotals2(bill_InputDiscount2.getText()); // Call the method to update values

        bill_cusName2.setText(billSelected.getCustomer().getName() + " " + billSelected.getCustomer().getPhone());
        bill_TableNo2.setText("0");
        bill_NoGuest2.setText("0");
        bill_inputPhone2.setText(String.valueOf(billSelected.getCustomer().getPhone()));
    }

    private void updateBillDetails() {
        if (billSelected != null) {
            bill_Total1.setText("$ " + billSelected.getBillTotal());
            bill_Discount1.setText(billSelected.getCustomer().getDiscount() + " %");
            bill_Tax1.setText("$ " + billSelected.getBillTax());
            bill_Service1.setText("$ " + billSelected.getBillService());
            bill_Subtotal1.setText("$ " + billSelected.getBillSubTotal());
            bill_cusName1.setText(billSelected.getCustomer().getName() + " " + billSelected.getCustomer().getPhone());
        } else {
            System.out.println("No bill selected for detail update.");
        }
    }

    private void updateBillDetails2() {
        if (billSelected != null) {
            bill_Total2.setText("$ " + billSelected.getBillTotal());
            bill_Discount2.setText(billSelected.getCustomer().getDiscount() + " %");
            bill_Tax2.setText("$ " + billSelected.getBillTax());
            bill_Service2.setText("$ " + billSelected.getBillService());
            bill_Subtotal2.setText("$ " + billSelected.getBillSubTotal());
            bill_cusName2.setText(billSelected.getCustomer().getName() + " " + billSelected.getCustomer().getPhone());
        } else {
            System.out.println("No bill selected for detail update.");
        }
    }

    private void showtable1() {
        billtable1 = FXCollections.observableArrayList(billDao.Addtable1(billSelected.getBillId()));
        tb1_tc_bill_proName.setCellValueFactory(new PropertyValueFactory<>("proName"));
        tb1_tc_bill_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tb1_tc_bill_price.setCellValueFactory(new PropertyValueFactory<>("proPrice"));

        // Set the data to the table
        bill_tbView_1.setItems(billtable1);
    }

    private void showtable2() {
        //billtable2 = FXCollections.observableArrayList(billDao.Addtable1(billSelected.getBillId()));
        tb2_tc_bill_proName.setCellValueFactory(new PropertyValueFactory<>("proName"));
        tb2_tc_bill_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tb2_tc_bill_price.setCellValueFactory(new PropertyValueFactory<>("proPrice"));

        // Set the data to the table
        ObservableList<Bill_Table2> observableList2 = FXCollections.observableArrayList(tlist2);
        bill_tbView_2.setItems(observableList2);
    }
// Display merged data in TableView2

    private void showtable2_merge() {
        billtable2_merge = FXCollections.observableArrayList(billDao.Addtable2(billSelected.getBillId()));
        tb2_tc_bill_proName.setCellValueFactory(new PropertyValueFactory<>("proName"));
        tb2_tc_bill_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tb2_tc_bill_price.setCellValueFactory(new PropertyValueFactory<>("proPrice"));
        System.out.println("Merged Table2 data:");
        bill_tbView_2.setItems(billtable2_merge);
    }

    @FXML
    private void Bill_HandleSelected_table1(MouseEvent event) {
        bill_table1_Selected = bill_tbView_1.getSelectionModel().getSelectedItem();
        indexbill_table1Selected = bill_tbView_1.getSelectionModel().getSelectedIndex();
    }

    private void filterBillList() {
        LocalDate fromDate = bill_from.getValue();
        LocalDate toDate = bill_to.getValue();

        filteredBillList.setPredicate(bill -> {
            LocalDate billLocalDate = bill.getBillDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (fromDate == null && toDate == null) {
                // Show all bills if both dates are not set
                return true;
            } else if (fromDate == null) {
                // Show all bills up to toDate if fromDate is not set
                return !billLocalDate.isAfter(toDate);
            } else if (toDate == null) {
                // Show all bills from fromDate if toDate is not set
                return !billLocalDate.isBefore(fromDate);
            } else {
                // Show bills within the range if both dates are set
                return (billLocalDate.isEqual(fromDate) || billLocalDate.isAfter(fromDate))
                        && (billLocalDate.isEqual(toDate) || billLocalDate.isBefore(toDate));
            }
        });
    }

    @FXML
    private void Bill_HandleCancelButton(ActionEvent event) {
        // Clear data from tb2
        tlist2.clear();
        // Refresh bill_tbView_2
        bill_tbView_2.setItems(FXCollections.observableArrayList(tlist2));
        bill_tbView_2.refresh();

        // Re-enable bill_tbView_main
        bill_tbView_main.setDisable(false);
        bill_view1.setDisable(false);
        checkMerge = true;
        // Update bill_tbView_1 using the showtable1() method
        showtable1();
        bill_showtable1_total();
        Bill_clearFields(bill_view11);
        // de clear luc merge ,ko có thì sẽ click cancel 2 lan moi đc
        tlist2.clear();
    }

    @FXML
    private void Bill_HandlePaylButton(ActionEvent event) {
        // Create a custom dialog for payment input
        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Enter Payment Amount");
        dialog.setHeaderText("Payment Input");

        // Set up the dialog content
        VBox vbox = new VBox();
        TextField paymentField = new TextField("0.0"); // Default value is 0.0
        paymentField.setPromptText("Enter the amount given by the customer");
        Label changeLabel = new Label();

        // Update the change label when the text changes
        paymentField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                double paymentAmount = Double.parseDouble(newText);
                double billSubTotal = billSelected.getBillSubTotal();
                if (paymentAmount >= billSubTotal) {
                    double change = paymentAmount - billSubTotal;
                    changeLabel.setText("Change: $" + String.format("%.2f", change));
                } else {
                    changeLabel.setText("Change: Not enough payment.");
                }
            } catch (NumberFormatException e) {
                changeLabel.setText("Change: Invalid amount.");
            }
        });

        vbox.getChildren().addAll(paymentField, changeLabel);
        dialog.getDialogPane().setContent(vbox);

        // Add OK and Cancel buttons
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Convert the result to a double and handle it
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    return Double.parseDouble(paymentField.getText());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        // Show the dialog and process the result
        Optional<Double> result = dialog.showAndWait();
        if (result.isPresent()) {
            double paymentAmount = result.get();
            double billSubTotal = billSelected.getBillSubTotal();

            if (paymentAmount < billSubTotal) {
                // Show an alert if the payment amount is less than the total bill
                Alert insufficientAlert = new Alert(AlertType.WARNING);
                insufficientAlert.setTitle("Warning");
                insufficientAlert.setHeaderText("Insufficient Payment");
                insufficientAlert.setContentText("The payment amount must be greater than or equal to the total bill amount.\n"
                        + "Amount Given: $" + String.format("%.2f", paymentAmount) + "\n"
                        + "Amount Due: $" + String.format("%.2f", billSubTotal));
                insufficientAlert.showAndWait();
            } else {
                // Calculate the change
                double change = paymentAmount - billSubTotal;

                // Show the result in an alert
                Alert successAlert = new Alert(AlertType.CONFIRMATION);
                successAlert.setTitle("Payment Information");
                successAlert.setHeaderText("Payment Processed");
                successAlert.setContentText("Payment successful!\n"
                        + "Amount Given: $" + String.format("%.2f", paymentAmount) + "\n"
                        + "Change to be Returned: $" + String.format("%.2f", change) + "\n"
                        + "Do you want to update the bill status?");
                Optional<ButtonType> confirmationResult = successAlert.showAndWait();
                if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                    // Update the bill status and refresh the table
                    billDao.UpdateBillStatus(billSelected);

//                billList = FXCollections.observableArrayList(billDao.listDB());
//                filteredBillList = new FilteredList<>(billList, p -> true);
//                sortedBillList = new SortedList<>(filteredBillList);
//                billShowData();
                }
            }
        } else {
            // Handle the case where the user cancels the dialog
            Alert cancelAlert = new Alert(AlertType.INFORMATION);
            cancelAlert.setTitle("Cancelled");
            cancelAlert.setHeaderText("Payment Cancelled");
            cancelAlert.setContentText("The payment process was cancelled.");
            cancelAlert.showAndWait();
        }
    }

    public void refreshBillTableView() {
        // Clear the existing data
        //billList.clear();
        //sortedBillList.clear();
        // Reload the data from the database and add it to the billList
        //billList.addAll(billDao.listDB());
        billList = FXCollections.observableArrayList(billDao.listDB());
        filteredBillList = new FilteredList<>(billList, p -> true);
        sortedBillList = new SortedList<>(filteredBillList);

        // Refresh the table view
        bill_tbView_main.refresh();
        billShowData();
    }

    public void Bill_clearFields(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();  // Clear text from TextField
            } else if (node instanceof Label) {
                Label label = (Label) node;
                if (label.getId() != null && !label.getId().isEmpty()) {
                    label.setText("0");  // Clear text from Label if it has an ID
                }
            } else if (node instanceof TableView) {
                ((TableView<?>) node).getItems().clear();  // Clear items from TableView
            } else if (node instanceof Parent) {
                Bill_clearFields((Parent) node);  // Recursive call for nested containers
            }
        }
    }
//============================================================================================================     MERGE       ============================================
    // Handle Merge Button Action
    boolean checkMerge = true;

    @FXML
    private void Bill_HandleMergeButton(ActionEvent event) {
        bill_saveButton.setDisable(false);
        bill_cancelButton1.setDisable(false);
        checkMerge = false;
    }

    @FXML
    private void Bill_HandleSaveButton(ActionEvent event) {
        if (tlist2.isEmpty() && billtable2_merge.isEmpty()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Save Bill");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to save?");

            // Adding Yes and No buttons
            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonYes, buttonNo);

            // Waiting for the user to respond
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                Bill_updaeteBill();
                Bill_clearFields(bill_view1);
                Bill_clearFields(bill_view11);
            }
            // No need to handle the No case explicitly as we do nothing
        } else if (!tlist2.isEmpty() || billtable2_merge.isEmpty()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Save Bill");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to split bill?");

            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonYes, buttonNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                Bill_updaeteBill();
                Bill_updateOrderArchive();
                Bill_addnewBill();
                Bill_addnewOrderArchive();

                tlist2.clear();
                Bill_clearFields(bill_view1);
                Bill_clearFields(bill_view11);
                bill_tbView_main.setDisable(false);
            }
        } else if (!billtable2_merge.isEmpty() || tlist2.isEmpty()) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Save Bill");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to merge bill?");

            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonYes, buttonNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                 Bill_updateOrderArchiveMerge();
                System.out.println("đã merge");
              
            }
        }
    }

    private void Bill_updateOrderArchive() {
        try {
            // Get all proId from OrderArchive for the given billId
            List<String> orderArchiveProIds = billDao.getProIdsForBill(billSelected.getBillId());
            System.out.println("danh sach id:" + orderArchiveProIds);
            // Iterate over tlist1 and update existing proIds in the list
            for (int i = 0; i < orderArchiveProIds.size(); i++) {
                orderArchiveProIds.get(i);

            }
            if (orderArchiveProIds.size() == billtable1.size()) {
                for (Bill_Table1 pro : billtable1) {
                    if (orderArchiveProIds.contains(pro.getProId())) {
                        // Update the record if proId exists in orderArchive
                        billDao.UpdateOrderArchiveSplit1(pro);
                        // Remove the proId from the list to avoid deletion later
                        //orderArchiveProIds.remove(pro.getProId());
                    }
                }
            } else {

                for (String proIdToDelete : orderArchiveProIds) {
                    boolean a = true; // Initialize a as true for each proIdToDelete
                    System.out.println(proIdToDelete);
                    for (Bill_Table1 pro : billtable1) {
                        System.out.println(pro.getProId() + " và " + proIdToDelete);
                        if (pro.getProId().equals(proIdToDelete)) {
                            a = false; // Set a to false if a match is found
                            System.out.println(a + " Không xóa và update: " + proIdToDelete);
                            billDao.UpdateOrderArchiveSplit1(pro);
                            break; // Exit the inner loop early since a match was found
                        }
                    }
                    if (a == true) {
                        System.out.println(a + " xóa:" + proIdToDelete);
                        billDao.deleteProIdFromBill(billSelected.getBillId(), proIdToDelete); // Uncomment and use if needed
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Bill_addnewBill() {
        Bill b = new Bill();
        //b.setBillId(billSelected.getBillId());
        b.setUserName(data.username);
        b.setBillTotal(Double.parseDouble(bill_Total2.getText().replace("$", "")));
        b.setBillDiscount(Double.parseDouble(bill_Discount2.getText().replace("- $", "")));
        b.setBillTax(Double.parseDouble(bill_Tax2.getText().replace("$", "")));
        b.setBillService(Double.parseDouble(bill_Service2.getText().replace("$", "")));
        b.setBillSubTotal(Double.parseDouble(bill_Subtotal2.getText().replace("$", "")));
        b.setTableNo(String.valueOf(bill_TableNo2.getText()));
        b.setGuestNo(Integer.parseInt(bill_NoGuest2.getText()));
        b.setBillStatus("UNPAID");
        if (data.customerId == null || data.customerId.equals("")) {
            if (billSelected != null) {
                b.setCus_id(billSelected.getCus_id());
            } else {
                // Handle the case where billSelected or billSelected.getCus_id() is null
                System.out.println("billSelected or billSelected.getCus_id() is null");
            }
        } else {
            b.setCus_id(data.customerId);
        }
        billDao.AddBillSlit(b);

        refreshBillTableView();
    }

    private void Bill_addnewOrderArchive() {
        for (Bill_Table2 pro : tlist2) {
            billDao.AddBillOrderArchive(pro);
        }
    }

    private void Bill_updaeteBill() {

        if (!bill_TableNo1.equals(billSelected.getTableNo())
                || !bill_NoGuest1.equals(billSelected.getGuestNo())
                || //                !bill_InputDiscount1.equals(billSelected.getBillDiscount()) ||
                !bill_inputPhone1.equals(billSelected.getCustomer().getPhone())) {
            bill_saveButton.setDisable(false);
            Bill b = new Bill();
            b.setBillId(billSelected.getBillId());
            b.setUserName(data.username);
            b.setBillTotal(Double.parseDouble(bill_Total1.getText().replace("$", "")));
            b.setBillDiscount(Double.parseDouble(bill_Discount1.getText().replace("- $", "")));
            b.setBillTax(Double.parseDouble(bill_Tax1.getText().replace("$", "")));
            b.setBillService(Double.parseDouble(bill_Service1.getText().replace("$", "")));
            b.setBillSubTotal(Double.parseDouble(bill_Subtotal1.getText().replace("$", "")));
            b.setTableNo(String.valueOf(bill_TableNo1.getText()));
            b.setGuestNo(Integer.parseInt(bill_NoGuest1.getText()));
            if (data.customerId == null || data.customerId.equals("")) {
                if (billSelected != null) {
                    b.setCus_id(billSelected.getCus_id());
                } else {
                    // Handle the case where billSelected or billSelected.getCus_id() is null
                    System.out.println("billSelected or billSelected.getCus_id() is null");
                }
            } else {
                b.setCus_id(data.customerId);
            }
            billDao.UpdateBillSplit(b);

            refreshBillTableView();

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Save Button");
            alert.setContentText("no changes need to be saved.");
            alert.showAndWait();
            return; // Exit the method
        }
    }

    @FXML
    private void bill_handleSearchBillId(ActionEvent event) {
    }

    @FXML
    private void bill_handleSphone1(MouseEvent event) {
        if (!bill_inputPhone1.getText().isEmpty()) {
            bill_getCustomerId();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setContentText("Please insert phone number");
            alert.showAndWait();
        }
    }

    public String bill_getCustomerId() {
        String cusPhoneSearching = bill_inputPhone1.getText();
        List<Customer> cusList = CustomerDAO.searchByPhone(cusPhoneSearching);
        if (!cusList.isEmpty()) {
            Customer c = cusList.get(0);
            bill_cusName1.setText(c.getName() + " " + c.getPhone());
            data.customerId = c.getCus_id();
        } else {
            data.customerId = "New ";
            bill_cusName1.setText("No Result");
        }
        System.out.println("Customer ID tu getcusid: " + data.customerId);
        return data.customerId;
    }

    public String bill_getCustomerId2() {
        String cusPhoneSearching = bill_inputPhone2.getText();
        List<Customer> cusList = CustomerDAO.searchByPhone(cusPhoneSearching);
        if (!cusList.isEmpty()) {
            Customer c = cusList.get(0);
            bill_cusName2.setText(c.getName() + " " + c.getPhone());
            data.customerId = c.getCus_id();
        } else {
            data.customerId = "New ";
            bill_cusName2.setText("No Result");
        }
        System.out.println("Customer ID tu getcusid: " + data.customerId);
        return data.customerId;
    }

    @FXML
    private void bill_handleSphone2(MouseEvent event) {
        if (!bill_inputPhone2.getText().isEmpty()) {
            bill_getCustomerId2();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setContentText("Please insert phone number");
            alert.showAndWait();
        }
    }

    @FXML
    private void menu_SearchCus_id(MouseEvent event) {
    }

    private void Bill_updateOrderArchiveMerge() {
        for (Bill_Table2 itemNew : billtable2_merge) {
                       System.out.println("san pham trong billtable2_merge: "+ itemNew);          
          }     
        List<String> orderArchiveProIds = billDao.getProIdsForBill(billSelected.getBillId());
        System.out.println("danh sach proId trong orderArchive duoi DB theo BillId vừa selected:" + orderArchiveProIds);
        
        System.out.println("size orderArchiveProIds:" +orderArchiveProIds.size() );
        System.out.println("size billtable2_merge:" +billtable2_merge.size() );      
            for(Bill_Table2 pro : billtable2_merge){
                //gán billId vừa được selected cho phần tử trong billtable2_merge 
                pro.setBillId(billSelected.getBillId());
                pro.setProPrice(pro.getProPrice()/pro.getQuantity());
                //nếu orderArchive có proId trùng proId với phần tử trong billtable2_merge 
                if(orderArchiveProIds.contains(pro.getProId())){                  
                    billDao.UpdateOrderArchiveMerge(pro);
                    System.out.println("Đã cập nhật sản phẩm trong orderArchive: " + pro);                  
                }else if(!orderArchiveProIds.contains(pro.getProId())){
                    billDao.AddOrderArchiveMerge(pro);
                }
            }
            System.out.println("bill được chọn đầu tiên : "+ billtable1.get(0).getBillId());
            billDao.DeleteDB(billtable1.get(0).getBillId());
            billDao.DeleteDB_tableBill(billtable1.get(0).getBillId());
             tlist2.clear();
        // Refresh bill_tbView_2
             bill_tbView_2.setItems(FXCollections.observableArrayList(tlist2));
             bill_tbView_2.refresh();
        // Re-enable bill_tbView_main
            bill_tbView_main.setDisable(false);
            bill_view1.setDisable(false);
            checkMerge = true;
        // Update bill_tbView_1 using the showtable1() method
            showtable1();
             bill_showtable1_total();
            Bill_clearFields(bill_view11);
        // de clear luc merge ,ko có thì sẽ click cancel 2 lan moi đc
             tlist2.clear();
//            billDao.DeleteDB(billSelected.getBillId());
            bill_tbView_main.refresh();
            refreshBillTableView();     
        }
    
}
