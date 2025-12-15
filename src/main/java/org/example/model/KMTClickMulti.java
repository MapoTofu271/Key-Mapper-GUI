package org.example.model;

import java.util.List;

public class KMTClickMulti extends Key {
    private String type = "-KMT_CLICK_MULTI";
    private int delay;
    private List<ClickNode> clickNodes;

    public KMTClickMulti() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public int getDelay() {
        return delay;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }
}
