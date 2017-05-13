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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="editions")
public class Edition
{
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @Column
    @NotBlank(message="{conference.name}")
    private String name;

    @ManyToOne
    @NotNull(message="{conference.conference}")
    private Conference conference;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{conference.begin-date}")
    private Calendar beginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{conference.end-date}")
    private Calendar endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{conference.begin-submissions}")
    private Calendar beginSubmissions;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{conference.end-submissions}")
    private Calendar endSubmissions;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{conference.end-bidding}")
    private Calendar endBidding;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @NotNull(message="{conference.end-review}")
    private Calendar endReview;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @CreationTimestamp
    private Calendar created;

    public Edition() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public Edition(
            User author, String name, Conference conference, Calendar beginDate, Calendar endDate,
            Calendar beginSubmissions, Calendar endSubmissions, Calendar endBidding, Calendar endReview
    ) {
        super();
        this.id = null;
        this.author = author;
        this.name = name;
        this.conference = conference;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.beginSubmissions = beginSubmissions;
        this.endSubmissions = endSubmissions;
        this.endBidding = endBidding;
        this.endReview = endReview;
    }

    public Integer getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Calendar getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getBeginSubmissions() {
        return beginSubmissions;
    }

    public void setBeginSubmissions(Calendar beginSubmissions) {
        this.beginSubmissions = beginSubmissions;
    }

    public Calendar getEndSubmissions() {
        return endSubmissions;
    }

    public void setEndSubmissions(Calendar endSubmissions) {
        this.endSubmissions = endSubmissions;
    }

    public Calendar getEndBidding() {
        return endBidding;
    }

    public void setEndBidding(Calendar endBidding) {
        this.endBidding = endBidding;
    }

    public Calendar getEndReview() {
        return endReview;
    }

    public void setEndReview(Calendar endReview) {
        this.endReview = endReview;
    }

    public Calendar getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return this.name;
    }
}