package com.example.sghss.services;

import com.example.sghss.entities.Paciente;
import com.example.sghss.entities.Usuario;
import com.example.sghss.repositories.PacienteRepository;
import com.example.sghss.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço responsável pelas regras de negócio relacionadas ao paciente.
 */
@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cadastra um novo paciente vinculado a um usuário já existente.
     */
    public Paciente criarPaciente(int usuarioId) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);

        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com ID: " + usuarioId);
        }

        Paciente paciente = new Paciente(usuario.get());
        return pacienteRepository.save(paciente);
    }

    /**
     * Lista todos os pacientes do sistema.
     */
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    /**
     * Busca um paciente pelo ID.
     */
    public Optional<Paciente> buscarPorId(int id) {
        return pacienteRepository.findById(id);
    }

    /**
     * Remove um paciente pelo ID.
     */
    public void deletarPaciente(int id) {
        pacienteRepository.deleteById(id);
    }

    /**
     * Cadastro novo de paciente
     */
    public Paciente cadastrarAutomaticamente(Usuario usuario) {
        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        return pacienteRepository.save(paciente);
    }
}
