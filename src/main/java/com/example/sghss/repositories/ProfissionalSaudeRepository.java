package com.example.sghss.repositories;

import com.example.sghss.entities.ProfissionalSaude;
import com.example.sghss.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório responsável pelo acesso à tabela de profissionais de saúde.
 */
@Repository
public interface ProfissionalSaudeRepository extends JpaRepository<ProfissionalSaude, Integer> {

    // Buscar profissional por usuário vinculado
    ProfissionalSaude findByUsuarioId(Integer usuarioId);

    // Busca profissional de saúde pelo usuário vinculado (autenticado)
    Optional<ProfissionalSaude> findByUsuario(Usuario usuario);
}
