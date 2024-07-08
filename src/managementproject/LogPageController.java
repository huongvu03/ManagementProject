package managementproject;

import Database.UserDAO;
import Models.UserInfo;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LogPageController implements Initializable {

    @FXML
    private AnchorPane LogInPage;
    @FXML
    private AnchorPane tvLogInForm;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassWord;
    @FXML
    private Hyperlink txtForgotPass;
    private TextField txt_ReUserName;
    private TextField txt_RePass;
    private TextField txt_ReAnswer;
    private TextField txt_ReUserId;
    private ComboBox<String> userQuestion;
    private ComboBox<String> userPosition;
    @FXML
    private ComboBox<String> combo_ForgotList;
    @FXML
    private AnchorPane tvForgotPass;
    @FXML
    private TextField txt_FGAnswer;
    @FXML
    private TextField txt_NewPass;
    @FXML
    private TextField txt_ConfirmNewPass;
    @FXML
    private AnchorPane tvResetPassForm;
    @FXML
    private TextField txt_FGUserName;
    @FXML
    private TextField txt_ResetUserName;

    public UserDAO udao = new UserDAO();
    public ObservableList<UserInfo> uList = FXCollections.observableArrayList(udao.listDB());
    public Alert alert;
    public ObservableList<String> questionList;
    @FXML
    private StackPane LogIn;
 
 

    private void questionList() {
        questionList = FXCollections.observableArrayList("What is your favorite Color?", "What is your favorite food?", "what is your  favorite Place?");
        userQuestion.setItems(questionList);
    }

    private void comboQuestionList() {
        questionList = FXCollections.observableArrayList("What is your favorite Color?", "What is your favorite food?", "what is your  favorite Place?");
        combo_ForgotList.setItems(questionList);
    }

    @FXML
    public void switchForgotPass() {
        tvForgotPass.setVisible(true);
        tvLogInForm.setVisible(false);
        comboQuestionList();
    }

    @FXML
    private void ResetPass(ActionEvent event) {
        String userName = txt_FGUserName.getText();
        String question = combo_ForgotList.getSelectionModel().getSelectedItem();
        String anwser = txt_FGAnswer.getText();
        UserInfo user = new UserInfo();
        user.setUserName(userName);
        user.setQuestion(question);
        user.setAnswer(anwser);

        if (udao.searchUser(user) == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Answer");
            alert.showAndWait();

        } else {
            tvForgotPass.setVisible(false);
            tvResetPassForm.setVisible(true);

        }
    }

    @FXML
    private void UpdatePass(ActionEvent event) {
        String userName = txt_ResetUserName.getText();
        String pass = txt_NewPass.getText();
        String cpass = txt_ConfirmNewPass.getText();
        if (pass.equals(cpass)) {
            UserInfo u = new UserInfo();
            u.setUserName(userName);
            u.setUserPassWord(cpass);
            udao.editDB(u);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Reset Pass Sucessfull. Please log in");
            alert.showAndWait();
            tvResetPassForm.setVisible(false);
            tvLogInForm.setVisible(true);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setContentText("pass must = confirm pass");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TO DO
    }

    @FXML
    private void LogIn(ActionEvent event) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        try {
            if (txtUserName.getText().isEmpty() || txtPassWord.getText().isEmpty()) {
                alert.setContentText(" Username/Password cant be empty");
                alert.showAndWait();
            } else {
                String userName = txtUserName.getText();
                String userPassWord = txtPassWord.getText();
                UserInfo user = new UserInfo();
                user.setUserName(userName);
                user.setUserPassWord(userPassWord);

                if (udao.searchDB(user) != null) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("LOGIN Sucessfull");
                    data.username = user.getUserName();
                    data.position = user.getUserPosition();

                    System.out.println("Username: " + data.username + ", Position: " + data.position);
                    txtUserName.clear();
                    txtPassWord.clear();

                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get().equals(ButtonType.OK)) {
                        // TO HIDE MAIN FORM 
                        LogInPage.getScene().getWindow().hide();

                        // LINK YOUR LOGIN FORM AND SHOW IT 
                        URL url = new File("src/managementproject/Menu.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        stage.setTitle("Order Page");
                        stage.setScene(scene);
                        //stage.setFullScreen(true);
                        stage.show();
                    }
                } else {
                    alert.setContentText("Incorrect Username/Password.No user Avaiable");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }

    public void Clear() {
        txt_ReUserId.clear();
        txt_ReUserName.clear();
        txt_RePass.clear();
        userPosition.getItems();
        userQuestion.getItems();
        txt_ReAnswer.clear();
        txtUserName.clear();
        txtPassWord.clear();
        txt_FGUserName.clear();
        txt_FGAnswer.clear();
        combo_ForgotList.getItems();

    }
}
