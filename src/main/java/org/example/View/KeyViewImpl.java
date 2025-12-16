package org.example.View;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import org.example.key_mapper_gui.viewModel.KeyViewModel;
import org.example.model.KeyPos;

public class KeyViewImpl extends StackPane{
    private final KeyViewModel keyViewModel;
    private VBox detailsPane;
    private KeyPos eventPos = new KeyPos(0, 0);
    private KeyPos Delta = new KeyPos(0, 0);

    public KeyViewImpl(KeyViewModel keyViewModel) {
        this.keyViewModel = keyViewModel;
        setUpUI();
        eventHandler();
    }
    private void setUpUI() {
        KeyPos viewModel = keyViewModel.getKeyPosObjectPropertyProperty().get();
        KeyPos realPos = KeyPos.convertToRealCoordinates(viewModel.getX(), viewModel.getY());
        Shape shape = keyViewModel.createShape(realPos);
        Text text = new Text();
        text.textProperty().bindBidirectional(keyViewModel.getKeyAssigned());
        this.setLayoutX(realPos.getX());
        this.setLayoutY(realPos.getY());
        this.getChildren().addAll(shape, text);
        detailsPane = createDetailsPane();
    }
    private void eventHandler() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if(mouseEvent.getClickCount() == 2) {
                        keyViewModel.showDetailsMenu();
                    }
                }
            }
        });
        this.setOnMousePressed(pressEvent -> {
            eventPos.setX(pressEvent.getSceneX());
            eventPos.setY(pressEvent.getSceneY());
        });
        this.setOnMouseDragged(dragEvent -> {
            Delta.setX(dragEvent.getSceneX() - eventPos.getX());
            Delta.setY(dragEvent.getSceneY() - eventPos.getY());

            this.setTranslateX(this.getTranslateX() + Delta.getX());
            this.setTranslateY(this.getTranslateY() + Delta.getY());

            eventPos.setX(dragEvent.getSceneX());
            eventPos.setY(dragEvent.getSceneY());
        });

    }
    public VBox createDetailsPane() {
        VBox pane = new VBox(6);
        pane.setMaxWidth(100);
        pane.setMaxHeight(50);
        TextField commentField = new TextField();
        commentField.textProperty().bindBidirectional(keyViewModel.getKeyComment());
        TextField keyAssignField = new TextField();
        keyAssignField.textProperty().bindBidirectional(keyViewModel.getKeyAssigned());

        pane.getChildren().add(new HBox(new Label("Comment: "), commentField));
        pane.getChildren().add(new HBox(new Label("Key: "), keyAssignField));
        pane.visibleProperty().bind(keyViewModel.getVisibility());

        return pane;
    }
}
