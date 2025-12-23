package org.example.model;

public class KMTDrag {
    private final String type = "-KMT_DRAG";
    private KeyPos startPos;
    private KeyPos endPos;
    private int dragSpeed = 1;  //DEFAULT VALUE
    public KMTDrag(String comment, String key, KeyPos pos, KeyPos startPos, KeyPos endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public void setEndPos(KeyPos endPos) {
        this.endPos = endPos;
    }
    public KeyPos getEndPos() {
        return endPos;
    }
    public KeyPos getStartPos() {
        return startPos;
    }
    public void setStartPos(KeyPos startPos) {
        this.startPos = startPos;
    }

    public int getDragSpeed() {
        return dragSpeed;
    }

    public void setDragSpeed(int dragSpeed) {
        this.dragSpeed = dragSpeed;
    }
}
