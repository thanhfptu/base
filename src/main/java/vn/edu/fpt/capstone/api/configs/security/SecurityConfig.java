package vn.edu.fpt.capstone.api.configs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import vn.edu.fpt.capstone.api.constants.AppRole;
import vn.edu.fpt.capstone.api.filters.JWTAuthenticationEntryPoint;
import vn.edu.fpt.capstone.api.filters.JWTAuthorizationEntryPoint;
import vn.edu.fpt.capstone.api.filters.JWTRequestFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    protected static final String[] WHITELIST_URLS = new String[]{"/v1/**"};
    protected static final String[] ADMIN_URLS = {"/admin/v1/**"};
    protected static final String[] MANAGE_URLS = {"/manage/v1/**"};
    protected static final String[] STUDENT_URLS = {"/student/v1/**"};
    protected static final String[] LINE_MANAGER_URLS = {"/evaluation/v1/**"};
    protected static final List<String> ALLOWED_METHODS = List.of(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name());

    private final JWTRequestFilter jwtRequestFilter;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JWTAuthorizationEntryPoint jwtAuthorizationEntryPoint;

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods(ALLOWED_METHODS);
        config.applyPermitDefaultValues();
        return config;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(request -> corsConfiguration());
        http.authorizeRequests()
                .antMatchers(WHITELIST_URLS).permitAll()
                .antMatchers(ADMIN_URLS).hasAuthority(AppRole.ADMIN)
                .antMatchers(MANAGE_URLS).hasAnyAuthority(AppRole.ADMIN, AppRole.STAFF)
                .antMatchers(STUDENT_URLS).hasAnyAuthority(AppRole.STUDENT)
                .antMatchers(LINE_MANAGER_URLS).hasAnyAuthority(AppRole.LINE_MANAGER)
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAuthorizationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
