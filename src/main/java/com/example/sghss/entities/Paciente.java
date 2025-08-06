package com.example.sghss.entities;

import jakarta.persistence.*;

/**
 * Entidade que representa um paciente no sistema.
 * Cada paciente está associado a um usuário do tipo PACIENTE.
 */
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Associação com a tabela de usuários
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Campos específicos do paciente (expandível no futuro)

    public Paciente() {}

    public Paciente(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
