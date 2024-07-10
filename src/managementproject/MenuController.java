package managementproject;

import Database.ProductDAO;
import Models.Product;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    @FXML
    private BorderPane PageView;
    @FXML
    private Text txt_DisPlayName;

    //menu
    @FXML
    private GridPane menu_gridPaneDrink;
    @FXML
    private GridPane menu_gridPaneFood;
    @FXML
    private AnchorPane MenuItem;
    @FXML
    private TextField menu_TextNote;

    //bill
    @FXML
    private TableView<Product> menu_TbView;
    @FXML
    private TableColumn<Product, String> menu_Col_ProName;
    @FXML
    private TableColumn<Product, Integer> menu_Col_Quantity;
    @FXML
    private TableColumn<Product, Double> menu_Col_Price;
    @FXML
    private TableColumn<Product, String> menu_Col_Note;
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
    private Button menu_btnPAY;
    @FXML
    private Button menu_btnREMOVE;
    @FXML
    private Button menu_btnRECEIPT;

    public Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        alert.setHeaderText(null);
        ShowUserName();
        menuDisplayCard();

    }
// NAVBAR CONTROL

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
        PageView.setCenter(MenuItem);

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

    //danh sach Menu
    ProductDAO pdao = new ProductDAO();
    ObservableList<Product> cardListDrinkData = FXCollections.observableArrayList();
    ObservableList<Product> cardListFoodData = FXCollections.observableArrayList();

    public void menuDisplayCard() {

        int row = 0;
        int column = 0;

        cardListDrinkData.clear();
        cardListDrinkData.addAll(pdao.menuListDB());
        menu_gridPaneDrink.getChildren().clear();
        menu_gridPaneDrink.getRowConstraints().clear();
        menu_gridPaneDrink.getColumnConstraints().clear();

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
        row = 0;
        column = 0;

        cardListFoodData.clear();
        cardListFoodData.addAll(pdao.menuListFoodDB());

        menu_gridPaneFood.getChildren().clear();
        menu_gridPaneFood.getRowConstraints().clear();
        menu_gridPaneFood.getColumnConstraints().clear();

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

}
