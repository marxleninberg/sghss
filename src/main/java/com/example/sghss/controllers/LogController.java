package com.example.sghss.controllers;

import com.example.sghss.entities.LogDeAcao;
import com.example.sghss.repositories.LogDeAcaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogDeAcaoRepository logRepo;

    @GetMapping("/exportar")
    public ResponseEntity<InputStreamResource> exportarLogs() throws IOException {
        List<LogDeAcao> logs = logRepo.findAll();

        // Cria arquivo temporário
        File tempFile = File.createTempFile("logs_", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (LogDeAcao log : logs) {
                writer.write(String.format("[%s] %s - %s",
                        log.getDataHora(), log.getUsuario(), log.getAcao()));
                writer.newLine();
            }
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(tempFile));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logs.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}
