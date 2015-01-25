/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater;

import java.io.File;

/**
 *
 * @author ChiaPing Tsai <chia7712@gmail.com>
 */
public class TConstants {
    public static final String[] DEFAULT_VIDEO_EXTENSIONS = {"wmv", "mkv", "avi", "mp4", "m4v"};
    public static final String[] DEFAULT_COVER_EXTENSIONS = {"jpg", "jpeg"};
    public static final File DEFAULT_LOG_FILE = new File("D:\\movie.log");
    public static final File DEFAULT_MOVIE_DIR = new File("D:\\延年益壽");
//    public static final File DEFAULT_MOVIE_DIR = new File("D:\\test");
    private TConstants(){}
}
