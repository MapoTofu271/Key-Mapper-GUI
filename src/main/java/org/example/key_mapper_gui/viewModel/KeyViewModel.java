package org.example.key_mapper_gui.viewModel;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.example.key_mapper_gui.repository.KeyStorage;
import org.example.model.Key;
import org.example.model.KeyPos;
import org.example.service.KeyService;

import java.util.HashMap;
import java.util.UUID;

public class KeyViewModel {
    private Key domainKey;
    private final KeyService keyService = new KeyService();
    private final StringProperty keyComment = new SimpleStringProperty();
    private final StringProperty keyAssigned = new SimpleStringProperty();
    private final BooleanProperty visibility = new SimpleBooleanProperty(false);
    private final ObjectProperty<KeyPos> keyPosition = new SimpleObjectProperty<>();

    private final DoubleProperty xLayout = new SimpleDoubleProperty();
    private final DoubleProperty yLayout = new SimpleDoubleProperty();
    public KeyViewModel(Key key) {
        this.domainKey = key;
        keyPosition.addListener((obs, oldPos, newPos) -> {
            if (newPos != null) {
                KeyPos realPos = KeyPos.convertToRealCoordinates(domainKey.getPos().getX(), domainKey.getPos().getY());
                xLayout.set(realPos.getX() + 60);
                yLayout.set(realPos.getY() - 20);
            }
        });
    }
    public void viewModelInit() {
        loadFromDomain();
        keyService.updateKey(domainKey);
    }

    public Shape createShape() {
        return new Circle(10f, Color.TRANSPARENT);
    }
    public void loadFromDomain() {
        keyComment.set(domainKey.getComment());
        keyAssigned.set(domainKey.getKey());
        keyPosition.set(domainKey.getPos());
    }

    public void savedChanges() {
        domainKey.setKey(keyAssigned.get());
        domainKey.setComment(keyComment.get());
        keyService.updateKey(domainKey);
    }

    public void updatedDragCoords(double x, double y) {
        KeyPos newCoords = KeyPos.convertToRatio(x, y);
        keyPosition.set(newCoords);
        domainKey.setPos(keyPosition.get());
        keyService.updateKey(domainKey);
    }

    public void showDetailsMenu() {
        visibility.set(true);
    }
    public void hideDetailsMenu() {
        visibility.set(false);
    }
    public BooleanProperty getVisibility() {
        return visibility;
    }
    public Key getDomainKey() {
        return domainKey;
    }
    public void setDomainKey(Key key) {
        this.domainKey = key;
    }
    public StringProperty getKeyAssigned() {
        return keyAssigned;
    }

    public StringProperty getKeyComment() {
        return keyComment;
    }

    public ObjectProperty<KeyPos> getKeyPosition() {
        return keyPosition;
    }
    public double getOffset() {
        return 20;
    }

    public DoubleProperty getxLayout() {
        return xLayout;
    }
    public DoubleProperty getyLayout() {
        return yLayout;
    }
}
