package org.example.View;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import org.example.key_mapper_gui.Main;
import org.example.model.Key;
import org.example.model.KeyPos;

import java.util.function.Function;

interface ClickView {
    KeyPos Delta = new KeyPos(0,0);
    KeyPos eventPos = new KeyPos(0,0);
    default void initShape(Key key, Function<KeyPos, Shape> shapeFactory) {
//        Circle circle = new Circle();
//        circle.setFill(Color.WHITE);
//        circle.setCenterX(keyInitPos.getX());
//        circle.setCenterY(keyInitPos.getY());
//
//        circle.setRadius(40.0f);
//        circle.setCursor(Cursor.HAND);

        KeyPos keyInitPos = key.getPos().convertToRealCoordinates();
        Shape shape = shapeFactory.apply(key.getPos().convertToRealCoordinates());
        shape.setCursor(Cursor.HAND);
        shape.setFill(Color.WHITE);
        StackPane stackPane = new StackPane();
        Text textInCircle = new Text(key.getKey().get());
        textInCircle.textProperty().bind(key.getKey());
        stackPane.setLayoutX(keyInitPos.getX());
        stackPane.setLayoutY(keyInitPos.getY());
        stackPane.translateYProperty();
        stackPane.translateXProperty();
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleMouseDoubleClick();
            }
        });
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent clickEvent) {
                if (clickEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (clickEvent.getClickCount() == 2) {
                        handleMouseDoubleClick();
                    }
                }
                if (clickEvent.getButton() == MouseButton.SECONDARY) {
                    Node parent = stackPane.getParent();
                    if(parent instanceof Pane) {
                        ((Pane) parent).getChildren().remove(stackPane);
                    }
                }
            }
        });
        stackPane.setOnMousePressed(pressEvent -> {
            handleMousePress(pressEvent, stackPane);
        });
        stackPane.setOnMouseDragged(dragEvent -> {
            handleMouseDrag(key, stackPane, dragEvent);
        });
        stackPane.setOnMouseReleased(releaseEvent -> {
            handleMouseRelease(stackPane);
        });
        stackPane.getChildren().addAll(shape, textInCircle);
        stackPane.setMaxSize(((Circle) shape).getRadius(), ((Circle) shape).getRadius());
        Pane pane = getPane();
        pane.getChildren().add(stackPane);
    }
    void handleMouseDoubleClick();

    default void handleMouseDrag(Key key, StackPane target, MouseEvent dragEvent) {
        Delta.setX(dragEvent.getSceneX() - eventPos.getX());
        Delta.setY(dragEvent.getSceneY() - eventPos.getY());

        target.setTranslateX(target.getTranslateX() + Delta.getX());
        target.setTranslateY(target.getTranslateY() + Delta.getY());

        eventPos.setX(dragEvent.getSceneX());
        eventPos.setY(dragEvent.getSceneY());

        KeyPos newCoords = eventPos.convertToRatio(eventPos.getX(), eventPos.getY());
        key.setPos(newCoords);
        updateDragCoord();
    }
    default void handleMousePress(MouseEvent pressEvent, StackPane target) {
        eventPos.setX(pressEvent.getSceneX());
        eventPos.setY(pressEvent.getSceneY());
    }
    default void handleMouseRelease(StackPane target) {

    }
    void updateDragCoord();
    Pane getPane();
}

