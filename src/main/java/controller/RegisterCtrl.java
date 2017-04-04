package controller;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.dao.DataIntegrityViolationException;
import service.UserService;
import util.Controller;
import util.UIUtil;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Set;


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


    /**
     * @return true if password is valid, false otherwise
     * Check if password length is bigger then 5 characters
     * Check if password field and repeat password field contain the same value
     */
    private Boolean validatePasswords(){
        if (!passwordTxt.getText().equals(repeatPassTxt.getText())){
            UIUtil.errorAlert("Passwords don't match!");
            return false;
        }
        return true;
    }

    /**
     * Clear password fields
     */
    private void clearPasswordFields(){
        passwordTxt.clear();
        repeatPassTxt.clear();
    }

    /**
     * @param event ActionEvent
     * Register a new user
     */
    @FXML
    void registerAction(ActionEvent event) {
        if (validatePasswords()){
            try {
                service.add(emailTxt.getText(), passwordTxt.getText(), firstNameTxt.getText(), secondNameTxt.getText(), null);
                ((Stage) emailTxt.getScene().getWindow()).close();
                UIUtil.showMessage("Registration", "Registration successful.\n\nPlease login to continue.");
                return;
            }catch(ConstraintViolationException validationException){
                UIUtil.errorAlert(UIUtil.getErrorMessage(validationException));
            }catch (DataIntegrityViolationException dataIntegrityViolationException){
                UIUtil.errorAlert("This email was already used!");
            }
        }
        clearPasswordFields();

    }
}
