package org.example.key_mapper_gui.repository;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.example.model.Key;

import java.util.HashMap;
import java.util.UUID;

public final class KeyStorage {
    private static KeyStorage instance;
    private final HashMap<UUID, Key> keyMapNodes = new HashMap<>();
    private KeyStorage() {}

    public static KeyStorage getInstance() {
        if(instance == null) {
            instance = new KeyStorage();
        }
        return instance;
    }
    public HashMap<UUID, Key> getKeyMapNodes() {
        return keyMapNodes;
    }

}
