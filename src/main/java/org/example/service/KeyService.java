package org.example.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.key_mapper_gui.repository.KeyStorage;
import org.example.model.KMTClick;
import org.example.model.Key;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
        keyStorage.getKeyMapNodes().clear();
//        Iterator<Map.Entry<UUID, Key>> it = keyStorage.getKeyMapNodes().entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry item = it.next();
//            it.remove();
//        }
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
