package repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.Conference;
import domain.User;

public class ConferenceRepository extends BaseRepository<Conference>
{
    public ConferenceRepository() {
        super(Conference.class);
    }

    public List<Conference> getByUser(User user) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Conference> query = builder.createQuery(genericType);
        Root<Conference> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("author"), user));

        try {
            return factory.getCurrentSession().createQuery(query).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    public Conference getById(Integer id) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Conference> query = builder.createQuery(genericType);
        Root<Conference> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("id"), id));

        try {
            return factory.getCurrentSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
