package domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="proposal_statuses")
public class ProposalStatus
{
    @Column
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private proposalStatus status;

    @Column
    @NotBlank(message="proposalStatus.comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @UpdateTimestamp
    private Calendar modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @CreationTimestamp
    private Calendar created;

    public ProposalStatus() {
        this(null, null, null, null);
    }

    public ProposalStatus(proposalStatus status, String comment, User user, Proposal proposal) {
        this.status = status;
        this.comment = comment;
        this.user = user;
        this.proposal = proposal;
    }
    
    public static enum proposalStatus {

        analyzes("ANALYZES_PROPOSAL"),
        maybeAnalyzes("MAYBE_ANALYZES_PROPOSAL"),
        rejectAnalyzes("REJECT_ANALYZES_PROPOSAL");
    
        /**
         * Numele statusului.
         */
        private String nume;

        /**
         * Creeaza un nou status cu un nume.
         * @param nume
         */
        proposalStatus(String nume) {
            this.nume = nume;
        }

        @Override
        public String toString() {
            return this.nume;
        }
    }
    public Integer getId() {
        return id;
    }

    /**
     * @return ProposalStatus status
     */
    public proposalStatus getStatus() {
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
    public void setStatus(proposalStatus status) {
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
}