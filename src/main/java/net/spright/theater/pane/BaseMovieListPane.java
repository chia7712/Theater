/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import net.spright.theater.control.Observer;
import net.spright.theater.model.BaseSubject;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.model.MovieManager;

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
    private final MovieInfoFactory movieInfoFactory;
    private Action currentAction;
    private int rows = 2;
    private int columns = 2;
    private int movieIndex = 0;
    private GridPane grid = null;
    public BaseMovieListPane(MovieManager movieManager, MovieInfoFactory movieInfoFactory) {
        this.movieManager = movieManager;
        this.movieInfoFactory = movieInfoFactory;
        HBox hbox = new HBox();
        for (Action action : Action.values()) {
            if (action == Action.Nothing) {
                continue;
            }
            Button btn = new Button(action.name());
            btn.setOnAction(e -> {
                try {
                    handleQuery(action);
                    currentAction = action;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                
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
    private static GridPane initializeGridPane(int rows, int columns) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        final double rowPer = (double)100 / (double)rows;
        final double colPer = (double)100 / (double)columns;

        for (int row = 0; row != rows; ++row) {
            RowConstraints constraint = new RowConstraints();
            constraint.setPercentHeight(rowPer);
            grid.getRowConstraints().add(constraint);
        }
        for (int column = 0; column != columns; ++column) {
            ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPercentWidth(colPer);
            grid.getColumnConstraints().add(constraint);
        }
        return grid;
    }
    private void handleQuery(Action action) throws IOException {
        switch (action) {
            case Hot:
                setMovieInfos(movieManager.sortHotMovies());
                break;
            case Favorite:
                setMovieInfos(movieManager.sortFavoriteMovies());
                break;
            case Random:
                setMovieInfos(movieManager.sortRandomMovies());
                break;
            case Next:
                updateContent(true);
                break;
            case Back:
                updateContent(false);
                break;
            default:
                break;
        }
    }
    private void setAllButtonDisable(boolean disable) {
        btnList.forEach(btn -> btn.setDisable(disable));
    }
    public void setNumbers(int rows, int columns) throws IOException {
        synchronized(lock) {
            this.rows = Math.max(this.rows, rows);
            this.columns = Math.max(this.columns, columns);
            updateContent();
        }

    }
    public void delete(MovieInfo movieInfo) throws IOException {
        synchronized(lock) {
            if (movies.remove(movieInfo)) {
                updateContent();
            }  
        }
    }
    private void setMovieInfos(List<MovieInfo> movieList) throws IOException {
        synchronized(lock) {
            this.movies.clear();
            this.movies.addAll(movieList);
            movieIndex = 0;
            updateContent();
        }
    }
    @Override
    public void updateContent() throws IOException {
        updateContent(true);
    }
    private void updateContent(boolean increment) throws IOException {
        synchronized(lock) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                try {
                    setAllButtonDisable(true);
                    if (grid != null) {
                        getChildren().remove(grid);
                        grid = null;
                    }
                    if (movies.isEmpty()) {
                        return;
                    }
                    if (!increment) {
                        movieIndex -= (rows * columns);
                    }
                    List<MovieInfoPane> moviePaneList = new LinkedList();
                    for (int count = 0; count != rows * columns && count < movies.size(); ++count) {
                        if (movieIndex >= movies.size() || movieIndex < 0) {
                            if (increment) {
                                movieIndex = 0;
                            } else {
                                movieIndex = movies.size() - 1;
                            }
                        }
                        MovieInfo movieInfo = movies.get(movieIndex);
                        try {
                            moviePaneList.add(movieInfoFactory.create(movieInfo));
                        } catch (Exception ex) {
                        } finally {
                            if (increment) {
                                ++movieIndex;
                            } else {
                                --movieIndex;
                            }
                        }
                    }
                    grid = initializeGridPane(rows, columns); 
                    setCenter(grid);
                    int index = 0;
                    for (int row = 0; row != rows; ++row) {
                        for (int column = 0; column != columns; ++column) {
                            if (index >= moviePaneList.size()) {
                                return;
                            }
                            grid.add(moviePaneList.get(index).asNode(), column, row);
                            ++index;
                        }
                    }
                } finally {
                    movieManager.saveWOException();
                    setAllButtonDisable(false);
                    latch.countDown();
                }
            });
            try {
                latch.await();
            } catch (InterruptedException ex) {
                throw new IOException(ex);
            }
        }
    }
    @Override
    public List<MovieInfo> getMovieInfos() {
        return new ArrayList(movies);
    }
    @Override
    public Action getAction() {
        return currentAction;
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
        movieManager.save();
    }
}
