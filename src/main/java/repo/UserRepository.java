package repo;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domain.User;

@Repository
@Transactional
public class UserRepository
{
    @Autowired
    SessionFactory factory;

    /**
     * Creeaza un nou obiect daca userul nu are id si il updateaza daca are id.
     *
     * @param user
     */
    public void save(User user) {
        factory.getCurrentSession().saveOrUpdate(user);
    }

    /**
     * Returneaza toti utilizatorii.
     *
     * @return
     */
    public List<User> all() {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.select(root);

        return factory.getCurrentSession().createQuery(query).getResultList();
    }

    public void delete(User obj) {
        factory.getCurrentSession().delete(obj);
    }
}
