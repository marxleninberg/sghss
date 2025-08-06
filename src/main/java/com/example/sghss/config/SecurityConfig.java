package com.example.sghss.config;

import com.example.sghss.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * Configuração da segurança da aplicação.
 * Define regras de acesso, autenticação com JWT e criptografia.
 */
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    private LogService logService;


    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Define como os usuários serão autenticados com base no UserDetailsService.
     */
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Cria o gerenciador de autenticação usado pelo filtro de login.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Define a criptografia das senhas (BCrypt).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define as regras de segurança e aplica o filtro JWT.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(authManager, jwtUtil, logService);

        jwtFilter.setAuthenticationManager(authManager);

        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers("/consultas/relatorio").hasRole("PROFISSIONAL")
                        .requestMatchers(HttpMethod.GET, "/consultas/**").hasAnyRole("PACIENTE", "PROFISSIONAL")
                        .requestMatchers(HttpMethod.POST, "/consultas").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/consultas/**").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/consultas/cancelar/**").hasRole("PACIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/consultas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/usuarios/profissional").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/pacientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/pacientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/pacientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/logs/exportar").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .addFilter(jwtFilter)
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil, userDetailsService), JwtAuthenticationFilter.class)
                .build();
    }

}
