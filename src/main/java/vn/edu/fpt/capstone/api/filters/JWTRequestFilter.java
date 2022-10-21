package vn.edu.fpt.capstone.api.filters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.edu.fpt.capstone.api.services.jwt.JWTService;
import vn.edu.fpt.capstone.api.services.otp.OTPService;
import vn.edu.fpt.capstone.api.services.user.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final OTPService otpService;
    private final UserService userService;
    private final FirebaseAuth firebaseAuth;

    private static final String EVALUATION_URI = "/evaluation/v1";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String uri = request.getRequestURI();
        String email = null;
        boolean isOTPAuth = uri.startsWith(EVALUATION_URI);
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            String accessToken = authorization.substring(7);
            if (isOTPAuth) {
                if (Boolean.TRUE.equals(jwtService.isTokenValid(accessToken))) {
                    email = Boolean.TRUE.equals(jwtService.isTokenValid(accessToken)) ? jwtService.getSubjectFromToken(accessToken) : null;
                }
            } else {
                try {
                    FirebaseToken token = firebaseAuth.verifyIdToken(accessToken, true);
                    email = token.getEmail();
                } catch (FirebaseAuthException e) {
                    log.trace("Token invalid, details: {}", e.getMessage());
                }
            }
        }

        if (StringUtils.hasText(email) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = isOTPAuth ? otpService.loadUserByUsername(email) : userService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
