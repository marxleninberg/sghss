package com.example.sghss.repositories;

import com.example.sghss.entities.Paciente;
import com.example.sghss.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório responsável pelo acesso à tabela de pacientes.
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    // Exemplo de método adicional: buscar paciente por ID de usuário
    Optional<Paciente> findByUsuario(Usuario usuario);
}
