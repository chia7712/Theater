/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.model;

import com.sun.media.jfxmedia.MediaManager;
import com.sun.media.jfxmedia.locator.Locator;
import com.sun.media.jfxmediaimpl.MediaUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import javafx.scene.media.Media;
import com.sun.media.jfxmedia.MediaException;
import javax.imageio.ImageIO;
import net.spright.theater.protos.ClickBookProtos.Click;
/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public class MovieInfo implements Comparable<MovieInfo> {
    private final String id;
    private final File video;
    private final File cover;
    private final AtomicLong count = new AtomicLong(0);
    private final AtomicBoolean favorite = new AtomicBoolean(false);
    private final boolean isSupported;
    public MovieInfo(String id, File video, File cover) {
        this.id = id;
        this.video = video;
        this.cover = cover;
        this.count.set(0);
        this.favorite.set(false);
        this.isSupported = isSupported(video);
    }
    public MovieInfo(Click click, File video, File cover) {
        this.id = click.getId();
        this.video = video;
        this.cover = cover;
        this.count.set(click.getCount());
        this.favorite.set(click.getFavorite());
        this.isSupported = isSupported(video);
    }
    public boolean isSupported() {
        return isSupported;
    }
    public long click() {
        return count.incrementAndGet();
    }
    public void deleteFile() {
        video.delete();
        cover.delete();
    }
    public long getCount() {
        return count.get();
    }
    public String getID() {
        return id;
    }
    public File getVideo() {
        return video;
    }
    public File getCoverFile() {
        return cover;
    }
    public void setFavorite(boolean value) {
        favorite.set(value);
    }
    public boolean getFavorite() {
        return favorite.get();
    }
    public BufferedImage readCover() throws IOException {
        return ImageIO.read(cover);
    }
    @Override
    public int compareTo(MovieInfo o) {
        return id.compareTo(o.getID());
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return compareTo((MovieInfo)obj) == 0;
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        return str.append("id = ")
           .append(id)
           .append(", file = ")
           .append(video.getAbsolutePath())
           .append(", cover = ")
           .append(cover.getAbsolutePath())
           .append(", click count = ")
           .append(count.get())
           .append(", favorite = ")
           .append(favorite)
           .append(", isSupported = ")
           .append(isSupported).toString();
    }
    public static boolean isSupported(File file) {
        try (InputStream stream = file.toURI().toURL().openStream()) {
            byte[] signature = new byte[MediaUtils.MAX_FILE_SIGNATURE_LENGTH];
            int size = stream.read(signature);
            String contentType = MediaUtils.filenameToContentType(file.toURI().toString());
            if (Locator.DEFAULT_CONTENT_TYPE.equals(contentType)) {
                contentType = MediaUtils.fileSignatureToContentType(signature, size);
            }
            return MediaManager.canPlayContentType(contentType);
        } catch (MalformedURLException ex) {
            return false;
        } catch (IOException | MediaException ex) {
            return false;
        }
        
    }
}
