package bartzmoveis.apigetitem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.betolara1.jwt_package.config.JwtProperties;
import com.betolara1.jwt_package.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtProperties jwtProperties;

    public SecurityConfig (JwtAuthFilter jwtAuthFilter, JwtProperties jwtProperties){
        this.jwtAuthFilter = jwtAuthFilter;
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // Converta a lista do .env para um array de Strings
        String[] paths = jwtProperties.getExcludedPaths().toArray(new String[0]);

        return http
                .csrf(csrf -> csrf.disable()) // Obrigatório para APIs REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sem sessão no servidor
                .authorizeHttpRequests(auth -> auth 
                    .requestMatchers(paths).permitAll() // 'paths' usa os caminhos do .env!
                    .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class).build();
    }

}
