package model;

import java.util.ArrayList;
import java.util.List;

public class Reviewers
{
    private List<String> idProposalsStatus;

    public Reviewers() {
        this.idProposalsStatus = new ArrayList<>();
    }

    public List<String> getIdProposalsStatus() {
        return idProposalsStatus;
    }

    public void setIdProposalsStatus(List<String> idProposalsStatus) {
        this.idProposalsStatus=idProposalsStatus;
    }

}
