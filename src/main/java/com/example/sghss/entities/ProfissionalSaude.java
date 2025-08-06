package com.example.sghss.entities;

import jakarta.persistence.*;

/**
 * Entidade que representa um profissional de saúde no sistema.
 * Cada profissional está vinculado a um usuário com role = PROFISSIONAL.
 */
@Entity
@Table(name = "profissionais_saude")
public class ProfissionalSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Associação com a tabela de usuários
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Informações específicas do profissional
    private String especialidade;

    private String registroProfissional;

    public ProfissionalSaude() {}

    public ProfissionalSaude(Usuario usuario, String especialidade, String registroProfissional) {
        this.usuario = usuario;
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
    }

    public Integer getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }
}
