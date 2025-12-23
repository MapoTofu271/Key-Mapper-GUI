package org.example.model;

import org.example.key_mapper_gui.Main;

import java.text.DecimalFormat;

public class KeyPos {

    private double x;
    private double y;
    public KeyPos() {}
    public KeyPos(double x, double corrdY) {
        this.x = x;
        this.y = corrdY;
    }

    public static KeyPos convertToRealCoordinates(double x, double y) {
        return new KeyPos(x* Main.SCREEN_MAX_WIDTH, y*Main.SCREEN_MAX_HEIGHT);
    }
    public static KeyPos convertToRatio(double coordX, double coordY) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return new KeyPos(Double.parseDouble(numberFormat.format(coordX / Main.SCREEN_MAX_WIDTH)),
                Double.parseDouble(numberFormat.format(coordY/Main.SCREEN_MAX_HEIGHT)));
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
