package com.example.sghss.dtos;

/**
 * DTO para cadastro completo de um profissional de saúde, incluindo dados do usuário.
 */
public class ProfissionalCompletoDTO {

    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String especialidade;
    private String registroProfissional;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getRegistroProfissional() { return registroProfissional; }
    public void setRegistroProfissional(String registroProfissional) { this.registroProfissional = registroProfissional; }
}
