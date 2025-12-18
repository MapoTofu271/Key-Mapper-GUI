package org.example.key_mapper_gui.viewModel;

import javafx.beans.property.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.example.model.Key;
import org.example.model.KeyPos;
import org.example.service.KeyService;

import java.util.UUID;

public class KeyViewModel {
    private Key domainKey;
    private final UUID keyID = UUID.randomUUID();
    private final KeyService keyService = new KeyService();
    private final StringProperty keyComment = new SimpleStringProperty();
    private final StringProperty keyAssigned = new SimpleStringProperty();
    private final BooleanProperty visibility = new SimpleBooleanProperty(false);
    private final ObjectProperty<KeyPos> keyPosition = new SimpleObjectProperty<>();
    public KeyViewModel(Key key) {
        this.domainKey = key;
    }

    public void viewModelInit() {
        loadFromDomain();
    }

    public Shape createShape() {
        return new Circle(20f);
    }
    public void loadFromDomain() {
        keyComment.set(domainKey.getComment());
        keyAssigned.set(domainKey.getKey());
        keyPosition.set(domainKey.getPos());
    }

    public void savedChanges() {
        domainKey.setKey(keyAssigned.get());
        domainKey.setComment(keyComment.get());
    }

    public void updatedDragCoords(double x, double y) {
        KeyPos newCoords = KeyPos.convertToRatio(x, y);
        keyPosition.set(newCoords);
        domainKey.setPos(keyPosition.get());
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

    public UUID getKeyID() {
        return keyID;
    }
    public ObjectProperty<KeyPos> getKeyPosition() {
        return keyPosition;
    }
    public double getOffset() {
        return 20;
    }

}
