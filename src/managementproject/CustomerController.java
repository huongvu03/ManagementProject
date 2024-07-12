



package managementproject;

import Database.CustomerDAO;
import Models.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;

public class CustomerController implements Initializable {

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
     private ObservableList<Customer> customerList;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    }

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

    @FXML
    private void handleAddCustomer() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        int discount = Integer.parseInt(discountField.getText());
        int deleted = Integer.parseInt(deletedField.getText());

        Customer newCustomer = new Customer(null, name, phone, email, discount, deleted);
        int inserted = CustomerDAO.insert(newCustomer);
        if (inserted > 0) {
            customerTable.getItems().add(newCustomer);
            clearFields();
        }
    }

    @FXML
    private void handleUpdateCustomer() {
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
    private void handleDeleteCustomer() {
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

    private void handleSearchByPhone() {
        String phone = searchField.getText();
          List<Customer> searchResults = CustomerDAO.searchByPhone(phone);
    
           if (searchResults.isEmpty()) { 
             loadData();
              } else {
                customerTable.getItems().setAll(searchResults);
            }
      }



    private void clearFields() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        discountField.clear();
        deletedField.clear();
    }
    
}
