package domain;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.File;
import java.util.Calendar;

@Entity(name="ProposalTopic")
public class ProposalTopic
{
    @Column
    @ManyToOne()
    @NotBlank(message="{ProposalTopic.nume}")
    private String nume;
    @Column
    @ManyToOne
    private User user;


    public ProposalTopic() {this(null,null);}


    /**
     * Creeaza un ProposalTopic
     *
     * @param nume
     *@param user
     */

    public ProposalTopic(String nume,User user){
        this.setNume(nume);
        this.setUser(user);
    }

    public String getNume() {
        return nume;
    }


    public User getUser() {
        return user;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setUser(User user) {
        this.user = user;
    }
}