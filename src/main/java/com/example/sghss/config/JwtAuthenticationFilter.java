package com.example.sghss.config;

import com.example.sghss.services.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Filtro personalizado que intercepta o POST /login,
 * realiza a autenticação e gera um token JWT em caso de sucesso.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final LogService logService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, LogService logService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.logService = logService;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            // Lê os dados do corpo da requisição (JSON com email e senha)
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);

            String email = credentials.get("email");
            String senha = credentials.get("senha");

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, senha);

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler as credenciais de login", e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String email = authResult.getName();
        String token = jwtUtil.generateToken(email);

        // Registra o login no log
        logService.registrar(email, "Login efetuado com sucesso");

        Map<String, String> body = new HashMap<>();
        body.put("token", token);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}
