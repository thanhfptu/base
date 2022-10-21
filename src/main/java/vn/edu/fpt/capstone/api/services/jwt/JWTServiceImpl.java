package vn.edu.fpt.capstone.api.services.jwt;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.api.configs.AppConfig;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service(JWTService.SERVICE_NAME)
public class JWTServiceImpl implements JWTService {

    private final AppConfig appConfig;

    public static final long EXPIRATION_TIME = 1L; // days

    @Override
    public String generateToken(String subject) {
        Signer signer = HMACSigner.newSHA512Signer(appConfig.getSecret());
        JWT jwt = new JWT()
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setExpiration(ZonedDateTime.now().plusDays(EXPIRATION_TIME))
                .setSubject(subject);
        return JWT.getEncoder().encode(jwt, signer);
    }

    @Override
    public Boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        Verifier verifier = HMACVerifier.newVerifier(appConfig.getSecret());
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        return jwt.isExpired();
    }

    @Override
    public LocalDateTime getExpirationDateFromToken(String token) {
        Verifier verifier = HMACVerifier.newVerifier(appConfig.getSecret());
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        return jwt.expiration.toLocalDateTime();
    }

    @Override
    public String getSubjectFromToken(String token) {
        Verifier verifier = HMACVerifier.newVerifier(appConfig.getSecret());
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        return jwt.subject;
    }

    @Override
    public LocalDateTime getIssuedAtFromToken(String token) {
        Verifier verifier = HMACVerifier.newVerifier(appConfig.getSecret());
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        return jwt.issuedAt.toLocalDateTime();
    }

}
