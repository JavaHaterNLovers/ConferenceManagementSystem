package repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.Edition;
import domain.User;

public class EditionRepository extends BaseRepository<Edition>
{
    public EditionRepository() {
        super(Edition.class);
    }

    public List<Edition> getByUser(User user) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Edition> query = builder.createQuery(genericType);
        Root<Edition> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("author"), user));

        try {
            return factory.getCurrentSession().createQuery(query).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
