package repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.Proposal;
import domain.User;

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
}
