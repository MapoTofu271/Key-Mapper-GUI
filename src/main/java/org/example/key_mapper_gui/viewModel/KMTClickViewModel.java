package org.example.key_mapper_gui.viewModel;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.example.model.KMTClick;

public class KMTClickViewModel extends KeyViewModel {
    public KMTClickViewModel(KMTClick key) {
        super(key);
    }
    @Override
    public double getOffset() {
        return 40;
    }
    @Override
    public Shape createShape() {
        return new Circle(40f, Color.WHITE);
    }
}
