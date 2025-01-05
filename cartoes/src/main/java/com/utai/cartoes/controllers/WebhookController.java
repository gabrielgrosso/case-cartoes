package com.utai.cartoes.controllers;

import com.utai.cartoes.service.DataExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    @Autowired
    private DataExportService dataExportService;

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        // Processar o payload conforme necessário
        List<String> changes = processPayload(payload);
        dataExportService.exportDataToFile(changes);
        return ResponseEntity.ok("Webhook handled");
    }

    private List<String> processPayload(Map<String, Object> payload) {
        // Processar o payload para extrair as informações necessárias
        // Este é um exemplo simplificado, você pode ajustar conforme suas necessidades
        List<String> changes = new ArrayList<>();
        changes.add(payload.get("operation") + "," + payload.get("cartao_id") + "," + payload.get("data"));
        return changes;
    }
}



