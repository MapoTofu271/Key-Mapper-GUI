package org.example.View;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.example.key_mapper_gui.Main;
import org.example.model.KMTClick;
import org.example.model.Key;
import org.example.model.KeyPos;
import org.example.service.KeyService;

import java.util.function.Function;

public class KMTClickViewImpl implements ClickView, ClickDetailView {
    private final KMTClick kmtClick;
    private static VBox keyDetails = null;
    private final KeyService keyService;

    public KMTClickViewImpl(KMTClick kmtClick) {
        this.kmtClick = kmtClick;
        StackPane stackPane = new StackPane();
        this.initShape(kmtClick, stackPane, keyPos -> {
            Circle circle = new Circle();
            circle.setCenterX(keyPos.getX());
            circle.setCenterY(keyPos.getY());
            circle.setRadius(40f);
            circle.setFill(Color.WHITE);
            stackPane.setMaxSize(circle.getRadius(), circle.getRadius());
            return circle;
        });
        this.keyService = new KeyService();
        Main.keyMap.put(this.kmtClick.keyMapGenerated(), kmtClick);
        keyDetails = this.initDetailsView();
    }

    public Group viewRender() {
        Group view = new Group();
        view.getChildren().add(keyDetails);
        return view;
    }

    @Override
    public void handleMouseDoubleClick() {
        keyDetails.setVisible(true);
        ClickDetailView.keyAssign.set(this.kmtClick.getKey().get());
        ClickDetailView.comment.set(this.kmtClick.getComment().get());
    }

    @Override
    public void updateDragCoord() {
        System.out.println(kmtClick.getPos().getX() + " " + kmtClick.getPos().getY());
    }

    @Override
    public void handleEnterClick() {
        keyService.deleteKey(this.kmtClick);
        this.kmtClick.setComment(ClickDetailView.comment.get());
        this.kmtClick.setKey(ClickDetailView.keyAssign.get());
        ClickDetailView.comment.set("");
        ClickDetailView.keyAssign.set("");
        keyService.savedKey(this.kmtClick);
        keyDetails.setVisible(false);

        System.out.println(kmtClick.getComment() + " " + kmtClick.getKey());
        System.out.println(Main.keyMap.size());
    }
    @Override
    public Pane getPane() {
        return Main.keyField;
    }

}
