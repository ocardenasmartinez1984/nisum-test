package cl.cardenas.servicies;

import cl.cardenas.configs.JwtTokenProvider;
import cl.cardenas.exceptions.UserAlreadyExistException;
import cl.cardenas.repositories.UserRepository;
import cl.cardenas.repositories.entities.UserEntity;
import cl.cardenas.servicies.entities.Login;
import cl.cardenas.servicies.entities.User;
import cl.cardenas.utilities.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUpShouldSaveUserWhenEmailNotExists() {
        User user = User.builder()
                .email("test@example.com")
                .password("123456")
                .build();
        UserEntity mockEntity = new UserEntity();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPass");
        try (MockedStatic<Mapper> mockedMapper = Mockito.mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.userToUserEntity(any())).thenReturn(mockEntity);
            authService.signUp(user);
            assertEquals("encodedPass", user.getPassword());
            verify(userRepository).save(mockEntity);
        }
    }

    @Test
    void signUpShouldThrowExceptionWhenEmailExists() {
        User user = User.builder()
                .email("test@example.com")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(UserAlreadyExistException.class, () -> authService.signUp(user));
    }

    @Test
    void loginShouldAuthenticateAndReturnUser() {
        Login login = Login.builder()
                .email("test@example.com")
                .password("123456")
                .build();
        Authentication auth = mock(Authentication.class);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(login.getEmail());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(jwtTokenProvider.generateToken(auth)).thenReturn("token123");
        when(userRepository.findByEmail(login.getEmail())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        User expectedUser = User.builder()
                .email(login.getEmail())
                .build();
        try (MockedStatic<Mapper> mockedMapper = Mockito.mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.userEntityToUser(userEntity)).thenReturn(expectedUser);
            User result = authService.login(login);
            assertEquals("test@example.com", result.getEmail());
            verify(userRepository).save(userEntity);
        }
    }
}
