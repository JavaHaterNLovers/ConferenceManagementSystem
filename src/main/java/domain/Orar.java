package domain;

import javax.persistence.*;
import java.util.Calendar;

@Entity(name = "proposal")
public class Orar
{
    /**
     * Creeaza un orar default.
     */
    private Orar() {this(null, null);}

    /**
     * Creeza un Orar
     * @param beginDate
     * @param endDate
     */
    private Orar(Calendar beginDate, Calendar endDate)
    {
        super();
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Calendar beginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Calendar endDate;

    @Column
    @Id
    @GeneratedValue
    private int id;

    /**
     * Returneaza id.
     * @return
     */
    public int getId() {
        return id;
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