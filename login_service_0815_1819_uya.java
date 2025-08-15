// 代码生成时间: 2025-08-15 18:19:20
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

// Service class for user authentication
@Service
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Autowire UserRepository and PasswordEncoder
    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by username
        Optional<User> userOptional = userRepository.findByUsername(username);

        // If user is not found, throw UsernameNotFoundException
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User user = userOptional.get();
        // Check if user's password is encoded and encode it if not
        if (!user.getPassword().matches(".*\$.*\$.*\$.*")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User's password needs to be encoded");
        }

        // Return a UserDetails object
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(user.getRoles())
                .build();
    }

    // Method to authenticate user (not part of UserDetailsService)
    public boolean authenticate(String username, String password) {
        // Try to find user by username
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the provided password matches the encoded password
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
