package cl.cardenas.servicies;

import cl.cardenas.configs.JwtTokenProvider;
import cl.cardenas.exceptions.UserAlreadyExistException;
import cl.cardenas.repositories.UserRepository;
import cl.cardenas.servicies.entities.Login;
import cl.cardenas.servicies.entities.User;
import cl.cardenas.utilities.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public void signUp(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) throw new UserAlreadyExistException();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setIsActive(true);
        user.setModified(LocalDateTime.now());
        userRepository.save(Mapper.userToUserEntity(user));
    }

    public User login(Login login) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = jwtTokenProvider.generateToken(authentication);
        var userEntity = userRepository.findByEmail(login.getEmail()).orElseThrow();
        userEntity.setToken(token);
        userEntity.setModified(LocalDateTime.now());
        return Mapper.userEntityToUser(userRepository.save(userEntity));
    }

}
