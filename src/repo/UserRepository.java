package repo;


import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.User;

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
}
