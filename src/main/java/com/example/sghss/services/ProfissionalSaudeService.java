package com.example.sghss.services;

import com.example.sghss.entities.ProfissionalSaude;
import com.example.sghss.entities.Usuario;
import com.example.sghss.repositories.ProfissionalSaudeRepository;
import com.example.sghss.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço responsável pelas regras de negócio
 * relacionadas ao Profissional de Saúde.
 */
@Service
public class ProfissionalSaudeService {

    @Autowired
    private ProfissionalSaudeRepository profissionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cria um novo profissional de saúde vinculado a um usuário existente.
     */
    public ProfissionalSaude criarProfissional(Integer usuarioId, String especialidade, String registroProfissional) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);

        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com ID: " + usuarioId);
        }

        ProfissionalSaude profissional = new ProfissionalSaude(usuario.get(), especialidade, registroProfissional);
        return profissionalRepository.save(profissional);
    }

    /**
     * Lista todos os profissionais cadastrados.
     */
    public List<ProfissionalSaude> listarTodos() {
        return profissionalRepository.findAll();
    }

    /**
     * Busca um profissional por ID.
     */
    public Optional<ProfissionalSaude> buscarPorId(Integer id) {
        return profissionalRepository.findById(id);
    }

    /**
     * Remove um profissional pelo ID.
     */
    public void deletar(Integer id) {
        profissionalRepository.deleteById(id);
    }
}
