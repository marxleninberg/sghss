package com.example.sghss.dtos;

import java.time.LocalDateTime;

public class ConsultaUpdateDTO {
    private LocalDateTime dataHora;
    private String status;

    // Getters e setters
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
