package service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.validation.ValidationException;

import domain.User;
import domain.User.UserRole;
import repo.UserRepository;

public class UserService extends BaseDomainService<User, UserRepository>
{
    private User currentUser;

    public UserService(UserRepository repo) {
        super(repo);

        this.currentUser = null;
    }

    public User add(String email, String pass, String nume, String prenume, UserRole rol) throws ValidationException {
        User user = new User(email, this.encodePassword(pass), nume, prenume, rol);
        this.save(user);

        return user;
    }

    /**
     * Verifica daca exista un user cu datele date.
     *
     * @param username
     * @param pass
     * @return
     */
    public User validUser(String email, String pass) {
        User user = this.repo.getByEmail(email);

        if (user == null) {
            return null;
        }

        String generatedPassword = this.encodePassword(pass);

        if (generatedPassword.trim().equals(user.getPassword().trim())) {
            return user;
        }

        return null;
    }

    /**
     * Encripteaza parola folosind SHA-512.
     *
     * @param password
     * @return
     */
    public String encodePassword(String password) {
        if (password.isEmpty()) {
            return password;
        }

        MessageDigest msg;
        try {
            msg = MessageDigest.getInstance("SHA-512");
            byte[] bytes = msg.digest(password.getBytes("UTF-8"));

            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returneaza userul curent.
     *
     * @return
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Seteaza userul curent.
     *
     * @param user
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
