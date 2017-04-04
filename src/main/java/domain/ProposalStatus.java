package domain;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.util.Calendar;

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

    public ProposalStatus(){
        this(null,null,null,null);
    }

    public ProposalStatus(String status, Calendar modified, Calendar created, String comment) {
        super();
        this.id = null;
        setStatus(status);
        setModified(modified);
        setCreated(created);
        setComment(comment);
    }

    /**
     * @return ProposalStatus identifier
     */
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
     * @param status
     * Set ProposalStatus status to the given value
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return ProposalStatus get modified date
     */
    public Calendar getModified() {
        return modified;
    }

    /**
     * @param modified
     * Set ProposalStatus modified date to the given value
     */
    public void setModified(Calendar modified) {
        this.modified = modified;
    }

    /**
     * @return ProposalStatus created date
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * @param created
     * Set ProposalStatus created date to the given value
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

    /**
     * @return ProposalStatus comments
     */
    public String getComment() {
        return comment;
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