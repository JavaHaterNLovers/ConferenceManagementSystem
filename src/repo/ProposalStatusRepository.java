package repo;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.Proposal;
import domain.ProposalStatus;
import domain.User;

public class ProposalStatusRepository extends BaseRepository<ProposalStatus>
{
    public ProposalStatusRepository() {
        super(ProposalStatus.class);
    }

    public ProposalStatus getForUserProposal(User user, Proposal proposal) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<ProposalStatus> query = builder.createQuery(genericType);
        Root<ProposalStatus> root = query.from(genericType);

        query.select(root);
        query.where(builder.and(builder.equal(root.get("user"), user), builder.equal(root.get("proposal"), proposal)));

        try {
            return factory.getCurrentSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
