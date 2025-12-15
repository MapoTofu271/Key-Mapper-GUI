package org.example.service;

import org.example.key_mapper_gui.Main;
import org.example.model.Key;

public class KeyService {
    public void savedKey(Key key) {
        Main.keyMap.put(key.keyMapGenerated(), key);
    }
    public void deleteKey(Key key) {
        Main.keyMap.remove(key.keyMapGenerated());
    }
}
