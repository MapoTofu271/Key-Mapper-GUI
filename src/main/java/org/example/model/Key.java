package org.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

@JsonIgnoreProperties(value = {"keyID"})
public abstract class Key {
    @JsonIgnore
    private final UUID keyID = UUID.randomUUID();
    private String comment;
    private String key;
    private KeyPos pos = new KeyPos(0.5,0.5);
    private final boolean switchMap = false;

    public void setComment(String comment) {
        this.comment = (comment);
    }
    public String getComment() {
        return comment;
    }
    public void setKey(String key) {
        this.key = (key);
    }

    public String getKey() {
        return key;
    }

    public KeyPos getPos() {
        return pos;
    }
    public void setPos(KeyPos pos) {
        this.pos = pos;
    }

    public UUID getKeyID() {
        return keyID;
    }

    public boolean isSwitchMap() {
        return switchMap;
    }
}
