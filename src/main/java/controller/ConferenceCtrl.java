package controller;

import domain.Conference;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.ConferenceService;
import util.Controller;

import java.util.List;

/**
 * Created by coroian on 4/4/2017.
 */
public class ConferenceCtrl extends Controller {

    private ConferenceService service;

    private TableView<Conference> conferenceTable;

    @FXML
    private void initialize() {
        this.service = (ConferenceService) this.get("service.Conference");
    }

    /**
     * Get all conferences
     * @return
     */
    public List<Conference> GetAll()
    {
        return service.all();
    }

    /**
     * Init the table conference
     */
    public void initData()
    {
        TableColumn authorCol = new TableColumn("Author");
        TableColumn nameCol = new TableColumn("Name");
        TableColumn beginSubmissionsCol = new TableColumn("Begin Submissions");
        TableColumn endSubmissionsCol = new TableColumn("End Submissions");
        TableColumn endBiddingCol = new TableColumn("End Bidding");
        TableColumn endReviewCol = new TableColumn("End Review");
        TableColumn createdCol = new TableColumn("Created");

        conferenceTable.getColumns().addAll(authorCol, nameCol, beginSubmissionsCol, endSubmissionsCol,
                endBiddingCol, endReviewCol, createdCol);

        conferenceTable.getItems().clear();
        for(Conference c:service.all())
        {
            conferenceTable.getItems().add(c);
        }
    }
}
