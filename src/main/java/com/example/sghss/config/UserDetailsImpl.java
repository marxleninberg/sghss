package com.example.sghss.config;

import com.example.sghss.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// Esta classe adapta o Usuario para o formato que o Spring Security entende
public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    // Retorna o papel (ROLE) do usuário
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()));
    }

    // A senha armazenada
    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    // O campo usado como "login", neste caso o email
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    // Os métodos abaixo podem ser ajustados, mas vamos deixar todos ativos por padrão
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
