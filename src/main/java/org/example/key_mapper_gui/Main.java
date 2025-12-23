package org.example.key_mapper_gui;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;
import org.example.View.KMTClickMultiViewImpl;
import org.example.View.KMTClickViewImpl;
import org.example.View.KeyViewImpl;
import org.example.key_mapper_gui.viewModel.KMTClickMultiViewModel;
import org.example.key_mapper_gui.viewModel.KMTClickViewModel;
import org.example.key_mapper_gui.viewModel.KeyViewModel;
import org.example.model.KMTClick;
import org.example.model.KMTClickMulti;
import org.example.model.Key;
import org.example.service.KeyExportService;
import org.example.service.KeyLoadService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    public static final long SCREEN_MAX_HEIGHT = 900;
    public static final long SCREEN_MAX_WIDTH = 1600;
    public static final HashMap<String, Key> keyMap = new HashMap<>();
    private SplitPane appPane = new SplitPane();
    private Pane keyField = new Pane();
    private static VBox keyDetails;
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent(), SCREEN_MAX_WIDTH,SCREEN_MAX_HEIGHT);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    private Region createContent() {
//        appPane.setStyle("-fx-background-color: transparent");
//        appPane.setOrientation(Orientation.VERTICAL);
//        appPane.getItems().addAll(createMenu(), keyField);
//
//        appPane.setDividerPosition(0, 0.03);
        keyField.getChildren().add(createMenu());
        keyField.setStyle("-fx-background-color: transparent");
        keyField.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/org/example/key_mapper_gui/css/key_mapper.css")).toExternalForm());
        return keyField;
    }

    private Node createMenu() {
        HBox menu = new HBox(10, addKMTClickButton(), addKMTClickMultiButton(), exportDataButton(), loadKeyDataButton(), deleteAllKeysButton());
        menu.setMaxHeight(50);
        menu.setMaxWidth(500);
        menu.getStyleClass().add("menu");
        menu.setAlignment(Pos.CENTER);
        menu.setStyle("-fx-background-color: #000000;");
        return menu;
    }

    private Node createDetailsPane(KeyViewModel keyViewModel) {
        VBox pane = new VBox(6);
        pane.toFront();
        //Set bind with the model position
        pane.layoutXProperty().bind(keyViewModel.getxLayout());
        pane.layoutYProperty().bind(keyViewModel.getyLayout());
        System.out.println(pane.getLayoutX());
        System.out.println(pane.getLayoutY());
        pane.setStyle("-fx-background-color: white");
        TextField commentField = new TextField();
        commentField.textProperty().bindBidirectional(keyViewModel.getKeyComment());
        TextField keyAssignField = new TextField();
        keyAssignField.textProperty().bindBidirectional(keyViewModel.getKeyAssigned());
        pane.getChildren().add(new HBox(new Label("Comment: "), commentField));
        pane.getChildren().add(new HBox(new Label("Key    : "), keyAssignField));
        pane.visibleProperty().bind(keyViewModel.getVisibility());
        pane.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                keyViewModel.hideDetailsMenu();
            }
        });
//        pane.getChildren().add(addAnotherField(keyViewModel));
        addAnotherField(pane, keyViewModel);
        Button savedButton = new Button("Save");
        Button deletedButton = new Button("Delete");
        pane.getChildren().addAll(savedButton, deletedButton);
        savedButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                keyViewModel.hideDetailsMenu();
            }
        });
        deletedButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for(Node node : pane.getChildren()) {
                    if(node instanceof HBox) {
                        if(((HBox) node).getChildren().getLast() instanceof TextField) {
                            ((TextField) ((HBox) node).getChildren().getLast()).textProperty().set("");
                        }
                    }
                }
            }
        });
        return pane;
    }

    private Node addKMTClickButton() {
        Button addButton = new Button("Add KMTClick");
        addButton.getStyleClass().add("menu-button");
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                KMTClickViewImpl kmtClickView = new KMTClickViewImpl(new KMTClickViewModel(new KMTClick()));
                kmtClickView.getKeyViewModel().viewModelInit();
                kmtClickView.viewInit();
                keyDetails = (VBox) createDetailsPane(kmtClickView.getKeyViewModel());
                keyField.getChildren().addAll(kmtClickView, keyDetails);
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
                KMTClickMultiViewImpl kmtClickMultiView = new KMTClickMultiViewImpl(new KMTClickMultiViewModel(new KMTClickMulti()));
                kmtClickMultiView.getKeyViewModel().viewModelInit();
                kmtClickMultiView.viewInit();
                keyDetails = (VBox) createDetailsPane(kmtClickMultiView.getKeyViewModel());
                keyField.getChildren().addAll(kmtClickMultiView, keyDetails);            }
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
                        KMTClickViewImpl kmtClickView = new KMTClickViewImpl(new KMTClickViewModel(click));
                        createKey(kmtClickView);
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
        deleteAllButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                final List<Node> deleteKey = new ArrayList<>();
                for(Node node : keyField.getChildren()) {
                    if (node instanceof KeyViewImpl) {
                        deleteKey.add(node);
                    }
                }
                keyField.getChildren().removeAll(deleteKey);
            }
        });
        return deleteAllButton;
    }
    private void addAnotherField(VBox vBox, KeyViewModel viewModel) {
        if(viewModel instanceof KMTClickMultiViewModel) {
            TextField delayField = new TextField();
            delayField.textProperty().bindBidirectional(((KMTClickMultiViewModel) viewModel).getDelay(), new NumberStringConverter());
            vBox.getChildren().add(new HBox(new Label("Delay: "), delayField));
        }
        if(viewModel instanceof KMTClickViewModel) {
        }
    }
    public void createKey(KeyViewImpl keyView) {
        keyView.getKeyViewModel().viewModelInit();
        keyView.viewInit();
        keyDetails = (VBox) createDetailsPane(keyView.getKeyViewModel());
        keyField.getChildren().addAll(keyView, keyDetails);
    }
}