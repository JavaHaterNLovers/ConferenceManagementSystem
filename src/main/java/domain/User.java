package domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="users")
public class User
{
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    @NotBlank
    private String nume;

    @Column
    @NotBlank
    private String username;

    @Column
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole rol;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Calendar created;

    /**
     * Creeaza un nou user.
     */
    public User() {
        this(null, null, null, null);
    }

    /**
     * Creeaza un nou user cu un id, nume, username, parola si rol.
     *
     * @param id
     * @param nume
     * @param username
     * @param password
     * @param rol
     */
    public User(String nume, String username, String password, UserRole rol) {
        super();
        this.id = null;
        this.nume = nume;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.created = Calendar.getInstance();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returneaza numele.
     *
     * @return
     */
    public String getNume() {
        return nume;
    }

    /**
     * Seteaza numele.
     *
     * @param nume
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Returneaza username-ul.
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Seteaza username-ul.
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returneaza parola.
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Seteaza parola.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returneaza rolul userului.
     *
     * @return
     */
    public UserRole getRol() {
        return rol;
    }

    /**
     * Seteaza rolul userului.
     *
     * @param rol
     */
    public void setRol(UserRole rol) {
        this.rol = rol;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", nume=" + nume + ", username=" + username + ", password=" + password + ", rol="
                + rol + ", created=" + created + "]";
    }

    public static enum UserRole {
        admin("Administrator"),
        moderator("Moderator"),
        user("User");

        /**
         * Numele rolului.
         */
        private String nume;

        /**
         * Creeaza un nou rol cu un nume.
         * @param nume
         */
        UserRole(String nume) {
            this.nume = nume;
        }

        @Override
        public String toString() {
            return this.nume;
        }
    }
}
