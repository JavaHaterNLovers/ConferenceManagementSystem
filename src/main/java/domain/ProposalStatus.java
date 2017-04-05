package domain;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name="proposalStatus")
public class ProposalStatus
{
    @Column
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    @NotBlank(message="proposalStatus.status")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Calendar modified;


    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Calendar created;

    @Column
    @NotBlank(message="proposalStatus.comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "USER_ID_FK")
    )
    private User user;

    @ManyToOne
    @JoinColumn(name = "proposal_id",
            foreignKey = @ForeignKey(name = "PROPOSAL_ID_FK")
    )
    private Proposal proposal;


    public ProposalStatus(String status, Calendar modified, String comment, User user, Proposal proposal) {
        this.status = status;
        this.modified = modified;
        this.created = Calendar.getInstance();
        this.comment = comment;
        this.user = user;
        this.proposal = proposal;
    }

    public ProposalStatus(){
        this(null,null,null,null,null);
    }


    public Integer getId() {
        return id;
    }

    /**
     * @return ProposalStatus status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return modified time
     */
    public Calendar getModified() {
        return modified;
    }

    /**
     * @return when the ProposalStatus was created
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return proposal
     */
    public Proposal getProposal() {
        return proposal;
    }

    /**
     * @param user
     * Set the ProposalStatus to the given user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param proposal
     * Set the ProposalStatus to the given proposal
     */
    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    /**
     * @param status
     * Set ProposalStatus status to the given value
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @param modified
     * Set ProposalStatus modified date to the given value
     */
    public void setModified(Calendar modified) {
        this.modified = modified;
    }

    /**
     * @param created
     * Set ProposalStatus created date to the given value
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

    /**
     * @param comment
     * Set ProposalStatus comments
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ProposalStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", modified=" + modified +
                ", created=" + created +
                ", comment='" + comment + '\'' +
                '}';
    }
}