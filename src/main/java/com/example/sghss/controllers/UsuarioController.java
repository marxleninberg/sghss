package com.example.sghss.controllers;

import com.example.sghss.dtos.ProfissionalCompletoDTO;
import com.example.sghss.entities.ProfissionalSaude;
import com.example.sghss.entities.Role;
import com.example.sghss.entities.Usuario;
import com.example.sghss.repositories.ProfissionalSaudeRepository;
import com.example.sghss.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProfissionalSaudeRepository profissionalRepository;

    // Endpoint POST para cadastrar um novo usuário
    @PostMapping
    public Usuario salvarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    // Endpoint GET para listar todos os usuários
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    @PostMapping("/profissional")
    public ResponseEntity<?> cadastrarProfissionalCompleto(@RequestBody ProfissionalCompletoDTO dto) {
        // Verifica se e-mail ou CPF já estão cadastrados
        if (usuarioService.emailOuCpfExiste(dto.getEmail(), dto.getCpf())) {
            return ResponseEntity.badRequest().body("E-mail ou CPF já cadastrado.");
        }

        // Cria e salva o usuário com role PROFISSIONAL
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha()); // Será criptografada no service
        usuario.setRole(Role.PROFISSIONAL);

        Usuario novoUsuario = usuarioService.salvar(usuario);

        // Cria e salva o profissional vinculado ao usuário
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setUsuario(novoUsuario);
        profissional.setEspecialidade(dto.getEspecialidade());
        profissional.setRegistroProfissional(dto.getRegistroProfissional());

        profissionalRepository.save(profissional);

        // Retorna os dados do profissional criado
        return ResponseEntity.ok(profissional);
    }

}
