package org.example.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.key_mapper_gui.Main;
import org.example.model.KMTClickMulti;
import org.example.service.KeyService;
import org.xml.sax.HandlerBase;

public class KMTClickMultiViewImpl implements ClickView, ClickDetailView {
    private final StringProperty delay = new SimpleStringProperty();

    private final KMTClickMulti kmtClickMulti;
    private static VBox keyDetails = null;
    private final KeyService keyService;

    public KMTClickMultiViewImpl(KMTClickMulti kmtClickMulti) {
        this.kmtClickMulti = kmtClickMulti;
        StackPane stackPane = new StackPane();
        this.initShape(kmtClickMulti,stackPane, keyPos -> {
            Circle circle = new Circle();
            circle.setCenterX(keyPos.getX());
            circle.setCenterY(keyPos.getY());
            circle.setRadius(20f);
            circle.setFill(Color.RED);

            stackPane.setMaxSize(circle.getRadius(), circle.getRadius());
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

    public TextField initDelayTextField() {
        TextField delayField = new TextField();
        delayField.textProperty().bindBidirectional(delay);
        return delayField;
    }
    public HBox createDelayBox() {
        return new HBox(6, this.createLabel("Delay: "), initDelayTextField());
    }

    @Override
    public void addAnotherChildComponent(VBox childView) {
        childView.getChildren().add(createDelayBox());
    }
}
