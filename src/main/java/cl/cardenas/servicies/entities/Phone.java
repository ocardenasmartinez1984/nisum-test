package cl.cardenas.servicies.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Phone {

    private String number;
    private String cityCode;
    private String countryCode;

}
