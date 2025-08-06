package com.example.sghss.services;

import com.example.sghss.entities.*;
import com.example.sghss.repositories.ConsultaRepository;
import com.example.sghss.repositories.PacienteRepository;
import com.example.sghss.repositories.ProfissionalSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço responsável pelas regras de negócio de consultas médicas.
 */
@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalSaudeRepository profissionalRepository;

    @Autowired
    private LogService logService;

    /**
     * Agenda uma nova consulta entre um paciente e um profissional.
     */
    public Consulta agendarConsulta(Integer pacienteId, Integer profissionalId, LocalDateTime dataHora) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        ProfissionalSaude profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        Consulta consulta = new Consulta(paciente, profissional, dataHora, StatusConsulta.AGENDADA);
        Consulta consultaSalva = consultaRepository.save(consulta);

        // Obtém o e-mail do usuário logado diretamente (sem classe utilitária)
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        // Registra o log da ação
        logService.registrar(emailUsuario, "Agendamento de consulta ID " + consultaSalva.getId());

        return consultaSalva;
    }

    /**
     * Retorna todas as consultas cadastradas.
     */
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    /**
     * Busca uma consulta específica por ID.
     */
    public Optional<Consulta> buscarPorId(Integer id) {
        return consultaRepository.findById(id);
    }

    /**
     * Deleta uma consulta.
     */
    public void deletar(Integer id) {
        consultaRepository.deleteById(id);
    }

    /**
     * Lista as consultas de um determinado paciente.
     */
    public List<Consulta> listarPorPaciente(Integer pacienteId) {
        return consultaRepository.findByPacienteId(pacienteId);
    }

    /**
     * Lista as consultas de um profissional específico.
     */
    public List<Consulta> listarPorProfissional(Integer profissionalId) {
        return consultaRepository.findByProfissionalId(profissionalId);
    }
}
