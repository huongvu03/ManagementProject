package managementproject;

import Database.UserDAO;
import Models.UserInfo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LogPageController implements Initializable {

    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassWord;
    @FXML
    private Hyperlink txtForgotPass;
    @FXML
    private TextField txt_ReUserName;
    @FXML
    private TextField txt_RePass;
    @FXML
    private TextField txt_ReAnswer;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnAlreadyHave;
    @FXML
    private TextField txt_ReUserId;
    @FXML
    private ComboBox<String> userQuestion;
    @FXML
    private ComboBox<String> userPosition;
    @FXML
    private AnchorPane LogInPage;
    @FXML
    private AnchorPane tvLogInForm;
    @FXML
    private AnchorPane tvRegisterForm;
    @FXML
    private AnchorPane tvSliderForm;

    private UserDAO udao = new UserDAO();
    private ObservableList<UserInfo> uList = FXCollections.observableArrayList(udao.listDB());

    private ObservableList<String> positionList;
    private ObservableList<String> questionList;
    private ObservableList<String> forgotQList;

    public Alert alert;
    @FXML
    private AnchorPane tvForgotPass;

    @FXML
    private ComboBox<String> Forgot_QuestionList;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void positionList() {
        positionList = FXCollections.observableArrayList("Staff", "Supervisior");
        userPosition.setItems(positionList);
    }

    public void questionList() {
        questionList = FXCollections.observableArrayList("What is your favorite Color?", "What is your favorite food?", "what is your  favorite Place?");
        userQuestion.setItems(questionList);
    }

    public void ResetQList() {
        forgotQList = FXCollections.observableArrayList("What is your favorite Color?", "What is your favorite food?", "what is your  favorite Place?");
        Forgot_QuestionList.setItems(forgotQList);
    }

    @FXML
    private void handleSwitch(ActionEvent event) {
        if (event.getSource() == btnCreate) {
            tvSliderForm.setVisible(false);
            tvRegisterForm.setVisible(true);
            tvLogInForm.setVisible(true);
            positionList();
            questionList();
        }
    }

    @FXML
    private void HandleLogIn(ActionEvent event) throws IOException {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);

        if (txtUserName.getText().isEmpty() || txtPassWord.getText().isEmpty()) {
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        } else {
            String userName = txtUserName.getText();
            String userPassWord = txtPassWord.getText();
            UserInfo user = new UserInfo();
            user.setUserName(userName);
            user.setUserPassWord(userPassWord);

            if (udao.searchDB(user) == null) {
                alert.setContentText("Incorrect Username/Password");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("LOGIN Sucessfull");
                data.username=user.getUserName();
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    // TO HIDE MAIN FORM 
                    LogInPage.getScene().getWindow().hide();
                    // LINK YOUR LOGIN FORM AND SHOW IT 
                    URL url = new File("src/managementproject/FXMLDocument.fxml").toURI().toURL();
                    Parent root = FXMLLoader.load(url);

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Cafe Shop Management System");

                    stage.setScene(scene);
                    stage.show();

                }
            }
        }
    }

    @FXML
    private void HandleSignUp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Notice:");
        alert.setHeaderText(null);
        if (txt_ReUserId.getText().isEmpty() || txt_ReUserName.getText().isEmpty()
                || txt_RePass.getText().isEmpty() || txt_ReAnswer.getText().isEmpty()
                || userPosition.getSelectionModel().getSelectedItem().isEmpty()
                || userQuestion.getSelectionModel().getSelectedItem().isEmpty()) {
            alert.setContentText("Please Fill All Blank");
            alert.showAndWait();
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
                alert.setContentText("Add successful.Please Log in");
                alert.showAndWait();
                Clear();
                tvSliderForm.setVisible(true);
                tvRegisterForm.setVisible(false);
                tvLogInForm.setVisible(true);
                tvForgotPass.setVisible(false);

            } else {
                alert.setContentText("Add failed, User might already exist");
                alert.showAndWait();
            }

        }

    }

    public void Clear() {
        txt_ReUserId.clear();
        txt_ReUserName.clear();
        txt_RePass.clear();
        userPosition.getItems();
        userQuestion.getItems();
        txt_ReAnswer.clear();

    }

    @FXML
    public void switchForgotPass() {
        tvForgotPass.setVisible(true);
        tvLogInForm.setVisible(false);
        ResetQList();
    }

    @FXML
    private void ReserPass(ActionEvent event) {
        String userName = txt_FGUserName.getText();
        String question = Forgot_QuestionList.getSelectionModel().getSelectedItem();
        String anwser = txt_FGAnswer.getText();
        UserInfo user = new UserInfo();
        user.setUserName(userName);
        user.setQuestion(question);
        user.setAnswer(anwser);
        System.out.println("" + user.toString());

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
    


}
