package controller;

import domain.Conference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.ConferenceService;
import util.BaseModel;
import util.Controller;

import java.util.List;

/**
 * Created by coroian on 4/4/2017.
 */
public class ConferenceCtrl extends Controller {

    private ConferenceService service;

    @FXML
    private TableView<Conference> tableConference;

    private BaseModel<Conference> model;

    private ConferenceCtrl() {

        this.service = (ConferenceService) this.get("service.conference");
        model = new BaseModel<>(service.all());

        initData();
    }

    /**
     * Init the table conference
     */
    public void initData()
    {
        tableConference.getItems().clear();
        for (Conference conf:model.getList()) {
            tableConference.getItems().add(conf);
        }
    }
}
