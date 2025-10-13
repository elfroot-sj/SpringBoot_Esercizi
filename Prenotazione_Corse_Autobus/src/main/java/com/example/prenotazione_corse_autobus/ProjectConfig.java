package com.example.prenotazione_corse_autobus;
//Security configuration


import com.example.prenotazione_corse_autobus.security.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // per @PreAuthorize, ecc.
//@RequiredArgsConstructor
public class ProjectConfig {

    private final ProjectUserDetailsService userDetailsService;
    private final AuthTokenFilter authTokenFilter;

    public ProjectConfig(ProjectUserDetailsService userDetailsService, AuthTokenFilter authTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.authTokenFilter = authTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/re-auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/trips/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/trips/*/buy").authenticated()
                        .requestMatchers(HttpMethod.POST, "/trips").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                //.httpBasic(Customizer.withDefaults())
                .userDetailsService(userDetailsService)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Serve per @Autowired AuthenticationManager (es. per login manuale)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
