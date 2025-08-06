package com.example.sghss.controllers;

import com.example.sghss.entities.Paciente;
import com.example.sghss.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsável por expor os endpoints REST da entidade Paciente.
 */
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    /**
     * Cria um novo paciente com base no ID de um usuário existente.
     * Esse usuário deve ter o perfil PACIENTE.
     */
    @PostMapping("/{usuarioId}")
    public ResponseEntity<Paciente> criarPaciente(@PathVariable int usuarioId) {
        Paciente paciente = pacienteService.criarPaciente(usuarioId);
        return ResponseEntity.ok(paciente);
    }

    /**
     * Lista todos os pacientes cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    /**
     * Busca um paciente específico pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable int id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        return paciente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deleta um paciente pelo ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        pacienteService.deletarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
