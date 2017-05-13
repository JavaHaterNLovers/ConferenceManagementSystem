package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="sessions")
public class Session
{
    @Id
    @Column
    @GeneratedValue
    private Integer id;

    @Column
    @NotBlank(message="{session.name}")
    private String name;

    @Column
    @NotBlank(message = "{session.room}")
    private String room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Edition edition;

    public Session() {
        this(null, null, null, null);
    }

    public Session(String name, String room, User user, Edition conference) {
        this.id = null;
        this.name = name;
        this.room = room;
        this.user = user;
        this.edition = conference;
    }
    /**
     * Returneaza id-ul.
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Returneaza numele
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Seteaza numele
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returneaza camera
     * @return
     */
    public String getRoom() {
        return room;
    }

    /**
     * Seteaza camera
     * @param room
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Returneaza user-ul
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Seteaza userul
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition conference) {
        this.edition = conference;
    }
}