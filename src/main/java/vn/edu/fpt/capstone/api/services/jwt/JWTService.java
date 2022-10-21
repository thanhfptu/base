package vn.edu.fpt.capstone.api.services.jwt;

import java.time.LocalDateTime;

public interface JWTService {

    String SERVICE_NAME = "JWTService";

    String generateToken(String username);

    Boolean isTokenValid(String token);

    Boolean isTokenExpired(String token);

    LocalDateTime getExpirationDateFromToken(String token);

    String getSubjectFromToken(String token);

    LocalDateTime getIssuedAtFromToken(String token);

}
