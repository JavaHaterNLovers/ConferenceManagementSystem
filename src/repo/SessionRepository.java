package repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.Edition;
import domain.Session;

public class SessionRepository extends BaseRepository<Session>
{
    public SessionRepository() {
        super(Session.class);
    }

    public List<Session> getForEdition(Edition ed) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Session> query = builder.createQuery(genericType);
        Root<Session> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("edition"), ed));

        try {
            return factory.getCurrentSession().createQuery(query).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
