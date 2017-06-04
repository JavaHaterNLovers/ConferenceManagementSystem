package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import domain.Proposal;
import domain.ProposalStatus;
import repo.ProposalStatusRepository;

/**
 * Created by NicoF on 4/4/2017.
 */
public class ProposalStatusService extends BaseDomainService<ProposalStatus, ProposalStatusRepository>
{
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
    
    public Integer getProposalStatus(Proposal proposal){
        List<ProposalStatus> res = getByProposalAndReviewed(proposal);
        Integer nrAccept = 0;
        Integer nrReject = 0;
        
        if (res.size() < 3){
            return 0;
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
        if (nrAccept == 0 && nrReject == 0){
            return 0;
        }
        if (nrReject == 0){
            return 1;
        }
        if (nrAccept == 0){
            return -1;
        }
        return 0;
    }
}
