
package be.ehb.boekenwinkel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class SecurityConfig {

    public SecurityConfig() {
        System.out.println(">>> SECURITY CONFIG DEV CHARGEE <<<");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Console H2
                        .requestMatchers(
                                "/h2-console",
                                "/h2-console/**"
                        ).permitAll()

                        // Livres
                        .requestMatchers(
                                "/api/books",
                                "/api/books/**"
                        ).permitAll()

                        // Utilisateurs (mode developpement)
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/users/*/purchases"
                        ).permitAll()

                        // Reservations
                        .requestMatchers(
                                "/api/reservations",
                                "/api/reservations/**"
                        ).permitAll()

                        // Avis clients
                        .requestMatchers(
                                "/api/reviews",
                                "/api/reviews/**"
                        ).permitAll()

                        // Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers("/error").permitAll()

                        .anyRequest().authenticated()
                )

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/h2-console",
                                "/h2-console/**",
                                "/api/books",
                                "/api/books/**",
                                "/api/users/register",
                                "/api/users/login",
                                "/api/reservations",
                                "/api/reservations/**",
                                "/api/reviews",
                                "/api/reviews/**"
                        )
                )

                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                .formLogin(form -> form.permitAll());

        return http.build();
    }
}
