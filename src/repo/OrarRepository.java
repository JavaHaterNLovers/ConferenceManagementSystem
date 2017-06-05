package repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.Orar;
import domain.Proposal;

public class OrarRepository extends BaseRepository<Orar>
{
    public OrarRepository() {
        super(Orar.class);
    }

    public Orar getByProposal(Proposal ps) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Orar> query = builder.createQuery(genericType);
        Root<Orar> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("proposal"), ps));

        try {
            return factory.getCurrentSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public boolean isOrarValid(Orar orar) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Orar> query = builder.createQuery(genericType);
        Root<Orar> root = query.from(genericType);

        query.select(root);
        query.where(builder.and(
                builder.lessThan(root.get("beginDate"), orar.getBeginDate()),
                builder.greaterThan(root.get("endDate"), orar.getBeginDate()),
                builder.equal(root.get("section"), orar.getSection()),
                builder.notEqual(root.get("id"), orar.getId())
            )
        );

        try {
            List<Orar> list = factory.getCurrentSession().createQuery(query).getResultList();

            return list.isEmpty();
        } catch (NoResultException ex) {
            return true;
        }
    }
}
