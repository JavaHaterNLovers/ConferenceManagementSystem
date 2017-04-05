package domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name="section")
public class Section
{


    @Column
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    @NotBlank(message="{section.name}")
    private String name;

    @Column
    @NotBlank(message = "{section.room}")
    private String room;

    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "SECTION_USER_ID_FK")
    )
    private User user;

    @ManyToOne
    @JoinColumn(name = "conference_id",
            foreignKey = @ForeignKey(name = "SECTION_CONFERENCE_ID_FK")
    )
    private Conference conference;

    public Section() {this(null,null,null,null);}

    public Section(String name, String room, User user, Conference conference) {
        this.id = null;
        this.name = name;
        this.room = room;
        this.user = user;
        this.conference = conference;
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

    /**
     * Returneaza conferinta
     * @return
     */
    public Conference getConference() {
        return conference;
    }

    /**
     * Seteaza conferinta
     * @param conference
     */
    public void setConference(Conference conference) {
        this.conference = conference;
    }
}