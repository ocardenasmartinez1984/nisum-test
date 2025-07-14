package cl.cardenas.controllers.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginResponse {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Boolean isActive;
    private String token;

}
