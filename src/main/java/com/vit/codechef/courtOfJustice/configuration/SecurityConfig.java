package com.vit.codechef.courtOfJustice.configuration;

import com.vit.codechef.courtOfJustice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Security config.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    /**
     * Filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public Endpoints
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/case/all").authenticated() // All roles can view

                        // Defendant / Plaintiff only
                        .requestMatchers(HttpMethod.POST, "/case/submit").hasAnyAuthority("DEFENDANT", "PLAINTIFF")

                        // Judge Powers
                        .requestMatchers("/case/edit/**", "/case/delete/**", "/case/approve/**", "/case/reject/**").hasAuthority("JUDGE")

                        // Juror Powers
                        .requestMatchers("/jury/vote/**").hasAuthority("JUROR")

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add your JWT filter here

        return http.build();
    }

}
