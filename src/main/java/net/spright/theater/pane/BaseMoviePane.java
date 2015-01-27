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
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import net.spright.theater.control.ImageViewPane;
import net.spright.theater.control.Observer;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.model.MovieManager;
import net.spright.theater.tools.FilenameUtils;

/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public class BaseMoviePane extends BorderPane implements MoviePane {
    private enum ButtonAction {
        Back, Hot, Favorite, Random, Next;
    }
    private final List<MovieInfo> movies = new LinkedList();
    private final Object lock = new Object();
    private final MovieManager movieManager;
    private final List<Button> btnList = new LinkedList();
    private final List<Observer> observers = new LinkedList();
    private MovieInfo currentMovieInfo;
    private int rows = 1;
    private int columns = 1;
    private int movieIndex = 0;
    private GridPane grid = null;
    public BaseMoviePane(MovieManager movieManager) {
        this.movieManager = movieManager;
        initControlButton();
        initMovieButton();
        initScroll();
    }
    private void initScroll() {
        setOnScroll((ScrollEvent e) -> {
            if (e.getDeltaY() > 0) {
                updateContent(true);
            } else if (e.getDeltaY() < 0) {
                updateContent(false);
            }
        });
    }
    private void initMovieButton() {
        HBox hbox = new HBox();
        Button playButton = new Button("Play");
        playButton.setOnAction(e -> {
            MovieInfo movieInfo = currentMovieInfo;
            if (movieInfo != null) {
                movieInfo.click();
                inform();
            }
        });
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            MovieInfo movieInfo = currentMovieInfo;
            if (movieInfo != null) {
                movieManager.delete(movieInfo);
                movies.remove(movieInfo);
                updateContent();
            }
        });
        btnList.add(playButton);
        btnList.add(deleteButton);
        hbox.getChildren().add(playButton);
        hbox.getChildren().add(deleteButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(5, 10, 5, 10));
        this.setBottom(hbox);
    }
    private void initControlButton() {
        HBox hbox = new HBox();
        for (ButtonAction action : ButtonAction.values()) {
            Button btn = new Button(action.name());
            btn.setOnAction(e -> {
                try {
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
    private void setCurrentMovieInfo(MovieInfo movieInfo) {
        this.currentMovieInfo = movieInfo;
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
    public void setNumbers(int rows, int columns) throws IOException {
        synchronized(lock) {
            this.rows = Math.max(this.rows, rows);
            this.columns = Math.max(this.columns, columns);
            updateContent();
        }

    }
    @Override
    public boolean delete(MovieInfo movieInfo) throws IOException {
        synchronized(lock) {
            if (movies.remove(movieInfo)) {
                updateContent();
                return true;
            } else {
                return false;
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
    public Optional<MovieInfo> getSelectedMovieInfo() {
        return Optional.ofNullable(currentMovieInfo);
    }
    private void updateContent() {
        updateContent(true);
    }
    private void updateContent(boolean increment) {
        synchronized(lock) {
            try {
                btnList.forEach(btn -> btn.setDisable(true));
                if (grid != null) {
                    getChildren().remove(grid);
                    grid = null;
                }
                if (movies.isEmpty()) {
                    return;
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
                        moviePaneList.add(new MovieInfoPane(this, movieInfo));
                    } catch (Exception ex) {
                    }
                    if (increment) {
                        ++movieIndex;
                    } else {
                        --movieIndex;
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
                        grid.add(moviePaneList.get(index), column, row);
                        ++index;
                    }
                }
            } finally {
                btnList.forEach(btn -> btn.setDisable(false));
            }
        }
    }
    @Override
    public List<MovieInfo> getMovieInfos() {
        return new ArrayList(movies);
    }

    @Override
    public Node asNode() {
        return this;
    }

    @Override
    public boolean attach(Observer observer) {
        return observers.add(observer);
    }

    @Override
    public boolean detach(Observer observer) {
        return observers.remove(observer);
    }

    @Override
    public void inform() {
        observers.parallelStream()
                .forEach((Observer observer) -> observer.update(this));
    }

    @Override
    public void close() throws IOException {
        movieManager.save();
    }
    private static class MovieInfoPane extends BorderPane {
        private final MovieInfo movieInfo;
        private final BaseMoviePane moviePane;
        private final Label countLabel = new Label();
        public MovieInfoPane(BaseMoviePane moviePane, MovieInfo movieInfo) throws Exception {
            this.movieInfo = movieInfo;
            this.moviePane = moviePane;
            Image image = new Image(movieInfo.getCoverFile().toURI().toString());
            if (image.isError()) {
                throw image.getException();
            }
            ImageViewPane view = new ImageViewPane(new ImageView(image));
            view.setSmooth(true);
            view.setPreserveRatio(true);
            view.setOnMouseReleased((MouseEvent e) -> {
                moviePane.setCurrentMovieInfo(movieInfo);
            });
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
            box.getChildren().add(new Label("   "));
            box.getChildren().add(new Label("點擊數："));
            box.getChildren().add(countLabel);
            box.getChildren().add(new Label("   "));
            box.getChildren().add(new Label("最愛"));
            box.getChildren().add(favoriteBox);
            this.setTop(box);
        }
        public MovieInfo getMovieInfo() {
            return movieInfo;
        }
    }
}
