package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.key_mapper_gui.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class KeyExportService {
    public static void exportToJSONFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(Main.keyMap);
        Files.write(Path.of("src/main/resources/exportData.json"), content.getBytes());
    }
}
