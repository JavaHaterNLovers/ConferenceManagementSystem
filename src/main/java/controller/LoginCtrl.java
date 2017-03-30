package controller;

import java.io.IOException;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserService;
import util.Controller;
import util.UIUtil;

public class LoginCtrl extends Controller
{
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField passTxt;

    @FXML
    private Button loginBtn;

    private UserService service;

    @FXML
    private void initialize() {
        this.service = (UserService) this.get("service.user");
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        User user = service.validUser(emailTxt.getText(), passTxt.getText());
        if (user == null) {
            UIUtil.errorAlert("Email sau parola invalide.");
            return;
        }

        service.setCurrentUser(user);

        ((Stage) emailTxt.getScene().getWindow()).close();
        UIUtil.showMessage("Success", "Ati fost logat cu success!");
    }

    public Button getLoginBtn() {
        return this.loginBtn;
    }
}
