package com.example.sghss.controllers;

import com.example.sghss.dtos.ConsultaAgendamentoDTO;
import com.example.sghss.dtos.ConsultaDTO;
import com.example.sghss.dtos.ConsultaUpdateDTO;
import com.example.sghss.entities.*;
import com.example.sghss.repositories.ConsultaRepository;
import com.example.sghss.repositories.PacienteRepository;
import com.example.sghss.repositories.ProfissionalSaudeRepository;
import com.example.sghss.repositories.UsuarioRepository;
import com.example.sghss.services.ConsultaService;
import com.example.sghss.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller responsável pelos endpoints REST relacionados a consultas.
 */
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalSaudeRepository profissionalRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private LogService logService;

    /**
     * Agenda uma nova consulta entre paciente e profissional.
     */
    @PostMapping
    public ResponseEntity<Consulta> criar(@RequestBody ConsultaDTO dto) {
        Consulta consulta = consultaService
                .agendarConsulta(dto.pacienteId, dto.profissionalId, dto.dataHora);
        return ResponseEntity.ok(consulta);
    }

    /**
     * Lista todas as consultas.
     */
    @GetMapping
    public ResponseEntity<List<Consulta>> listarTodas() {
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    /**
     * Busca uma consulta pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Integer id) {
        Optional<Consulta> consulta = consultaService.buscarPorId(id);
        return consulta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todas as consultas de um paciente específico.
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Consulta>> listarPorPaciente(@PathVariable Integer pacienteId) {
        return ResponseEntity.ok(consultaService.listarPorPaciente(pacienteId));
    }

    /**
     * Lista todas as consultas de um profissional específico.
     */
    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<Consulta>> listarPorProfissional(@PathVariable Integer profissionalId) {
        return ResponseEntity.ok(consultaService.listarPorProfissional(profissionalId));
    }

    @PostMapping("/agendar")
    public ResponseEntity<?> agendarConsulta(@RequestBody ConsultaAgendamentoDTO dto,
                                             Authentication authentication) {
        // Pega email do usuário logado via token
        String email = authentication.getName();

        // Busca usuário (paciente)
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Garante que é PACIENTE
        if (usuario.getRole() != Role.PACIENTE) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Apenas pacientes podem agendar consulta.");
        }

        // Busca entidade Paciente pelo usuário
        Paciente paciente = pacienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // Busca profissional
        ProfissionalSaude profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        // Converte dataHora
        LocalDateTime dataHora = LocalDateTime.parse(dto.getDataHora());

        // Cria e salva consulta
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);
        consulta.setDataHora(dataHora);
        consulta.setStatus(StatusConsulta.AGENDADA);

        consultaRepository.save(consulta);

        return ResponseEntity.ok(consulta);
    }

    @PutMapping("/cancelar/{id}")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<String> cancelarConsulta(@PathVariable int id, Authentication authentication) {
        String emailUsuario = authentication.getName();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(emailUsuario);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }

        Optional<Consulta> consultaOpt = consultaRepository.findById(id);
        if (consultaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Consulta consulta = consultaOpt.get();

        // Verifica se a consulta pertence ao paciente autenticado
        Optional<Paciente> pacienteOpt = pacienteRepository.findByUsuario(usuarioOpt.get());
        if (pacienteOpt.isEmpty() || !pacienteOpt.get().getId().equals(consulta.getPaciente().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não pode cancelar essa consulta");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);

        logService.registrar(
                SecurityContextHolder.getContext().getAuthentication().getName(),
                "Cancelamento de consulta ID " + consulta.getId()
        );

        return ResponseEntity.ok("Consulta cancelada com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarConsulta(
            @PathVariable Integer id,
            @RequestBody ConsultaUpdateDTO dto,
            Authentication authentication
    ) {
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Paciente paciente = pacienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        if (!consulta.getPaciente().equals(paciente)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para alterar esta consulta.");
        }

        if (dto.getDataHora() != null) {
            consulta.setDataHora(dto.getDataHora());
        }

        consultaRepository.save(consulta);

        logService.registrar(
                SecurityContextHolder.getContext().getAuthentication().getName(),
                "Reagendamento de consulta ID " + consulta.getId()
        );

        return ResponseEntity.ok(consulta);
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<Consulta>> relatorioConsultas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            Authentication authentication) {

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario == null || usuario.getRole() != Role.PROFISSIONAL) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ProfissionalSaude profissional = profissionalRepository.findByUsuario(usuario).orElse(null);
        if (profissional == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Consulta> consultas;
        if (data != null) {
            LocalDateTime inicio = data.atStartOfDay();
            LocalDateTime fim = inicio.plusDays(1);
            consultas = consultaRepository
                    .findByProfissionalAndDataHoraBetween(profissional, inicio, fim);
        } else {
            consultas = consultaRepository.findByProfissional(profissional);
        }

        return ResponseEntity.ok(consultas);
    }
}
