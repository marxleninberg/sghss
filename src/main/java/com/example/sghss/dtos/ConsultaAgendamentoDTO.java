package com.example.sghss.dtos;

public class ConsultaAgendamentoDTO {
    private Integer profissionalId;
    private String dataHora; // formato ISO 8601: "2025-08-10T14:00:00"

    // Getters e Setters
    public Integer getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Integer profissionalId) {
        this.profissionalId = profissionalId;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}
