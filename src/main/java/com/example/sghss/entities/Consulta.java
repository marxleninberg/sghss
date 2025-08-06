package com.example.sghss.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entidade que representa uma consulta médica entre um paciente e um profissional de saúde.
 */
@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Associação com paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // Associação com profissional de saúde
    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalSaude profissional;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status;

    public Consulta() {}

    public Consulta(Paciente paciente, ProfissionalSaude profissional, LocalDateTime dataHora, StatusConsulta status) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.dataHora = dataHora;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public ProfissionalSaude getProfissional() {
        return profissional;
    }

    public void setProfissional(ProfissionalSaude profissional) {
        this.profissional = profissional;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }
}
