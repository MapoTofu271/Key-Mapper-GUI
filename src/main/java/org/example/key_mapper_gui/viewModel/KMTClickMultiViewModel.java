package org.example.key_mapper_gui.viewModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.example.model.KMTClickMulti;


public class KMTClickMultiViewModel extends KeyViewModel {
    private IntegerProperty delay = new SimpleIntegerProperty();
    private KMTClickMulti key;
    public KMTClickMultiViewModel(KMTClickMulti key) {
        super(key);
        this.key = key;
    }

    @Override
    public Shape createShape() {
        return new Circle(25f, Color.RED);
    }

    @Override
    public double getOffset() {
        return 25;
    }

    public IntegerProperty getDelay() {
        return delay;
    }

    @Override
    public void savedChanges() {
//        KMTClickMulti key = (KMTClickMulti) this.getDomainKey();
        super.savedChanges();
        key.setDelay(delay.get());
    }

    @Override
    public void loadFromDomain() {
//        KMTClickMulti key = (KMTClickMulti) this.getDomainKey();
        super.loadFromDomain();
        this.delay.set(key.getDelay());
    }
}
