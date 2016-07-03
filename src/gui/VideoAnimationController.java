/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Letter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Yan Kaic
 */
public class VideoAnimationController implements Initializable {

  @FXML
  private ImageView playImage;
  @FXML
  private MediaView mediaView;
  private MediaPlayer player;
  @FXML
  private AnchorPane backgroundPane;
  @FXML
  private StackPane parentPane;
  private Letter letter;
  @FXML
  private BorderPane cardPane;
  private boolean hasClock = true;
  private Image soundImage;
  private Image muteImage;
  @FXML
  private ImageView muteView;

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

  }

  public void setLetter(Letter letter) {
    this.letter = letter;
    fadeIn();
    loadVideo();
    Timeline timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
      videoTransition();
    }));
    timer.play();
    timer = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
      player.play();
      Timeline t = new Timeline(new KeyFrame(Duration.seconds(2), ee -> {
        if (hasClock) {
          closeAction();
        }
      }));
      t.play();
    }));
    timer.play();

  }

  public void fadeIn() {
    FadeTransition fade = new FadeTransition(Duration.seconds(2), backgroundPane);
    fade.setFromValue(0.0);
    fade.setToValue(0.75);
    fade.play();
  }

  public void fadeOut() {
    FadeTransition fade = new FadeTransition(Duration.seconds(2), parentPane);
    fade.setFromValue(1);
    fade.setToValue(0);
    fade.play();
  }

  private void loadVideo() {
    String source = letter.getPath() + "video.mp4";
    Media video = new Media(source);
    player = new MediaPlayer(video);
    mediaView.setMediaPlayer(player);
    player.setCycleCount(2);

    player.setOnPlaying(() -> {
      playImage.setImage(new Image(getClass().getResource("/icons/pause.png").toString()));
    });
    player.setOnPaused(() -> {
      playImage.setImage(new Image(getClass().getResource("/icons/play.png").toString()));
    });

    soundImage = new Image(getClass().getResource("/icons/audio.png").toString());
    muteImage = new Image(getClass().getResource("/icons/mute.png").toString());

    FadeTransition fade = new FadeTransition(Duration.seconds(1), mediaView);
    fade.setFromValue(0.0);
    fade.setToValue(1);
    fade.play();
  }

  public void videoTransition() {
    AnchorPane parent = (AnchorPane) cardPane.getParent();
    double height = parent.getHeight() * 2 / 3;
    double scaleY = height / cardPane.getHeight();

    ScaleTransition scale = new ScaleTransition(Duration.seconds(2), cardPane);
    scale.setFromX(1);
    scale.setFromY(1);
    scale.setToY(scaleY);
    scale.setToX(scaleY);

    double x, y;
    x = parent.getWidth() - cardPane.getLayoutX() - cardPane.getWidth() / 2;
    x -= parent.getWidth() / 2;
    y = parent.getHeight() - cardPane.getLayoutY();
    y -= parent.getHeight() / 2;

    TranslateTransition translate = new TranslateTransition(Duration.seconds(2), cardPane);
    translate.setToX(x);
    translate.setToY(y);

    scale.play();
    translate.play();
  }

  @FXML
  public void playAction(ActionEvent event) {
    if (player.getStatus() == Status.PLAYING) {
      player.pause();
    }
    else {
      player.play();
    }
    hasClock = false;
  }

  @FXML
  public void closeAction() {
    fadeOut();
    Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), (e) -> {
      StackPane parent = (StackPane) parentPane.getParent();
      parent.getChildren().remove(parentPane);
    }));
    timer.play();
  }

  @FXML
  private void replayAction(ActionEvent event) {
    hasClock = false;
    player.stop();
    player.play();
  }

  @FXML
  private void muteAction(ActionEvent event) {
    hasClock = false;
    player.setMute(!player.isMute());
    
    muteView.setImage(player.isMute()? muteImage: soundImage);
  }

}
