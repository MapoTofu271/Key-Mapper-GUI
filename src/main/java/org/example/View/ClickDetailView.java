package org.example.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.model.Key;
import org.example.model.KeyDTO;
import org.example.model.KeyPos;

interface ClickDetailView {
    StringProperty comment = new SimpleStringProperty();
    StringProperty keyAssign = new SimpleStringProperty();
    void handleEnterClick();

    default VBox initDetailsView() {
        VBox menuPane = new VBox();
        menuPane.setPrefWidth(200);
        menuPane.setPrefHeight(100);
        menuPane.setVisible(false);
        menuPane.setStyle("-fx-background-color: white;");
        menuPane.setOnMouseDragged(dragEvent -> {
            menuPane.setLayoutX(dragEvent.getSceneX());
            menuPane.setLayoutY(dragEvent.getSceneY());
        });
        menuPane.setOnKeyPressed(key -> {
            if(key.getCode() == KeyCode.ENTER) {
                handleEnterClick();
            }
        });
        menuPane.getChildren().add(initMenuComponentsView());
        return menuPane;
    }
    default VBox initMenuComponentsView() {
        VBox childView = new VBox(6);
        childView.getChildren().add(createCommentBox());
        childView.getChildren().add(createKeyAssignBox());
        addAnotherChildComponent(childView);
        return childView;
    }
    default void addAnotherChildComponent(VBox childView) {

    }
    default Node createCommentBox() {
        return new HBox(6, createLabel("Comment: "), initCommentTextField());
    }
    default  Node createKeyAssignBox() {
        return new HBox(6, createLabel("Key: "), initAssignKeyTextField());
    }
    default Node initAssignKeyTextField() {
        TextField assignKeyField = new TextField();
        assignKeyField.textProperty().bindBidirectional(keyAssign);
        return assignKeyField;
    }
    default Node initCommentTextField() {
        TextField commentField = new TextField();
        commentField.textProperty().bindBidirectional(comment);
        return commentField;
    }
    default Label createLabel(String contents) {
        return new Label(contents);
    }

}
