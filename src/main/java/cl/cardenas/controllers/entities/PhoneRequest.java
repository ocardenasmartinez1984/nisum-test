package cl.cardenas.controllers.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneRequest {

    private String number;
    private String cityCode;
    private String countryCode;

}
