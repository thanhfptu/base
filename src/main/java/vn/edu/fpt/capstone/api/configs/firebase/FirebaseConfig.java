package vn.edu.fpt.capstone.api.configs.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {

    private final FirebaseProperties firebaseProperties;

    @Bean
    public FirebaseOptions firebaseOptions() {
        String path = firebaseProperties.getGoogleCredentials();
        ClassPathResource credentials = new ClassPathResource(path);
        FirebaseOptions options = null;
        try (InputStream stream = credentials.getInputStream()) {
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .build();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return options;
    }

    @Bean
    public FirebaseApp firebaseApp(FirebaseOptions options) {
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp app) {
        return FirebaseAuth.getInstance(app);
    }

}
