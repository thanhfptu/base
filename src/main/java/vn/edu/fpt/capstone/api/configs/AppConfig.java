package vn.edu.fpt.capstone.api.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        servers = {
                @Server(url = "https://api.foms.me", description = "Production"),
                @Server(url = "http://localhost:9090", description = "Local")
        },
        info = @Info(title = "FOMS API", version = "v1")
)
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private String secret;
    private Long readTimeout = 30L; // Seconds
    private Long connectTimeout = 30L; // Seconds

    public static final String V1_PATH = "/v1";
    public static final String ADMIN_PATH = "/admin";
    public static final String MANAGE_PATH = "/manage";
    public static final String STUDENT_PATH = "/student";

}
