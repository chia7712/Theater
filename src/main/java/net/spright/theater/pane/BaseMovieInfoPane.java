/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.pane;

import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import net.spright.theater.control.ImageViewPane;
import net.spright.theater.control.Observer;
import net.spright.theater.model.BaseSubject;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.tools.FilenameUtils;

/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public class BaseMovieInfoPane extends BorderPane implements MovieInfoPane {
    private final MovieInfo movieInfo;
    private final Label countLabel = new Label();
    private final BaseSubject subject = new BaseSubject();
    private Action action = Action.Nothing;
    public BaseMovieInfoPane(MovieInfo movieInfo) throws Exception {
        this.movieInfo = movieInfo;
        Image image = new Image(movieInfo.getCoverFile().toURI().toString());
        if (image.isError()) {
            throw image.getException();
        }
        Button playBtn = new Button("播放");
        playBtn.setOnAction(e -> {
            movieInfo.click();
            countLabel.setText(String.valueOf(movieInfo.getCount()));
            action = Action.Play;
            subject.inform();
        });
        Button deleteBtn = new Button("刪除");
        deleteBtn.setOnAction(e -> {
            action = Action.Delete;
            subject.inform();
        });
        ImageViewPane view = new ImageViewPane(new ImageView(image));
        view.setSmooth(true);
        view.setPreserveRatio(true);
        countLabel.setText(String.valueOf(movieInfo.getCount()));
        this.setCenter(view);

        CheckBox favoriteBox = new CheckBox();
        favoriteBox.setSelected(movieInfo.getFavorite());
        favoriteBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            movieInfo.setFavorite(newValue);
        });
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5, 10, 5, 10));
        FilenameUtils.getBaseName(movieInfo.getVideo()).ifPresent((name) -> {
            box.getChildren().add(new Label(name));
        });
        box.getChildren().add(playBtn);
        box.getChildren().add(deleteBtn);
        box.getChildren().add(new Label("   "));
        box.getChildren().add(new Label("點擊數："));
        box.getChildren().add(countLabel);
        box.getChildren().add(new Label("   "));
        box.getChildren().add(new Label("最愛"));
        box.getChildren().add(favoriteBox);
        this.setTop(box);
    }
    @Override
    public Node asNode() {
        return this;
    }
    @Override
    public MovieInfo getMovieInfo() {
        return movieInfo;
    }
    @Override
    public Action getAction() {
        return action;
    }
    @Override
    public boolean attach(Observer observer) {
        return subject.attach(observer);
    }

    @Override
    public boolean detach(Observer observer) {
        return subject.detach(observer);
    }

    @Override
    public void inform() {
        subject.inform();
    }

    @Override
    public void close() throws IOException {
    }
}
