package domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="payments")
public class Payment {
    public static final float sumaDePlata = 30f;

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Edition edition;

    @Column
    private String cardNumber;

    @Column
    private float suma;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @CreationTimestamp
    private Calendar created;

    public Payment() {}

    public Payment(User user, Edition edition, String cardNumber, float suma) {
        super();
        this.user = user;
        this.edition = edition;
        this.cardNumber = cardNumber;
        this.suma = suma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }

    public Calendar getCreated() {
        return created;
    }

    @AssertTrue(message="{payment.card}")
    public boolean isCardNumberValid() {
        return this.cardNumber != null && this.cardNumber.equals("1111222233334444");
    }
}
