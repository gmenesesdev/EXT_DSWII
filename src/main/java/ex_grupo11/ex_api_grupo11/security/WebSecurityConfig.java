package ex_grupo11.ex_api_grupo11.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                // Permitir acceso público solo a operaciones de lectura específicas
                .requestMatchers("/album").authenticated()
                .requestMatchers("/album/{idAlbum}").authenticated()
                .requestMatchers("/lamina").authenticated()
                .requestMatchers("/lamina/{idLamina}").authenticated())
                .httpBasic(Customizer.withDefaults()) // Usar autenticación básica
                .csrf(csrf -> csrf.disable()); // Deshabilitar CSRF para facilitar pruebas
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Definir usuarios en memoria
        User.UserBuilder users = User.builder();

        UserDetails usuario = users
                .username("user")
                .password(passwordEncoder.encode("password123"))
                .build();

        return new InMemoryUserDetailsManager(usuario);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
