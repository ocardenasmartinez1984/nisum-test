package cl.cardenas.controllers.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private List<PhoneRequest> phones;

}
