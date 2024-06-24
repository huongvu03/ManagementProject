package managementproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    @FXML
    private BorderPane PageView;
    @FXML
    private Text txt_DisPlayName;
    @FXML
    private AnchorPane Menu;

    public Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        alert.setHeaderText(null);

        ShowUserName();
    }

    public void ShowUserName() {
        String user = data.username;
        txt_DisPlayName.setText(user);
    }

    private void LoadPage(String page) {
        Parent root = null;
        try {
            URL url = new File("src/managementproject/" + page + ".fxml").toURI().toURL();
            root = FXMLLoader.load(url);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PageView.setCenter(root);
    }

    @FXML
    private void DashBoard(MouseEvent event) {
        if (data.position.equals("Admin")) {
            LoadPage("DashBoard");
        } else {
            alert.setContentText("Access Denied !");
            alert.showAndWait();
        }
    }

    @FXML
    private void Menu(MouseEvent event) {
        PageView.setCenter(Menu);
    }

    @FXML
    private void Inventory(MouseEvent event) {
        if (!data.position.equals("Admin")) {
            alert.setContentText("Access Denied !");
            alert.showAndWait();
        } else {
            LoadPage("Inventory");
        }
    }

    @FXML
    private void Customers(MouseEvent event) {
        LoadPage("Customers");
    }

    @FXML
    private void Staff(MouseEvent event) {
        if (!data.position.equals("Admin")) {
            alert.setContentText("Access Denied !");
            alert.showAndWait();
        } else {
            LoadPage("Staff");
        }

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
                stage.show();

            }else if (option.isPresent() && option.get() == ButtonType.CANCEL) {
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

}
