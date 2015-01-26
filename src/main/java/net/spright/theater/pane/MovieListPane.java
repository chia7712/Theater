/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.pane;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import javafx.scene.Node;
import net.spright.theater.model.MovieInfo;
import net.spright.theater.model.Subject;

/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public interface MovieListPane extends Subject, Closeable {
    public enum Action {
        Back, Hot, Favorite, Random, Next, Nothing;
    }
    public List<MovieInfo> getMovieInfos();
    public void updateContent() throws IOException;
    public Action getAction();
    public Node asNode();
}
