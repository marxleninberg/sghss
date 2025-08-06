package com.example.sghss.services;

import com.example.sghss.entities.Role;
import com.example.sghss.entities.Usuario;
import com.example.sghss.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Cria automaticamente um usuário ADMIN ao iniciar o sistema, caso não exista.
 */
@Component
public class StartupAdminInitializer {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin() {
        String email = "admin@admin.com";

        // Verifica se já existe um usuário com esse e-mail
        if (usuarioRepository.findByEmail(email).isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setCpf("00000000000"); // CPF obrigatório (valor fictício)
            admin.setEmail(email);
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            usuarioRepository.save(admin);

            System.out.println("Usuario ADMIN criado com sucesso (email: admin@admin.com | senha: admin123)");
        } else {
            System.out.println("Usuario ADMIN ja existe. Nenhuma acao foi realizada.");
        }
    }
}
