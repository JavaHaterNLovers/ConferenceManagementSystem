package domain;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.File;
import java.util.Calendar;

/**
 *
 */

@Entity(name = "proposal")
public class Proposal
{

    @Column
    @Id
    @GeneratedValue
    private int id;


    @Column
    @NotBlank(message="{proposal.name}")
    private String name;


    @NotBlank(message="{proposal.user}")
    @ManyToOne
    private User user;


    @Column
    private String keywords;


    @Column
    private File file;


    @Column
    @NotBlank(message="{proposal.description}")
    private String description;


    @Column
    @NotBlank(message="{proposal.status}")
    private String status;


    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Calendar modified;


    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Calendar created;


    /**
     * Creeaza un proposal default.
     */

    public Proposal(){ this(null,null,null,null,null,null);}

    /**
     * Creeaza un proposal.
     *
     * @param id
     * @param name
     * @param user
     * @param keywords
     * @param file
     * @param description
     * @param status
     */
    public Proposal(String name, User user, String keywords, File file, String description, String status) {
        this.setName(name);
        this.setUser(user);
        this.setKeywords(keywords);
        this.setFile(file);
        this.setDescription(description);
        this.setStatus(status);
    }

    /**
     * Returneaza id.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Seteaza id.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
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
    public String getStatus() {
        return status;
    }

    /**
     * Seteaza status.
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
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
