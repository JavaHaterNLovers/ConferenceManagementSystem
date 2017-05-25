package repo;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.Edition;
import domain.Payment;
import domain.User;

public class PaymentRepository extends BaseRepository<Payment>
{
    public PaymentRepository() {
        super(Payment.class);
    }

    public Payment getForUserEdition(User user, Edition edition) {
        CriteriaBuilder builder = factory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Payment> query = builder.createQuery(genericType);
        Root<Payment> root = query.from(genericType);

        query.select(root);
        query.where(builder.and(builder.equal(root.get("user"), user), builder.equal(root.get("edition"), edition)));

        try {
            return factory.getCurrentSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
