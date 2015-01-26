/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.control;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.util.Pair;
import net.spright.theater.model.BaseSubject;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.model.MovieManager;
import net.spright.theater.model.QueryQueue;
import net.spright.theater.model.Subject;
import net.spright.theater.player.MoviePlayer;
import net.spright.theater.player.SimplePlayer;
import net.spright.theater.tools.FilenameUtils;

/**
 *
 * @author ChiaPing Tsai <chia7712@gmail.com>
 */
public class ControlPane extends BorderPane implements Closeable {
    private final QueryQueue<Pair<MovieAction, MovieInfo>> controlQueue;
    private final PrimaryPane primaryPane;
    private final MovieManager manager = getDefaultMovieManager();
    private final Observer playerObserver;
    private MoviePlayer player;
    public ControlPane() {
        this.controlQueue = new QueryQueue((QueryQueue.Responder<Pair<MovieAction, MovieInfo>>) (Pair<MovieAction, MovieInfo> entry) -> {
            switch(entry.getKey()) {
                case Play:
                    switchToPlayer(entry.getValue());
                    break;
                case Stop:
                    switchToPrimary();
                    break;
                case Delete:
                    delete(entry.getValue());
                    break;
                default:
                    break;
            }
        });
        this.primaryPane = new PrimaryPane(manager, controlQueue);
        this.setCenter(primaryPane);
        this.setOnScroll((ScrollEvent e) -> {
           if (e.getDeltaY() > 0) {
               primaryPane.getQueue().offer(QueryType.Next);
           } else if (e.getDeltaY() < 0) {
               primaryPane.getQueue().offer(QueryType.Back);
           }
        });
    }
    private static MovieManager getDefaultMovieManager() {
        return MovieManager.newBuilder().build();
    }
    private void delete(MovieInfo movieInfo) throws InterruptedException {
        primaryPane.delete(movieInfo);
        manager.delete(movieInfo);
    }
    private void switchToPlayer(MovieInfo movieInfo) throws InterruptedException {
        if (player == null) {
            player = new SimplePlayer(movieInfo);
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() ->{
                this.setCenter(player.asNode());
                latch.countDown();
            });
            latch.await();
        } else {
            throw new RuntimeException("duplicate Player");
        }
    }
    private void switchToPrimary() throws InterruptedException {
        if (player != null) {
            closeNoException(player);
            player = null;
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() ->{
                this.setCenter(primaryPane);
                latch.countDown();
            });
            latch.await();
        } else {
            throw new RuntimeException("Is primary pane now");
        }

        
    }
    private static void closeNoException(Closeable closer) {
        try {
            if (closer != null) {
                closer.close();
            }
            
        } catch (IOException ex) {
        }
    }
    @Override
    public void close() throws IOException {
        closeNoException(controlQueue);
        closeNoException(player);
        closeNoException(primaryPane);
    }
    private enum QueryType {
        Back("←"),
        Hot("熱門"),
        Favorite("最愛"),
        Random("隨機"),
        Next("→");
        private final String desc;
        QueryType(String desc) {
            this.desc = desc;
        }
        public String getDescription() {
            return desc;
        }
    }
    private static class PrimaryPane extends BorderPane implements Closeable, Subject {
        private final List<MovieInfo> movies = new LinkedList();
        private final Object lock = new Object();
        private final MovieManager movieManager;
        private final List<Button> btnList = new LinkedList();
        private final BaseSubject subject = new BaseSubject();
        private int rows = 2;
        private int columns = 2;
        private int movieIndex = 0;
        private GridPane grid = null;
        public PrimaryPane(MovieManager movieManager) {
            this.movieManager = movieManager;
            HBox hbox = new HBox();
            for (QueryType type : QueryType.values()) {
                Button btn = new Button(type.getDescription());
                btn.setOnAction(e -> {
                    try {
                        handleQuery(type);
                    } catch (InterruptedException ex) {
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
        private void handleQuery(QueryType type) throws InterruptedException {
            switch (type) {
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
                    updateValue(true);
                    break;
                case Back:
                    updateValue(false);
                    break;
                default:
                    break;
            }
        }
        private void setAllButtonDisable(boolean disable) {
            btnList.forEach(btn -> btn.setDisable(disable));
        }
        public void setNumbers(int rows, int columns) throws InterruptedException {
            synchronized(lock) {
                this.rows = Math.max(this.rows, rows);
                this.columns = Math.max(this.columns, columns);
                updateValue();
            }

        }
        private void delete(MovieInfo movieInfo) throws InterruptedException {
            synchronized(lock) {
                if (movies.remove(movieInfo)) {
                    updateValue();
                }  
            }

        }
        private void setMovieInfos(List<MovieInfo> movieList) throws InterruptedException {
            synchronized(lock) {
                this.movies.clear();
                this.movies.addAll(movieList);
                movieIndex = 0;
                updateValue();
            }
        }
        private void updateValue() throws InterruptedException {
            updateValue(true);
        }
        private void updateValue(boolean increment) throws InterruptedException {
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
                                moviePaneList.add(new MovieInfoPane(movieInfo, controlQueue));
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
                                grid.add(moviePaneList.get(index), column, row);
                                ++index;
                            }
                        }
                    } finally {
                        movieManager.saveWOException();
                        setAllButtonDisable(false);
                        latch.countDown();
                    }
                });
                latch.await();
            }
        }
        @Override
        public void close() throws IOException {
            primaryQueue.close();
        }
        public QueryQueue<QueryType> getQueue() {
            return primaryQueue;
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
    }
}
