package cl.cardenas.controllers.entities;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class ErrorDetailsResponse {

    private String message;

}
