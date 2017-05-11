package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="users")
public class User implements UserDetails
{
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(unique = true)
    @NotBlank(message="{user.emailBlank}")
    @Email(message="{user.email}")
    private String email;

    @Column
    @Length(min = 5, message = "{user.password}")
    private String password;

    @Transient
    private String confirmPassword;

    @Column
    @NotBlank(message="{user.nume}")
    private String nume;

    @Column
    @NotBlank(message="{user.prenume}")
    private String prenume;

    @Enumerated(EnumType.STRING)
    private UserRole rol;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @CreationTimestamp
    private Calendar created;

    public User() {
        this(null, null, null, null, null);
    }

    public User(String email, String password, String nume, String prenume, UserRole rol) {
        super();
        this.id = null;
        this.email = email;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    /**
     * Returneaza parola.
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
        return this.nume + " " + this.prenume;
    }

    @AssertTrue(message="{user.confPass}")
    public boolean isPasswordConfirmed() {
        return this.password != null && this.confirmPassword != null
            && this.password.equals(this.confirmPassword);
    }

    public static enum UserRole {
        chair("ROLE_CHAIR"),
        coChair("ROLE_CO_CHAIR"),
        staff("ROLE_STAFF"),
        user("ROLE_USER"),
        superAdmin("ROLE_SUPER_ADMIN");

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();

        auths.add(new SimpleGrantedAuthority(this.getRol().toString()));

        // All users should also have the default user role
        if (this.getRol() != UserRole.user) {
            auths.add(new SimpleGrantedAuthority(UserRole.user.toString()));
        }

        return auths;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
