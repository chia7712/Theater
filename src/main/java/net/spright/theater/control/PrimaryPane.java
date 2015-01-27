/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.control;

import java.io.Closeable;
import java.io.IOException;
import javafx.scene.layout.BorderPane;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.model.MovieManager;
import net.spright.theater.model.Subject;
import net.spright.theater.pane.MoviePane;
import net.spright.theater.player.MoviePlayer;

/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public class PrimaryPane extends BorderPane implements Closeable {
    private final MoviePane moviePane = MoviePane.getMoviePane(MovieManager.getDefaultMovieManager());
    private MoviePlayer player;
    public PrimaryPane() throws IOException {
        moviePane.attach((Subject subject) -> {
            if (subject instanceof MoviePane) {
                MoviePane moviePaneSubject = (MoviePane)subject;
                moviePaneSubject.getSelectedMovieInfo().ifPresent(movieInfo -> {
                    switchToPlayer(movieInfo);
                });
            }
        });
        this.setCenter(moviePane.asNode());
 
    }
    private void switchToPlayer(MovieInfo movieInfo)  {
        if (player == null) {
            player = MoviePlayer.getDefaultMoviePlayer(movieInfo);
            player.attach((Subject subject) -> {
                if (subject instanceof MoviePlayer) {
                    MoviePlayer moviePlayerSubject = (MoviePlayer)subject;
                    switch(moviePlayerSubject.getStatus()) {
                        case UNKNOWN:
                        case STOPPED:
                        case HALTED:
                            switchToPrimary();
                            break;
                        default:
                            break;
                    }
                }
            });
            this.setCenter(player.asNode());
        } else {
            throw new RuntimeException("duplicate Player");
        }
    }
    private void switchToPrimary() {
        if (player != null) {
            closeNoException(player);
            player = null;
            this.setCenter(moviePane.asNode());
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
        closeNoException(player);
        closeNoException(moviePane);
    }


}
