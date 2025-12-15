package org.example.key_mapper_gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.View.KMTCLickMultiViewImpl;
import org.example.View.KMTClickViewImpl;
import org.example.model.KMTClick;
import org.example.model.KMTClickMulti;
import org.example.model.Key;
import org.example.service.KeyExportService;
import org.example.service.KeyLoadService;


import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Main extends Application {
    public static final HashMap<String, Key> keyMap = new HashMap<>();
    public static final HashMap<Key, Node> nodeMap = new HashMap<>();
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent(), 1600,900);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
    public static Pane keyField = new Pane();

    private Region createContent() {
        keyField.getChildren().add(createMenu());
        keyField.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/org/example/key_mapper_gui/css/key_mapper.css")).toExternalForm());
        return keyField;
    }

    private Node createMenu() {
        HBox menu = new HBox(10, addKMTClickButton(), addKMTClickMultiButton(), exportDataButton(), loadKeyDataButton(), deleteAllKeysButton());
        menu.setPrefHeight(50);
        menu.setMaxWidth(500);
        menu.getStyleClass().add("menu");
        menu.setAlignment(Pos.CENTER);
        menu.setStyle("-fx-background-color: #000000;");
        return menu;
    }

    private Node addKMTClickButton() {
        Button addButton = new Button("Add KMTClick");
        addButton.getStyleClass().add("menu-button");
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                KMTClickViewImpl kmtClickView = new KMTClickViewImpl(new KMTClick());
                keyField.getChildren().add(kmtClickView.viewRender());
            }
        });
        return addButton;
    }
    private Node addKMTClickMultiButton() {
        Button addButton = new Button("Add KMTMulti Click");
        addButton.getStyleClass().add("menu-button");
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                KMTCLickMultiViewImpl kmtcLickMultiView = new KMTCLickMultiViewImpl(new KMTClickMulti());
                keyField.getChildren().add(kmtcLickMultiView.viewRender());
            }
        });
        return addButton;
    }
    private Node exportDataButton() {
        Button exportButton = new Button("Export");
        exportButton.getStyleClass().add("menu-button");
        exportButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    KeyExportService.exportToJSONFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return exportButton;
    }
    private Node loadKeyDataButton() {
        Button loadButton =  new Button("Load");
        loadButton.getStyleClass().add("menu-button");
        loadButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    KMTClick[] test = KeyLoadService.loadKeyFromFile();
                    for(KMTClick click : test) {
                        KMTClickViewImpl kmtClickView = new KMTClickViewImpl(click);
                        keyField.getChildren().add(kmtClickView.viewRender());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return loadButton;
    }
    private Node deleteAllKeysButton() {
        Button deleteAllButton = new Button("Delete");
        deleteAllButton.getStyleClass().add("menu-button");
        return deleteAllButton;
    }
}