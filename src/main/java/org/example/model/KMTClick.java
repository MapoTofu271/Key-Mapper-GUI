package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class KMTClick extends Key{

    private String type = "-KMT_CLICK";
    public KMTClick() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
//    public KMTClick(String comment, String key, KeyPos pos) {
//        super(comment, key, pos);
//    }

}
