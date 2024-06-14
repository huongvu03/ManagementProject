/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package managementproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 *
 * @author PC-User
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private AnchorPane mainform;
    @FXML
    private TableView<?> tvProduct;
    @FXML
    private TableColumn<?, ?> tcProId;
    @FXML
    private TableColumn<?, ?> tcProName;
    @FXML
    private TableColumn<?, ?> tcCateName;
    @FXML
    private TableColumn<?, ?> tcStock;
    @FXML
    private TableColumn<?, ?> tcProPrice;
    @FXML
    private TableColumn<?, ?> tcStatus;
    @FXML
    private TableColumn<?, ?> tcDate;
    @FXML
    private TextField txtProId;
    @FXML
    private TextField txtProName;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtProPrice;
    @FXML
    private ComboBox<?> boxName;
    @FXML
    private ComboBox<?> boxStatus;
    @FXML
    private ImageView ImaeView;
    @FXML
    private TextField txtSearch;
    @FXML
    private Text textNotice;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ProductSelected(MouseEvent event) {
    }

    @FXML
    private void handleImport(ActionEvent event) {
    }

    @FXML
    private void handleAdd(ActionEvent event) {
    }

    @FXML
    private void handleDelete(ActionEvent event) {
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
    }

    @FXML
    private void handleSearch(ActionEvent event) {
    }

    @FXML
    private void ClearrAllFields(ActionEvent event) {
    }
    
}
