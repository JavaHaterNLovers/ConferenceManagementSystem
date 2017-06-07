package test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import domain.Conference;
import domain.Edition;
import domain.Proposal;
import domain.ProposalStatus;
import domain.ProposalStatus.proposalStatus;
import domain.User;
import domain.User.UserRole;
import repo.ProposalStatusRepository;

public class ProposalStatusRepositoryMock extends ProposalStatusRepository
{
    public static User user = new User("email", "pass", "name", "prenume", UserRole.user);
    public static Proposal proposal = new Proposal(user,
        new Edition(user, "edition", new Conference(user, "conference"), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(),
                    Calendar.getInstance(), Calendar.getInstance()
        ), "proposal", new ArrayList<>(), "", null, "desc");

    @Override
    public List<ProposalStatus> getByProposal(Proposal proposal2) {
        List<ProposalStatus> list = new ArrayList<>();

        list.add(new ProposalStatus(proposalStatus.strongReject, "comment", user, proposal));
        list.add(new ProposalStatus(proposalStatus.analyzes, "comment", user, proposal));
        list.add(new ProposalStatus(proposalStatus.strongAccept, "comment", user, proposal));
        list.add(new ProposalStatus(proposalStatus.borderlinePaper, "comment", user, proposal));
        list.add(new ProposalStatus(proposalStatus.rejectAnalyzes, "comment", user, proposal));
        list.add(new ProposalStatus(proposalStatus.toReview, "comment", user, proposal));

        return list;
    }
}
