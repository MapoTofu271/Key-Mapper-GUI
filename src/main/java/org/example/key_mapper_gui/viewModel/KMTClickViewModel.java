package org.example.key_mapper_gui.viewModel;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.example.model.KMTClick;
import org.example.model.Key;
import org.example.model.KeyPos;

public class KMTClickViewModel extends KeyViewModel {
    public KMTClickViewModel(KMTClick key) {
        super(key);
    }

    @Override
    public Shape createShape(KeyPos keyPos) {
        KeyPos realPos = KeyPos.convertToRealCoordinates(keyPos.getX(), keyPos.getY());
        return new Circle(40f, Color.WHITE);
    }
}
