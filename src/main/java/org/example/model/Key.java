package org.example.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Key {
    private String comment;
    private String key;
    private KeyPos pos = new KeyPos(0.5,0.5);


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
    public String keyMapGenerated() {
        return comment + key;
    }
}
