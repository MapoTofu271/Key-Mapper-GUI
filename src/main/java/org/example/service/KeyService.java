package org.example.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.View.KeyViewImpl;
import org.example.key_mapper_gui.Main;
import org.example.key_mapper_gui.repository.KeyStorage;
import org.example.model.KMTClick;
import org.example.model.Key;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class KeyService{
    private final KeyStorage keyStorage;
    public KeyService() {
        this.keyStorage = KeyStorage.getInstance();
    }
    public void updateKey(Key key) {
        keyStorage.getKeyMapNodes().putIfAbsent(key.getKeyID(), key);
    }
    public void deleteKey(Key key) {
        keyStorage.getKeyMapNodes().remove(key.getKeyID());
    }
    public void deleteAll() {
        for(UUID keyId : keyStorage.getKeyMapNodes().keySet()) {
            keyStorage.getKeyMapNodes().remove(keyId);
        }
    }
    public int getDataSize() {
        return keyStorage.getKeyMapNodes().size();
    }
    public void exportToJSONFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Key> keyList = keyStorage.getKeyMapNodes().values()
                .stream()
                .toList();
        String content = objectMapper.writeValueAsString(keyList);
        Files.write(Path.of("src/main/resources/exportData.json"), content.getBytes());
    }
    public KMTClick[] loadKeyFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(new File("src/main/resources/dummyData.json")
                , KMTClick[].class);
    }
    public void printData() {
        for(UUID keyID : keyStorage.getKeyMapNodes().keySet()) {
            System.out.println(
                    keyStorage.getKeyMapNodes().get(keyID).getComment()
                            + " " + keyStorage.getKeyMapNodes().get(keyID).getKey());
        }
    }

}
