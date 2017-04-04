package controller;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
        if (passwordTxt.getText().length() < 5){
            UIUtil.errorAlert("Password should be at least 5 characters.");
            return false;
        }
        if (!passwordTxt.getText().equals(repeatPassTxt.getText())){
            UIUtil.errorAlert("Passwords don't match!");
            return false;
        }
        return true;
    }


    /**
     * @return true if textFields are not empty, false otherwise
     * Check for each textField if it is empty
     */
    private Boolean notEmptyFields(){
        String errors = "";
        if (firstNameTxt.getText().equals("")){
            errors += "First name can't be empty!\n";
        }
        if (secondNameTxt.getText().equals("")){
            errors += "Second name can't be empty!\n";
        }
        if (emailTxt.getText().equals("")){
            errors += "E-mail can't be empty!\n";
        }
        if (passwordTxt.getText().equals("")){
            errors += "Password field can't be empty!\n";
        }
        if (errors.equals("")) return true;
        UIUtil.errorAlert(errors);
        return false;
    }

    /**
     * Clear password fields
     */
    private void clearPasswordFields(){
        passwordTxt.clear();
        repeatPassTxt.clear();
    }

    /**
     * @param constraintViolationException a constraint validation exception
     * @return a string with all the messages from the exception
     */
    private String getErrorMessage(ConstraintViolationException constraintViolationException){
        String errors = "";
        Set constraintViolations =  constraintViolationException.getConstraintViolations();
        for (Object c:constraintViolations){
            ConstraintViolation cv = (ConstraintViolation) c;
            errors += cv.getMessage() + "\n";
        }
        return errors;
    }

    /**
     * @param event ActionEvent
     * Register a new user
     */
    @FXML
    void registerAction(ActionEvent event) {
        if (validatePasswords()){
            User user = service.validUser(emailTxt.getText(), passwordTxt.getText());
            if (user != null) {
                UIUtil.errorAlert("An user with this e-mail is already registered.");
            }else{
                try {
                    service.add(emailTxt.getText(), passwordTxt.getText(), firstNameTxt.getText(), secondNameTxt.getText(), null);
                    ((Stage) emailTxt.getScene().getWindow()).close();
                    UIUtil.showMessage("Registration", "Registration successful.\n\nPlease login to continue.");
                    return;
                }catch(ConstraintViolationException validationException){
                    UIUtil.errorAlert(getErrorMessage(validationException));
                }
            }
        }
        clearPasswordFields();

    }
}
