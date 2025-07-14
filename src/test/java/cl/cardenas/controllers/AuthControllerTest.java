package cl.cardenas.controllers;

import cl.cardenas.controllers.entities.LoginRequest;
import cl.cardenas.controllers.entities.LoginResponse;
import cl.cardenas.controllers.entities.StatusResponse;
import cl.cardenas.controllers.entities.UserRequest;
import cl.cardenas.exceptions.EmailNotMatchException;
import cl.cardenas.exceptions.InvalidPasswordException;
import cl.cardenas.servicies.AuthService;
import cl.cardenas.servicies.entities.Login;
import cl.cardenas.servicies.entities.User;
import cl.cardenas.utilities.Mapper;
import org.junit.jupiter.api.*;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        setPrivateField(authController, "emailRegex", "^[A-Za-z0-9+_.-]+@(.+)$");
        setPrivateField(authController, "passwordRegex", "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testRegisterValidInputReturnsCreated() {
        UserRequest request = UserRequest.builder()
                .email("test@example.com")
                .password("abc123")
                .build();
        User mockUser = User.builder().build();
        try (MockedStatic<Mapper> mapperMock = mockStatic(Mapper.class)) {
            mapperMock.when(() -> Mapper.userRequestToUser(request)).thenReturn(mockUser);
            ResponseEntity<StatusResponse> response = authController.register(request);
            assertEquals(201, response.getStatusCodeValue());
            assertEquals("User Created", response.getBody().getMessage());
            verify(authService).signUp(mockUser);
        }
    }

    @Test
    void testRegisterInvalidEmailThrowsEmailNotMatchException() {
        UserRequest request = UserRequest.builder()
                .email("invalid-email")
                .password("abc123")
                .build();
        assertThrows(EmailNotMatchException.class, () -> authController.register(request));
    }

    @Test
    void testRegisterInvalidPasswordThrowsInvalidPasswordException() {
        UserRequest request = UserRequest.builder()
                .email("test@example.com")
                .password("short")
                .build();
        assertThrows(InvalidPasswordException.class, () -> authController.register(request));
    }

    @Test
    void testLoginValidCredentialsReturnsLoginResponse() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("abc123")
                .build();
        Login mockLogin = Login.builder().build();
        User mockUser = User.builder().build();
        LoginResponse expectedResponse = LoginResponse.builder()
                .token("jwt-token")
                .build();
        try (MockedStatic<Mapper> mapperMock = mockStatic(Mapper.class)) {
            mapperMock.when(() -> Mapper.loginRequestToLogin(loginRequest)).thenReturn(mockLogin);
            mapperMock.when(() -> Mapper.userToLoginResponse(mockUser)).thenReturn(expectedResponse);
            when(authService.login(mockLogin)).thenReturn(mockUser);
            ResponseEntity<LoginResponse> response = authController.login(loginRequest);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("jwt-token", response.getBody().getToken());
        }
    }

    // Utilidad para setear campos privados como los @Value
    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
