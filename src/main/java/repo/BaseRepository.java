package repo;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.Validator;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class BaseRepository<T>
{
    private final Class<T> genericType;

    @Autowired
    SessionFactory factory;
    @Autowired
    Validator validator;

    public BaseRepository(Class<T> cls) {
        this.genericType = cls;
    }

    /**
     * Creeaza un nou obiect in DB daca obiectul nu are id si il updateaza daca are id.
     *
     * @param user
     */
    public void save(T obj) {
        validator.validate(obj);
        factory.getCurrentSession().saveOrUpdate(obj);
    }

    /**
     * Sterge obiectul dat.
     *
     * @param obj
     */
    public void delete(T obj) {
        factory.getCurrentSession().delete(obj);
    }

    /**
     * Returneaza toate obiectele.
     *
     * @return
     */
    public List<T> all() {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(genericType);
        Root<T> root = query.from(genericType);

        query.select(root);

        return factory.getCurrentSession().createQuery(query).getResultList();
    }
}
