package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import domain.Proposal;
import domain.ProposalStatus;
import domain.User;
import repo.ProposalStatusRepository;

/**
 * Created by NicoF on 4/4/2017.
 */
public class ProposalStatusService extends BaseDomainService<ProposalStatus, ProposalStatusRepository>
{
    public static final int STATUS_ACCEPTED = 1;
    public static final int STATUS_PENDING = 0;
    public static final int STATUS_REJECTED = -1;

    public ProposalStatusService(ProposalStatusRepository repo) {
        super(repo);
    }

    public List<ProposalStatus> getByProposalAndReviewed(Proposal proposal) {
        List<ProposalStatus> res = new ArrayList<>();
        repo.getByProposal(proposal)
                    .stream()
                    .filter(x->Arrays.asList(ProposalStatus.proposalStatus.strongAccept,
                            ProposalStatus.proposalStatus.accept,
                            ProposalStatus.proposalStatus.borderlinePaper,
                            ProposalStatus.proposalStatus.reject,
                            ProposalStatus.proposalStatus.strongReject,
                            ProposalStatus.proposalStatus.weekAccept,
                            ProposalStatus.proposalStatus.weekReject
                            ).contains(x.getStatus()))
                    .sorted((x,y)->x.getStatus().compareTo(y.getStatus()))
                    .forEach(x->res.add(x));
        return res;
    }

    public List<ProposalStatus> getByProposalAndReviewedIgnore(Proposal proposal, User user) {
        List<ProposalStatus> res = new ArrayList<>();
        repo.getByProposal(proposal)
                    .stream()
                    .filter(x->Arrays.asList(ProposalStatus.proposalStatus.strongAccept,
                            ProposalStatus.proposalStatus.accept,
                            ProposalStatus.proposalStatus.borderlinePaper,
                            ProposalStatus.proposalStatus.reject,
                            ProposalStatus.proposalStatus.strongReject,
                            ProposalStatus.proposalStatus.weekAccept,
                            ProposalStatus.proposalStatus.weekReject
                            ).contains(x.getStatus())
                            && (x.getProposal().getUser().getId() != user.getId()
                                || x.getUser().getId() == user.getId()
                            )
                        )
                    .sorted((x,y)->x.getStatus().compareTo(y.getStatus()))
                    .forEach(x->res.add(x));
        return res;
    }

    public List<ProposalStatus> getByProposalWithoutReviews(Proposal proposal) {
        List<ProposalStatus> res = new ArrayList<>();
        repo.getByProposal(proposal)
                    .stream()
                    .filter(x->Arrays.asList(ProposalStatus.proposalStatus.analyzes,
                            ProposalStatus.proposalStatus.maybeAnalyzes,
                            ProposalStatus.proposalStatus.rejectAnalyzes
                            ).contains(x.getStatus()))
                    .sorted((x,y)->x.getStatus().compareTo(y.getStatus()))
                    .forEach(x->res.add(x));
        return res;
    }

    public List<ProposalStatus> getByProposalWithAnalyzes(Proposal proposal) {
        List<ProposalStatus> res = new ArrayList<>();
        repo.getByProposal(proposal)
                    .stream()
                    .filter(x->Arrays.asList(ProposalStatus.proposalStatus.analyzes,
                            ProposalStatus.proposalStatus.maybeAnalyzes
                            ).contains(x.getStatus()))
                    .sorted((x,y)->x.getStatus().compareTo(y.getStatus()))
                    .forEach(x->res.add(x));
        return res;
    }

    public List<ProposalStatus> getByProposalReviwers(Proposal proposal) {
        List<ProposalStatus> res = new ArrayList<>();
        repo.getByProposal(proposal)
                    .stream()
                    .filter(x->Arrays.asList(ProposalStatus.proposalStatus.toReview
                            ).contains(x.getStatus()))
                    .sorted((x,y)->x.getStatus().compareTo(y.getStatus()))
                    .forEach(x->res.add(x));
        return res;
    }

    public Integer getProposalStatus(Proposal proposal){
        List<ProposalStatus> res = getByProposalAndReviewed(proposal);
        Integer nrAccept = 0;
        Integer nrReject = 0;
        Boolean reviewesEnd = Calendar.getInstance().compareTo(proposal.getEdition().getEndReview()) == 1;
        if (res.size() < 2) {
            if (reviewesEnd){
                return STATUS_REJECTED;
            }
            return STATUS_PENDING;
        }

        for (ProposalStatus ps:res){
            if (Arrays.asList(ProposalStatus.proposalStatus.strongAccept,
                            ProposalStatus.proposalStatus.accept,
                            ProposalStatus.proposalStatus.weekAccept).contains(ps.getStatus())){
                nrAccept++;
            }else if (ProposalStatus.proposalStatus.borderlinePaper != ps.getStatus()){
                nrReject++;
            }
        }
        if (nrAccept == 0 && nrReject == 0) {
            return STATUS_PENDING;
        }

        if (nrReject == 0) {
            return STATUS_ACCEPTED;
        }

        if (nrAccept == 0){
            return STATUS_REJECTED;
        }

        return STATUS_PENDING;
    }
}
