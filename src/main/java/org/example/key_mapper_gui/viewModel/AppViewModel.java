package org.example.key_mapper_gui.viewModel;

import org.example.key_mapper_gui.repository.KeyStorage;
import org.example.model.Key;
import org.example.service.KeyService;

import java.io.IOException;
import java.util.UUID;

public class AppViewModel {
    private final KeyService keyService;
    public AppViewModel() {
        this.keyService = new KeyService();
    }
    public void deleteAllButton() {
        keyService.deleteAll();
    }
    public void printData() throws IOException {
        System.out.println(keyService.getDataSize());
        keyService.printData();
    }
    public Key[] loadKeyData() throws IOException {
        return keyService.loadKeyFromFile();
    }
    public void exportData() throws IOException {
        keyService.exportToJSONFile();
    }
}
