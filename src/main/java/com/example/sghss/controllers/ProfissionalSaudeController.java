package com.example.sghss.controllers;

import com.example.sghss.dtos.ProfissionalSaudeDTO;
import com.example.sghss.entities.ProfissionalSaude;
import com.example.sghss.services.ProfissionalSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsável pelos endpoints REST de Profissional de Saúde.
 */
@RestController
@RequestMapping("/profissionais")
public class ProfissionalSaudeController {

    @Autowired
    private ProfissionalSaudeService profissionalService;

    /**
     * Cadastra um novo profissional de saúde.
     * Requer um JSON contendo: usuarioId, especialidade e registroProfissional.
     */

    @PostMapping
    public ResponseEntity<ProfissionalSaude> criar(@RequestBody ProfissionalSaudeDTO dto) {
        ProfissionalSaude profissional = profissionalService
                .criarProfissional(dto.usuarioId, dto.especialidade, dto.registroProfissional);

        return ResponseEntity.ok(profissional);
    }

    /**
     * Lista todos os profissionais cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<ProfissionalSaude>> listarTodos() {
        return ResponseEntity.ok(profissionalService.listarTodos());
    }

    /**
     * Busca um profissional por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalSaude> buscarPorId(@PathVariable Integer id) {
        Optional<ProfissionalSaude> profissional = profissionalService.buscarPorId(id);
        return profissional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um profissional de saúde pelo ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        profissionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
