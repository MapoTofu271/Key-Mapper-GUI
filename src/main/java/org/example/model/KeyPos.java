package org.example.model;

import java.text.DecimalFormat;

public class KeyPos {
    public static final double SCREEN_WIDTH = 1600;
    public static final double SCREEN_HEIGHT = 900;
    private double x;
    private double y;
    public KeyPos() {}
    public KeyPos(double x, double corrdY) {
        this.x = x;
        this.y = corrdY;
    }

    public static KeyPos convertToRealCoordinates(double x, double y) {
        return new KeyPos(x*SCREEN_WIDTH, y*SCREEN_HEIGHT);
    }
    public static KeyPos convertToRatio(double coordX, double coordY) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return new KeyPos(Double.parseDouble(numberFormat.format(coordX / SCREEN_WIDTH)),
                Double.parseDouble(numberFormat.format(coordY/SCREEN_HEIGHT)));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

}
