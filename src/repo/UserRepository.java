package repo;


import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.User;
import domain.User.UserRole;

public class UserRepository extends BaseRepository<User>
{
    public UserRepository() {
        super(User.class);
    }

    public User getByEmail(String email) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(genericType);
        Root<User> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("email"), email));

        try {
            return factory.getCurrentSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<User> getChairCoChair() {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(genericType);
        Root<User> root = query.from(genericType);

        query.select(root);
        query.where(builder.or(builder.equal(root.get("rol"), UserRole.chair), builder.equal(root.get("rol"), UserRole.coChair)));

        try {
            return factory.getCurrentSession().createQuery(query).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
