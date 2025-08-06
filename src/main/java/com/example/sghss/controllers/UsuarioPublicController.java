package com.example.sghss.controllers;

import com.example.sghss.entities.Paciente;
import com.example.sghss.entities.Role;
import com.example.sghss.entities.Usuario;
import com.example.sghss.services.PacienteService;
import com.example.sghss.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller público para cadastro de pacientes (sem autenticação)
 */
@RestController
@RequestMapping("/public")
public class UsuarioPublicController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> autoCadastro(@RequestBody Usuario usuario) {
        // Verifica se já existe e-mail ou CPF
        if (usuarioService.emailOuCpfExiste(usuario.getEmail(), usuario.getCpf())) {
            return ResponseEntity.badRequest().body("E-mail ou CPF já cadastrado.");
        }

        // Define role = PACIENTE (ignora qualquer outro valor enviado)
        usuario.setRole(Role.PACIENTE);

        // Salva usuário
        Usuario novoUsuario = usuarioService.salvar(usuario);

        // Cria paciente automaticamente
        Paciente paciente = pacienteService.cadastrarAutomaticamente(novoUsuario);

        return ResponseEntity.ok(paciente);
    }
}
