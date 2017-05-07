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

@Entity
@Table(name = "orare")
public class Orar
{
    @Column
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name="proposal_id")
    @NotNull(message="{orar.proposal}")
    private Proposal proposal;

    @ManyToOne
    @JoinColumn(name="section_id")
    @NotNull(message="{orar.section}")
    private Section section;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{orar.begin-date}")
    private Calendar beginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{orar.end-date}")
    private Calendar endDate;

    /**
     * Creeaza un orar default.
     */
    private Orar() {
        this(null, null, null, null);
    }

    /**
     * Creeza un Orar
     * @param beginDate
     * @param endDate
     */
    private Orar(Proposal proposal, Section section, Calendar beginDate, Calendar endDate)
    {
        super();
        this.proposal = proposal;
        this.section = section;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    /**
     * Returneaza id.
     * @return
     */
    public int getId() {
        return id;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
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