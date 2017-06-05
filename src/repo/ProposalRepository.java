package repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;

import domain.Edition;
import domain.Proposal;
import domain.User;

@SuppressWarnings("unchecked")
public class ProposalRepository extends BaseRepository<Proposal>
{
    public ProposalRepository() {
        super(Proposal.class);
    }

    public List<Proposal> getByUser(User user) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Proposal> query = builder.createQuery(genericType);
        Root<Proposal> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("user"), user));

        try {
            return factory.getCurrentSession().createQuery(query).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Proposal> getNewProposals(User user) {
        Query<Proposal> proposals = factory.getCurrentSession()
                .createQuery("select distinct p from Proposal p"
                + " left join ProposalStatus ps on ps.proposal = p"
                + " where ((ps.user = ? and ps.status in ('analyzes', 'maybeAnalyzes', 'rejectAnalyzes')) or ps.user is null or"
                + "(ps.user != ? and ps.status in ('analyzes', 'maybeAnalyzes', 'rejectAnalyzes'))) and p.edition.endBidding >= CURRENT_TIMESTAMP");

        proposals.setParameter(0, user);
        proposals.setParameter(1, user);

        try {
            return proposals.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Proposal> getByEdition(Edition edition) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Proposal> query = builder.createQuery(genericType);
        Root<Proposal> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("edition"), edition));

        try {
            return factory.getCurrentSession().createQuery(query).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Proposal> getToReview(User user) {
        Query<Proposal> proposals = factory.getCurrentSession()
                .createQuery("select distinct p from Proposal p"
                + " left join ProposalStatus ps on ps.proposal = p"
                + " where ps.user = ? and ps.status not in ('analyzes', 'maybeAnalyzes', 'rejectAnalyzes')");

        proposals.setParameter(0, user);

        try {
            return proposals.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
