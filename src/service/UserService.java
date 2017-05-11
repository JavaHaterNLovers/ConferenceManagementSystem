package service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import domain.User;
import domain.User.UserRole;
import repo.UserRepository;

public class UserService extends BaseDomainService<User, UserRepository> implements UserDetailsService
{
    public UserService(UserRepository repo) {
        super(repo);
    }

    public User add(User user) {
        user.setPassword(this.encodePassword(user.getPassword()));
        user.setRol(UserRole.user);

        this.save(user);

        return user;
    }

    /**
     * Encripteaza parola folosind bcrypt.
     *
     * @param password
     * @return
     */
    public String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.repo.getByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User sau parola incorecta");
        }

        return user;
    }
}
