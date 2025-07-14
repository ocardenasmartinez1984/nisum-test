package cl.cardenas.controllers.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String userName;

}
