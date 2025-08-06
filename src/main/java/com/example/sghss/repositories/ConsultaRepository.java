package com.example.sghss.repositories;

import com.example.sghss.entities.Consulta;
import com.example.sghss.entities.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório responsável pelo acesso à tabela de consultas.
 */
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    // Consultas por paciente
    List<Consulta> findByPacienteId(Integer pacienteId);

    // Consultas por profissional
    List<Consulta> findByProfissionalId(Integer profissionalId);

    List<Consulta> findByProfissional(ProfissionalSaude profissional);

    List<Consulta> findByProfissionalAndDataHoraBetween(ProfissionalSaude profissional,
                                                        LocalDateTime inicio,
                                                        LocalDateTime fim);
}
