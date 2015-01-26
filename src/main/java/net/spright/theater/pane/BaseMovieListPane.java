/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.pane;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.spright.theater.control.ControlPane;
import net.spright.theater.control.Observer;
import net.spright.theater.model.BaseSubject;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.model.MovieManager;
import net.spright.theater.model.Subject;

/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public class BaseMovieListPane extends BorderPane implements MovieListPane {
    private final List<MovieInfo> movies = new LinkedList();
    private final Object lock = new Object();
    private final MovieManager movieManager;
    private final List<Button> btnList = new LinkedList();
    private final BaseSubject subject = new BaseSubject();
    private int rows = 2;
    private int columns = 2;
    private int movieIndex = 0;
    private GridPane grid = null;
    public BaseMovieListPane(MovieManager movieManager) {
        this.movieManager = movieManager;
        HBox hbox = new HBox();
        for (Action action : Action.values()) {
            Button btn = new Button(action.name());
            btn.setOnAction(e -> {
                handleQuery(action);
            });
            btnList.add(btn);
            hbox.getChildren().add(btn);
        }
        hbox.getChildren().add(new Label("影片數量：" + movieManager.getMovieNumber()));
        hbox.getChildren().add(new Label("   "));
        hbox.getChildren().add(new Label("不支援影片數量：" + movieManager.getErrorMovieNumber()));
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(5, 10, 5, 10));
        this.setTop(hbox);
    }
    private void handleQuery(Action action) {
        
    }

    @Override
    public List<MovieInfo> getMovieInfos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateContent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Action getAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node asNode() {
        return this;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
