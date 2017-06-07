package test.service;

import java.util.ArrayList;
import java.util.List;

import domain.Edition;
import domain.Proposal;
import repo.ProposalRepository;

public class ProposalRepositoryMock extends ProposalRepository
{
    @Override
    public List<Proposal> getByEdition(Edition edition) {
        List<Proposal> list = new ArrayList<>();

        list.add(ProposalStatusRepositoryMock.proposal);

        return list;
    }
}
