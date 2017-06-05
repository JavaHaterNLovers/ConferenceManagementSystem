package domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orare")
public class Orar
{
    @Column
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name="proposal_id")
    private Proposal proposal;

    @ManyToOne
    @JoinColumn(name="section_id")
    @NotNull(message="{orar.section}")
    private Session section;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{orar.begin-date}")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Calendar beginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{orar.end-date}")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Calendar endDate;

    /**
     * Creeaza un orar default.
     */
    public Orar() {
        this(null, null, null, null);
    }

    /**
     * Creeza un Orar
     * @param beginDate
     * @param endDate
     */
    public Orar(Proposal proposal, Session section, Calendar beginDate, Calendar endDate)
    {
        super();
        this.id = null;
        this.proposal = proposal;
        this.section = section;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returneaza id.
     * @return
     */
    public Integer getId() {
        return id;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public Session getSection() {
        return section;
    }

    public void setSection(Session section) {
        this.section = section;
    }

    /**
     * Returneaza beginDate
     * @return
     */
    public Calendar getBeginDate() {
        return beginDate;
    }

    /**
     * Seteaza endDate.
     *
     * @param beginDate
     */
    public void setBeginDate(Calendar beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * Returneaza endDate
     * @return
     */
    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * Seteaza beginDate.
     *
     * @param endDate
     */
    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }


}