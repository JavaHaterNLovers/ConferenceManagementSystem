package repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.Conference;
import domain.Payment;
import domain.User;

public class PaymentRepository extends BaseRepository<Payment>
{
    public PaymentRepository() {
        super(Payment.class);
    }

    public List<Payment> getByUser(User user) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Payment> query = builder.createQuery(genericType);
        Root<Payment> root = query.from(genericType);

        query.select(root);
        query.where(builder.equal(root.get("author"), user));

        try {
            return factory.getCurrentSession().createQuery(query).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
