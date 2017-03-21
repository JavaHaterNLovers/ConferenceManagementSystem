package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User
{
    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column
    private String nume;
    @Column
    private String username;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole rol;

    /**
     * Creeaza un nou user.
     */
    public User() {
        this(-1, null, null, null, null);
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
    public User(int id, String nume, String username, String password, UserRole rol) {
        super();
        this.id = id;
        this.nume = nume;
        this.username = username;
        this.password = password;
        this.rol = rol;
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
