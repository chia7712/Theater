/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.player;

import java.io.Closeable;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer.Status;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.model.Subject;

/**
 *
 * @author 嘉平
 */
public interface MoviePlayer extends Subject, Closeable {
    public static MoviePlayer getDefaultMoviePlayer(MovieInfo movieInfo) {
        return new SimplePlayer(movieInfo);
    }
    public Status getStatus();
    public MovieInfo getMovieInfo();
    public Node asNode();
}
