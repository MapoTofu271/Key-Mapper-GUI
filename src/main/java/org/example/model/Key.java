package org.example.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Key {
    private StringProperty comment = new SimpleStringProperty("");
    private StringProperty key = new SimpleStringProperty("");
    private KeyPos pos = new KeyPos(0.5,0.5);


    public void setComment(String comment) {
        this.comment.set(comment);
    }
    public StringProperty getComment() {
        return comment;
    }
    public void setKey(String key) {
        this.key.set(key);
    }

    public StringProperty getKey() {
        return key;
    }

    public KeyPos getPos() {
        return pos;
    }
    public void setPos(KeyPos pos) {
        this.pos = pos;
    }
    public String keyMapGenerated() {
        return comment.get() + key.get();
    }
}
