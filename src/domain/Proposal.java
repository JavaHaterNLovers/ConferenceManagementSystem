package domain;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="proposals")
public class Proposal
{
    @Column
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Edition edition;

    @Column
    @NotBlank(message="{proposal.name}")
    private String name;

    /**
     * Daca un topic nu exista el va fi creat automat cand acest obiect va fi salvat.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @NotEmpty(message="{proposal.topics}")
    @Valid
    private List<Topic> topics = new ArrayList<>();

    @Column
    private String keywords;

    @Column
    private File file;

    @Column
    @Type(type="text")
    @NotBlank(message="{proposal.description}")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @UpdateTimestamp
    private Calendar modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @CreationTimestamp
    private Calendar created;

    public Proposal() {
        this(null, null, null, new ArrayList<Topic>(), null, null, null);
    }

    public Proposal(
        User user, Edition edition, String name, List<Topic> topics, String keywords, File file, String description
    ) {
        super();
        this.user = user;
        this.edition = edition;
        this.name = name;
        this.topics = topics;
        this.keywords = keywords;
        this.file = file;
        this.description = description;
    }

    /**
     * Returneaza id.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Returneaza .
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Seteaza user.
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    /**
     * Returneaza .
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Seteaza name.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    /**
     * Adauga un topic.
     *
     * @param topic
     */
    public void addTopic(Topic topic) {
        this.topics.add(topic);
    }

    /**
     * Sterge un topic.
     *
     * @param topic
     */
    public void removeTopic(Topic topic) {
        this.topics.remove(topic);
    }

    /**
     * Returneaza toate topicurile.
     *
     * @return
     */

    public List<Topic> getTopics() {
        return this.topics;
    }

    /**
     * Returneaza .
     * @return
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Seteaza keywords.
     *
     * @param keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * Returneaza .
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     * Seteaza file.
     *
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Returneaza .
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Seteaza description.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returneaza .
     * @return
     */
    public Calendar getModified() {
        return modified;
    }


    /**
     * Seteaza modifed.
     *
     * @param modified
     */
    public void setModified(Calendar modified) {
        this.modified = modified;
    }

    /**
     * Returneaza .
     * @return
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * Seteaza created.
     *
     * @param created
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

}
