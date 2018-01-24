package pl.kruczek.mp3player.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.kruczek.mp3player.mp3.Mp3Parser;
import pl.kruczek.mp3player.mp3.Mp3Player;
import pl.kruczek.mp3player.mp3.Mp3Song;

public class MainController implements Initializable {

    @FXML
    private ContentPaneController contentPaneController;
    @FXML
    private ControlPaneController controlPaneController;
    @FXML
    private MenuPaneController menuPaneController;

    private Mp3Player mp3Player;
    private Mp3Parser mp3Parser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("MainController| initialize");
        mp3Player = new Mp3Player();
        mp3Parser = new Mp3Parser();
        configControlPaneAction();
        configureVolume();
        configureTable();
        configureMenu();


    }


    private void configureMenu() {
        MenuItem openFile = menuPaneController.getOpenMp3Item();
        MenuItem openDir = menuPaneController.getOpenDirItem();

        openFile.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().add(new ExtensionFilter("MP3", "*.mp3"));
                File file = fc.showOpenDialog(new Stage());

//                mp3Player.getMp3Collection().clearList();
                if (file != null) {
                    mp3Player.getMp3Collection().addSong(mp3Parser.createMp3Song(file));
                }


            }
        });

        openDir.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser dc = new DirectoryChooser();
                File dir = dc.showDialog(new Stage());


                if (dir != null) {
                    mp3Player.getMp3Collection().clearList();
                    mp3Player.getMp3Collection().addSongs(mp3Parser.createMp3Songs(dir));
                }

            }
        });

    }

    private void configureTable() {
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        contentTable.setItems(mp3Player.getMp3Collection().getSongList());
        contentTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && !contentTable.getSelectionModel().isEmpty()) {
                    mp3Player.loadSong(contentTable.getSelectionModel().getSelectedIndex());
                    configureProgressBar();
                    controlPaneController.getPlayButton().setSelected(true);
                }

            }

        });
    }

    private void configureVolume() {
        Slider volSlider = controlPaneController.getVolumeSlider();
        final double minVolume = 0;
        final double maxVolume = 1;
        volSlider.setMin(minVolume);
        volSlider.setMax(maxVolume);
        volSlider.setValue(maxVolume);
        volSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mp3Player.setVolume(newValue.doubleValue());

            }
        });
    }

    private void configControlPaneAction() {
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        ToggleButton playButton = controlPaneController.getPlayButton();
        Button prevButton = controlPaneController.getPrevButton();
        Button nextButton = controlPaneController.getNextButton();

        playButton.setDisable(true);
        prevButton.setDisable(true);
        nextButton.setDisable(true);

        contentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Mp3Song>() {
            @Override
            public void changed(ObservableValue<? extends Mp3Song> observable, Mp3Song oldValue, Mp3Song newValue) {
                if (newValue != null) {
                    playButton.setDisable(false);
                    prevButton.setDisable(false);
                    nextButton.setDisable(false);
                }
            }
        });

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (playButton.isSelected()) {
                    mp3Player.play();

                } else {
                    mp3Player.pause();
                }
            }
        });

        nextButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() + 1);
                mp3Player.loadSong(contentTable.getSelectionModel().getSelectedIndex());
            }
        });

        prevButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() - 1);
                mp3Player.loadSong(contentTable.getSelectionModel().getSelectedIndex());

            }
        });

    }

    private void configureProgressBar() {
        Slider songSlider = controlPaneController.getSongSlider();

        if (mp3Player != null) {

            mp3Player.getMediaPlayer().setOnReady(new Runnable() {

                @Override
                public void run() {
                    songSlider.setMax(mp3Player.getLoadedSongLength());

                }
            });
        }

        if (mp3Player != null) {
            mp3Player.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {

                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    songSlider.setValue(newValue.toSeconds());

                }
            });
        }

        songSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (songSlider.isValueChanging()) {
                    mp3Player.getMediaPlayer().seek(Duration.seconds(newValue.doubleValue()));
                }

            }
        });
    }

    private void testMp3Add() {
        mp3Player.getMp3Collection().addSong(createMp3SongFromPath("test.mp3"));
    }

    private Mp3Song createMp3SongFromPath(String filePath) {
        File file = new File(filePath);
        Mp3Song result = new Mp3Song();
        try {
            MP3File mp3File = new MP3File(file);
            result.setFilePath(file.getAbsolutePath());
            result.setTitle(mp3File.getID3v2Tag().getSongTitle());
            result.setAuthor(mp3File.getID3v2Tag().getLeadArtist());
            result.setAlbum(mp3File.getID3v2Tag().getAlbumTitle());
        } catch (IOException | TagException e) {
            e.printStackTrace();
        }
        return result;
    }

}
