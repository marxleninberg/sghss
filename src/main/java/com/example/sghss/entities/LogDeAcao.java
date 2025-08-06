package com.example.sghss.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LogDeAcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario; // email ou identificador do usuário que realizou a ação

    private String acao; // descrição da ação realizada

    private LocalDateTime dataHora;

    public LogDeAcao() {
        this.dataHora = LocalDateTime.now();
    }

    public LogDeAcao(String usuario, String acao) {
        this.usuario = usuario;
        this.acao = acao;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAcao() {
        return acao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
