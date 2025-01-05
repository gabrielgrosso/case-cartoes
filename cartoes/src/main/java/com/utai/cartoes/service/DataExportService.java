package com.utai.cartoes.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DataExportService {

    public void exportDataToFile(List<String> data) {
        try (FileWriter writer = new FileWriter("C:\\Users\\nicol\\Desktop\\export\\export.csv", true)) {
            for (String row : data) {
                writer.write(row + "\n");
            }
            // Adicionar log para confirmar a exportação
            Logger.getLogger(DataExportService.class.getName()).info("Data exported successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
