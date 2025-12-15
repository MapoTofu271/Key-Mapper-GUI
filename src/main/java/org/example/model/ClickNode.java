package org.example.model;

public class ClickNode {
    private int delay;
    private KeyPos keyPos;
    public ClickNode(int delay, KeyPos keyPos) {
        this.delay = delay;
        this.keyPos = keyPos;
    }

    public int getDelay() {
        return delay;
    }

    public KeyPos getKeyPos() {
        return keyPos;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setKeyPos(KeyPos keyPos) {
        this.keyPos = keyPos;
    }
}
