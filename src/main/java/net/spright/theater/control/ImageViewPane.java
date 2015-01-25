/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;


/**
 *
 * @author ChiaPing Tsai <chia7712@gmail.com>
 */
public class ImageViewPane extends Region {
    private final ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty();
    public ImageViewPane() {
        this(new ImageView());
    }
    public ImageViewPane(ImageView imageView) {
        imageViewProperty.addListener((ObservableValue<? extends ImageView> arg0, ImageView oldIV, ImageView newIV) -> {
            if (oldIV != null) {
                getChildren().remove(oldIV);
            }
            if (newIV != null) {
                getChildren().add(newIV);
            }
        });
        this.imageViewProperty.set(imageView);
    }
    public void setSmooth(boolean value) {
        ImageView imageView = imageViewProperty.get();
        if (imageView != null) {
            imageView.setSmooth(value);
        }
    }
    public void setPreserveRatio(boolean value) {
        ImageView imageView = imageViewProperty.get();
        if (imageView != null) {
            imageView.setPreserveRatio(value);
        }
    }
    public ObjectProperty<ImageView> imageViewProperty() {
        return imageViewProperty;
    }
    public ImageView getImageView() {
        return imageViewProperty.get();
    }
    public void setImageView(ImageView imageView) {
        this.imageViewProperty.set(imageView);
    }

    @Override
    protected void layoutChildren() {
        ImageView imageView = imageViewProperty.get();
        if (imageView != null) {
            imageView.setFitWidth(getWidth());
            imageView.setFitHeight(getHeight());
            layoutInArea(imageView, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
        }
        super.layoutChildren();
    }

}
