package vn.edu.fpt.capstone.api.services.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service(FirebaseService.SERVICE_NAME)
public class FirebaseServiceImpl implements FirebaseService {

    private final FirebaseAuth auth;

    @Override
    public FirebaseToken of(String accessToken) {
        FirebaseToken token = null;
        try {
            token = auth.verifyIdToken(accessToken, true);
        } catch (FirebaseAuthException e) {
            log.error("Token invalid, details: {}", e.getMessage());
        }
        return token;
    }

    @Override
    public Map<String, Object> getClaims(String accessToken) {
        FirebaseToken token = of(accessToken);
        return getClaims(token);
    }

    @Override
    public Map<String, Object> getClaims(FirebaseToken token) {
        return Objects.isNull(token) ? Collections.emptyMap() : token.getClaims();
    }

    @Override
    public boolean isTokenValid(String accessToken) {
        FirebaseToken token = of(accessToken);
        return Objects.nonNull(token);
    }

}
