package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import service.ConferenceService;
import service.UserService;
import util.Controller;
import util.UIUtil;

import javax.validation.ConstraintViolationException;

/**
 * Created by Clusi on 05.04.2017.
 */
public class CreateConferenceCtrl extends Controller{

    @FXML
    private TextField nameTxt;

    @FXML
    private DatePicker beginSubmiDate;

    @FXML
    private DatePicker endSubmiDate;

    @FXML
    private DatePicker endBidDate;

    @FXML
    private DatePicker endRevDate;

    private ConferenceService service;

    private UserService userService;
    @FXML
    private void initialize() {
        this.service = (ConferenceService) this.get("service.conference");
        this.userService = (UserService) this.get("service.user");
    }

    @FXML
    void createAction(ActionEvent event) {
        try {
            service.add(nameTxt.getText(),
                    userService.getCurrentUser(),
                    service.getCalendarFromLocalDate(beginSubmiDate.getValue()),
                    service.getCalendarFromLocalDate(endSubmiDate.getValue()),
                    service.getCalendarFromLocalDate(endBidDate.getValue()),
                    service.getCalendarFromLocalDate(endRevDate.getValue()));
        } catch (ConstraintViolationException validationException) {
            UIUtil.errorAlert(UIUtil.getErrorMessage(validationException));
        }
    }
}
