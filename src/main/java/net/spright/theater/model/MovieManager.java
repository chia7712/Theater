/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import net.spright.theater.TConstants;
import net.spright.theater.protos.ClickBookProtos;
import net.spright.theater.tools.FilenameUtils;

/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public class MovieManager {
    public static MovieManager getDefaultMovieManager() {
        return MovieManager.newBuilder().build();
    }
    public static Builder newBuilder() {
        return new Builder();
    }
    public static class Builder {
        private File movieDir;
        private File logFile;
        private List<String> videoExtension = new LinkedList();
        private List<String> coverExtension = new LinkedList();
        private Builder() {
        }
        public MovieManager build() {
            return new MovieManager(
                    movieDir == null ? TConstants.DEFAULT_MOVIE_DIR : movieDir
                    , logFile == null ? TConstants.DEFAULT_LOG_FILE : logFile
                    , new Filter(videoExtension.isEmpty() ? Arrays.asList(TConstants.DEFAULT_VIDEO_EXTENSIONS) : videoExtension)
                    , new Filter(coverExtension.isEmpty() ? Arrays.asList(TConstants.DEFAULT_COVER_EXTENSIONS) : coverExtension));
        }
        public Builder setMovieDir(File movieDir) {
            this.movieDir = movieDir;
            return this;
        }
        public Builder setLogFile(File logFile) {
            this.logFile = logFile;
            return this;
        }
        public Builder setVideoExtension(List<String> extendsion) {
            videoExtension.clear();
            videoExtension.addAll(extendsion);
            return this;
        }
        public Builder setCoverExtension(List<String> extendsion) {
            coverExtension.clear();
            coverExtension.addAll(extendsion);
            return this;
        }
    }
    private final List<MovieInfo> movieList = new LinkedList();
    private final List<File> noUsedFiles = new LinkedList(); 
    private final List<MovieInfo> errorMovieList = new LinkedList();
    private final File logFile;
    private final FilenameFilter videoFilter;
    private final FilenameFilter coverFilter;
    private MovieManager(File movieDir, File logFile, FilenameFilter videoFilter, FilenameFilter coverFilter) {
        this.logFile = logFile;
        this.videoFilter = videoFilter;
        this.coverFilter = coverFilter;
        scanDir(movieDir);
    }
    private List<ClickBookProtos.Click> loadHistory() {
        ClickBookProtos.ClickBook.Builder clickBook = ClickBookProtos.ClickBook.newBuilder();
        try (FileInputStream input = new FileInputStream(logFile)) {
            clickBook.mergeFrom(input);
            return clickBook.build().getClickList();
        } catch (IOException ex) {
            return new LinkedList();
        }
    }
    public List<MovieInfo> sortRandomMovies() {
        List<MovieInfo> rval = new ArrayList(movieList);
        Collections.shuffle(rval);
        return rval;
    }
    public List<MovieInfo> sortFavoriteMovies() {
        List<MovieInfo> rval = new ArrayList(movieList.size());
        movieList.forEach(value -> {
            if (value.getFavorite()) {
                rval.add(value);
            }
        });
        return rval;
    }
    public List<MovieInfo> sortHotMovies() {
        List<MovieInfo> rval = new ArrayList(movieList);
        rval.sort((MovieInfo a, MovieInfo b) -> Long.compare(b.getCount(), a.getCount()));
        return rval;
    }
    public void delete(MovieInfo movieInfo) {
        movieList.remove(movieInfo);
        movieInfo.deleteFile();
    }
    private void scanDir(File movieDir) {
        movieList.clear();
        noUsedFiles.clear();
        Map<String, ClickBookProtos.Click> clicks = toMap(loadHistory());
        Map<String, File> videos = toMap(movieDir.listFiles(videoFilter));
        Map<String, File> covers = toMap(movieDir.listFiles(coverFilter));
        videos.forEach((id, videoFile) -> {
            File coverFile = covers.get(id);
            if (coverFile != null) {
                ClickBookProtos.Click click = clicks.get(id);
                MovieInfo movieInfo = click == null ? new MovieInfo(id, videoFile, coverFile) : new MovieInfo(click, videoFile, coverFile);
                if (movieInfo.isSupported()) {
                    movieList.add(movieInfo);
                } else {
                    errorMovieList.add(movieInfo);
                }
            } else {
                noUsedFiles.add(videoFile);
            }
        });
        
    }
    public int getErrorMovieNumber() {
        return errorMovieList.size();
    }
    public List<MovieInfo> copyErrorMovies() {
        return new ArrayList(errorMovieList);
    }
    public int getMovieNumber() {
        return movieList.size();
    }
    public List<MovieInfo> copyAllMovies() {
        return new ArrayList(movieList);
    }
    public List<File> copyNoUsedFile() {
        return new ArrayList(noUsedFiles);
    }
    private static Map<String, ClickBookProtos.Click> toMap(List<ClickBookProtos.Click> clickList) {
        Map<String, ClickBookProtos.Click> rval = new HashMap();
        clickList.forEach(click -> rval.put(click.getId(), click));
        return rval;
    }
    private static Map<String, File> toMap(File[] files) {
        Map<String, File> rval = new HashMap();
        if (files != null && files.length != 0) {
            Stream.of(files).forEach(file -> {
                Optional<String> id = FilenameUtils.getBaseName(file);
                id.ifPresent(name -> rval.put(name.toUpperCase(), file));
            });
        }

        return rval;
    }
    public boolean saveWOException() {
        try {
            save();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public void save() throws IOException {
        ClickBookProtos.ClickBook.Builder clickBook = ClickBookProtos.ClickBook.newBuilder();
        movieList.forEach((value) -> {
            clickBook.addClick(ClickBookProtos.Click.newBuilder()
                        .setId(value.getID())
                        .setCount(value.getCount())
                        .setFavorite(value.getFavorite())
                        .build());
        });
        try (FileOutputStream output = new FileOutputStream(logFile)) {
            clickBook.build().writeTo(output);
        }
        
    }
    private static class Filter implements FilenameFilter {
        private final List<String> extensionComp = new LinkedList();
        public Filter(String[] extensionComp) {
            this(Arrays.asList(extensionComp));
        }
        public Filter(List<String> extensionComp) {
            this.extensionComp.addAll(extensionComp);
        }
        @Override
        public boolean accept(File dir, String name) {
            Optional<String> extendsion = FilenameUtils.getExtension(name);
            if (extendsion.isPresent()) {
                return extensionComp.stream().anyMatch(key -> key.compareTo(extendsion.get().toLowerCase()) == 0);
            }
            return false;
        }
    }
}
