/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.player;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import net.spright.theater.model.BaseSubject;
import net.spright.theater.model.MovieInfo;

/**
 *
 * @author ChiaPing Tsai <chia7712@gmail.com>
 */
public class SimplePlayer extends BaseSubject implements MoviePlayer {
    private static final String PLAY_STRING = ">";
    private static final String PAUSED_STRING = "||";
    private static final String STOP_STRING = "X";
    private final MediaPlayer mp;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private final MovieInfo movieInfo;
    private final PlayerPane pane;
    public SimplePlayer(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
        this.mp = new MediaPlayer(new Media(movieInfo.getVideo().toURI().toString()));
        this.pane = new PlayerPane(mp);
        pane.getPlayButton().setOnAction((ActionEvent e) -> {
            pane.updateValues();
            Status status = mp.getStatus();
            if (status == Status.UNKNOWN || status == Status.HALTED) {
            }
            
            if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
                // rewind the movie if we're sitting at the end
                if (atEndOfMedia) {
                    mp.seek(mp.getStartTime());
                    atEndOfMedia = false;
                    pane.getPlayButton().setText(PLAY_STRING);
                    pane.updateValues();
                }
                mp.play();
                pane.getPlayButton().setText(PAUSED_STRING);
            } else {
                mp.pause();
            }
        });
        pane.getStopButton().setOnAction((ActionEvent e) -> {
            mp.stop();
        });
        mp.currentTimeProperty().addListener((Observable observable) -> {
            pane.updateValues();
            
        });
        mp.setOnPlaying(() -> {
            if (stopRequested) {
                mp.pause();
                stopRequested = false;
            } else {
                pane.getPlayButton().setText(PAUSED_STRING);
            }
            inform();
        });
        mp.setOnPaused(() -> {
            pane.getPlayButton().setText(PLAY_STRING);
            inform();
        });

        mp.setOnReady(() -> {
            pane.setDuration(mp.getMedia().getDuration());
            pane.updateValues();
            inform();
        });

        mp.setOnStopped(() -> {
            mp.stop();
            inform();
        });
        mp.setOnEndOfMedia(() -> {
            mp.stop();
            inform();
        });

        pane.getTimeSlider().valueProperty().addListener((Observable ov) -> {
            if (pane.getTimeSlider().isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                pane.getDuration().ifPresent((Duration duration) -> {
                    mp.seek(duration.multiply(pane.getTimeSlider().getValue() / 100.0));
                });
                pane.updateValues();
            }
        });

        pane.getVolumnSlider().valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if ( pane.getVolumnSlider().isValueChanging()) {
                mp.setVolume( pane.getVolumnSlider().getValue() / 100.0);
            }
        });
        
    }
    @Override
    public Status getStatus() {
        return mp.getStatus();
    }
    @Override
    public MovieInfo getMovieInfo() {
        return movieInfo;
    }
    @Override
    public Node asNode() {
        return pane;
    }

    private static class PlayerPane extends BorderPane {
        private final MediaView mediaView;
        private final Slider timeSlider = new Slider();
        private final Slider volumeSlider = new Slider();
        private final HBox mediaBar = new HBox();
        private final MediaPlayer mp;
        private final Button playButton = new Button(PLAY_STRING);
        private final Button stopButton = new Button(STOP_STRING);
        private Duration duration;
        public PlayerPane(MediaPlayer mp) {
            this.mediaView = new MediaView(mp);
            this.mp = mp;
            
            mediaBar.setAlignment(Pos.CENTER);
            mediaBar.setPadding(new Insets(5, 10, 5, 10));
            BorderPane.setAlignment(mediaBar, Pos.CENTER);

            timeSlider.setMaxWidth(Double.MAX_VALUE);
            timeSlider.setMinWidth(30);
            HBox.setHgrow(timeSlider, Priority.ALWAYS);
            
            volumeSlider.setPrefWidth(70);
            volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
            volumeSlider.setMinWidth(30);
            
            mediaBar.getChildren().add(playButton);
            mediaBar.getChildren().add(stopButton);
            mediaBar.getChildren().add(timeSlider);
            mediaBar.getChildren().add(volumeSlider);
            
        }
        public void setDuration(Duration duration) {
            this.duration = duration;
        }
        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }
        public Button getPlayButton() {
            return playButton;
        }
        public Button getStopButton() {
            return stopButton;
        }
        public Slider getVolumnSlider() {
            return volumeSlider;
        }
        public Slider getTimeSlider() {
            return timeSlider;
        }
        private void initPane() {
            Pane mvPane = new Pane();
            mvPane.getChildren().add(mediaView);
            mvPane.setId("mediaViewPane");
            setCenter(mvPane);
            setBottom(mediaBar);
        }
        private void updateValues() {
            if (timeSlider != null && volumeSlider != null) {
                Platform.runLater(() -> {
                    Duration currentTime = mp.getCurrentTime();
                    if (duration == null) {
                        timeSlider.setDisable(true);
                    } else {
                        timeSlider.setDisable(duration.isUnknown());
                    }
                    if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
                    }
                });
            }
        }
        @Override
        protected void layoutChildren() {
            if (mediaView != null && getBottom() != null) {
                mediaView.setFitWidth(getWidth());
                mediaView.setFitHeight(getHeight() - getBottom().prefHeight(-1));
            }
            super.layoutChildren();
            if (mediaView != null) {
                mediaView.setTranslateX((((Pane) getCenter()).getWidth() - mediaView.prefWidth(-1)) / 2);
                mediaView.setTranslateY((((Pane) getCenter()).getHeight() - mediaView.prefHeight(-1)) / 2);
            }
        }

        @Override
        protected double computeMinWidth(double height) {
            return mediaBar.prefWidth(-1);
        }

        @Override
        protected double computeMinHeight(double width) {
            return 200;
        }

        @Override
        protected double computePrefWidth(double height) {
            return Math.max(mp.getMedia().getWidth(), mediaBar.prefWidth(height));
        }

        @Override
        protected double computePrefHeight(double width) {
            return mp.getMedia().getHeight() + mediaBar.prefHeight(width);
        }

        @Override
        protected double computeMaxWidth(double height) {
            return Double.MAX_VALUE;
        }

        @Override
        protected double computeMaxHeight(double width) {
            return Double.MAX_VALUE;
        } 
    }


    @Override
    public void close() throws IOException {
        mp.stop();
        mp.dispose();
    }
}
