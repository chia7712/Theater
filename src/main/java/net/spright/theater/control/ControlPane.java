/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.control;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.model.MovieManager;
import net.spright.theater.model.QueryQueue;
import net.spright.theater.player.MoviePlayer;
import net.spright.theater.player.SimplePlayer;

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


}
