package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.KMTClick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class KeyLoadService {
    public static KMTClick[] loadKeyFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(new File("src/main/resources/dummyData.json")
                , KMTClick[].class);
    }
}
