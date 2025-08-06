package com.example.sghss.services;

import com.example.sghss.entities.Usuario;
import com.example.sghss.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // injeta o encoder

    public boolean emailOuCpfExiste(String email, String cpf) {
        return usuarioRepository.findByEmail(email).isPresent() ||
                usuarioRepository.findByCpf(cpf).isPresent();
    }

    @Autowired
    private LogService logService;

    // Salva o usuário com senha criptografada
    public Usuario salvar(Usuario usuario) {
        // Codifica a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        // Salva no banco
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Registra o log
        logService.registrar(usuarioSalvo.getEmail(), "Cadastro de novo usuário");

        return usuarioSalvo;
    }


    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}
