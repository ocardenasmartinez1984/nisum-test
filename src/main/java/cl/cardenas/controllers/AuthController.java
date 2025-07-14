package cl.cardenas.controllers;

import cl.cardenas.controllers.entities.LoginRequest;
import cl.cardenas.controllers.entities.LoginResponse;
import cl.cardenas.controllers.entities.StatusResponse;
import cl.cardenas.controllers.entities.UserRequest;
import cl.cardenas.exceptions.EmailNotMatchException;
import cl.cardenas.exceptions.InvalidPasswordException;
import cl.cardenas.servicies.AuthService;
import cl.cardenas.utilities.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${validation.email}")
    private String emailRegex;
    @Value("${validation.password}")
    private String passwordRegex;

    private final AuthService authService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusResponse> register(@RequestBody UserRequest userRequest) {
        if(!Pattern.matches(emailRegex, userRequest.getEmail())) throw new EmailNotMatchException();
        if(!Pattern.matches(passwordRegex, userRequest.getPassword())) throw new InvalidPasswordException();
        authService.signUp(Mapper.userRequestToUser(userRequest));
        return new ResponseEntity<>(StatusResponse.builder().message("User Created").build(), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var user = authService.login(Mapper.loginRequestToLogin(loginRequest));
        return new ResponseEntity<>(Mapper.userToLoginResponse(user), HttpStatus.OK);
    }

}
