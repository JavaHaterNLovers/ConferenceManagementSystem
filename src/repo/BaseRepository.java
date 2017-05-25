package repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class BaseRepository<T>
{
    protected final Class<T> genericType;

    @Autowired
    SessionFactory factory;

    public BaseRepository(Class<T> cls) {
        this.genericType = cls;
    }

    /**
     * Creeaza un nou obiect in DB daca obiectul nu are id si il updateaza daca are id.
     *
     * @param obj
     */
    public void save(T obj) {
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

    /**
     * Returneaza numarul de elemente.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public long size() {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(genericType);

        query.select((Selection<? extends T>) builder.count(query.from(genericType)));

        return Long.parseLong(factory.getCurrentSession().createQuery(query).getSingleResult().toString());
    }

    public T get(Integer id) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(genericType);
        Root<T> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("id"), id));

        try {
            return factory.getCurrentSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
