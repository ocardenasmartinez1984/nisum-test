package cl.cardenas.servicies;

import cl.cardenas.exceptions.UserNotFoundException;
import cl.cardenas.repositories.UserRepository;
import cl.cardenas.repositories.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
        String email = "test@example.com";
        String password = "hashedPassword";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void loadUserByUsernameShouldThrowExceptionWhenUserNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(email));
    }
}
