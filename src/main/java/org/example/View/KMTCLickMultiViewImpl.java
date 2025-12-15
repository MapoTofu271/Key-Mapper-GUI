package org.example.View;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.example.key_mapper_gui.Main;
import org.example.model.KMTClickMulti;
import org.example.model.Key;
import org.example.service.KeyService;

public class KMTCLickMultiViewImpl implements ClickView, ClickDetailView {
    private final KMTClickMulti kmtClickMulti;
    private static VBox keyDetails = null;
    private final KeyService keyService;

    public KMTCLickMultiViewImpl(KMTClickMulti kmtClickMulti) {
        this.kmtClickMulti = kmtClickMulti;
        this.initShape(kmtClickMulti, keyPos -> {
            Circle circle = new Circle();
            circle.setCenterX(keyPos.getX());
            circle.setCenterY(keyPos.getY());
            circle.setRadius(20f);
            return circle;
        });
        this.keyService = new KeyService();
        Main.keyMap.put(this.kmtClickMulti.keyMapGenerated(), kmtClickMulti);
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
        ClickDetailView.keyAssign.set(this.kmtClickMulti.getKey().get());
        ClickDetailView.comment.set(this.kmtClickMulti.getComment().get());
    }

    @Override
    public void updateDragCoord() {
        System.out.println(kmtClickMulti.getPos().getX() + " " + kmtClickMulti.getPos().getY());
    }

    @Override
    public void handleEnterClick() {
        keyService.deleteKey(this.kmtClickMulti);
        this.kmtClickMulti.setComment(ClickDetailView.comment.get());
        this.kmtClickMulti.setKey(ClickDetailView.keyAssign.get());
        ClickDetailView.comment.set("");
        ClickDetailView.keyAssign.set("");
        keyService.savedKey(this.kmtClickMulti);
        keyDetails.setVisible(false);

        System.out.println(kmtClickMulti.getComment() + " " + kmtClickMulti.getKey());
        System.out.println(Main.keyMap.size());
    }
    @Override
    public Pane getPane() {
        return Main.keyField;
    }
}
