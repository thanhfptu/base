package vn.edu.fpt.capstone.api.services.firebase;

import com.google.firebase.auth.FirebaseToken;

import java.util.Map;

public interface FirebaseService {

    String SERVICE_NAME = "FirebaseService";

    FirebaseToken of(String accessToken);

    Map<String, Object> getClaims(String accessToken);

    Map<String, Object> getClaims(FirebaseToken token);

    boolean isTokenValid(String accessToken);

}
