package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.UserService;
import util.Controller;


/**
 * Created by Mihai on 04.04.2017.
 */
public class RegisterCtrl extends Controller {
    @FXML
    private TextField firstNameTxt;

    @FXML
    private TextField secondNameTxt;

    @FXML
    private TextField emailTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private PasswordField repeatPassTxt;

    private UserService service;

    @FXML
    private void initialize() {
        this.service = (UserService) this.get("service.user");
    }

    @FXML
    void registerAction(ActionEvent event) {

    }
}
