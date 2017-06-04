package repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.Proposal;
import domain.ProposalStatus;

public class ProposalStatusRepository extends BaseRepository<ProposalStatus>
{
    public ProposalStatusRepository() {
        super(ProposalStatus.class);
    }

    public List<ProposalStatus> getByProposal(Proposal proposal) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<ProposalStatus> query = builder.createQuery(genericType);
        Root<ProposalStatus> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("proposal"), proposal));

        try {
            return factory.getCurrentSession().createQuery(query).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}