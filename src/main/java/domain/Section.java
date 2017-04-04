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

    public Section() {this(null,null);}

    public Section( String name, String room) {
        this.id = null;
        this.name = name;
        this.room = room;
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
}