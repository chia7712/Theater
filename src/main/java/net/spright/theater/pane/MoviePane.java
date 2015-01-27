/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.pane;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.scene.Node;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.model.MovieManager;
import net.spright.theater.model.Subject;

/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public interface MoviePane extends Subject, Closeable {
    public static MoviePane getMoviePane(MovieManager movieManager) {
        return new BaseMoviePane(movieManager);
    }
    public boolean delete(MovieInfo movieInfo) throws IOException;
    public List<MovieInfo> getMovieInfos();
    public Optional<MovieInfo> getSelectedMovieInfo();
    public Node asNode();
}
