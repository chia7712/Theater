/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.pane;

import net.spright.theater.model.MovieInfo;

/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public interface MovieInfoFactory {
    public MovieInfoPane create(MovieInfo movieInfo);
}
