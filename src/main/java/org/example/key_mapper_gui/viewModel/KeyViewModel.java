package org.example.key_mapper_gui.viewModel;

import javafx.beans.property.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.example.model.Key;
import org.example.model.KeyPos;
import org.example.service.KeyService;

public class KeyViewModel {
    private Key domainKey;
    private final KeyService keyService = new KeyService();
    private final StringProperty keyComment = new SimpleStringProperty();
    private final StringProperty keyAssigned = new SimpleStringProperty();
    private final BooleanProperty visibility = new SimpleBooleanProperty(false);
    private final ObjectProperty<KeyPos> keyPos = new SimpleObjectProperty<>();
    public KeyViewModel(Key key) {
        this.domainKey = key;
        bindFromDomain(key);
    }

    public Shape createShape(KeyPos keyPos) {
        KeyPos realPos = KeyPos.convertToRealCoordinates(keyPos.getX(), keyPos.getY());
        return new Circle(20f);
    }
    private void bindFromDomain(Key key) {
        keyComment.set(key.getComment());
        keyAssigned.set(key.getKey());
        keyPos.set(key.getPos());
    }

    public void savedChanged() {
        domainKey.setKey(keyAssigned.get());
        domainKey.setComment(keyComment.get());
    }

    public void updatedDragCoords(double x, double y) {
        KeyPos newCoords = KeyPos.convertToRatio(x, y);
        keyPos.set(newCoords);
        domainKey.setPos(keyPos.get());
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

    public StringProperty getKeyAssigned() {
        return keyAssigned;
    }

    public StringProperty getKeyComment() {
        return keyComment;
    }

    public ObjectProperty<KeyPos> getKeyPosObjectPropertyProperty() {
        return keyPos;
    }

}
