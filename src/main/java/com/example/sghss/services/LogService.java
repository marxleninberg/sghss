package com.example.sghss.services;

import com.example.sghss.entities.LogDeAcao;
import com.example.sghss.repositories.LogDeAcaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    private LogDeAcaoRepository logRepo;

    public void registrar(String usuario, String acao) {
        LogDeAcao log = new LogDeAcao(usuario, acao);
        logRepo.save(log);
    }
}
