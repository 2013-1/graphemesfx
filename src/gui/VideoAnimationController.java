/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    fadeIn();
    loadVideo();
    videoTransition();
  }

  public void fadeIn() {
    FadeTransition fade = new FadeTransition(Duration.seconds(2), backgroundPane);
    fade.setFromValue(0.0);
    fade.setToValue(0.5);
    fade.play();
  }

  public void fadeOut() {
    FadeTransition fade = new FadeTransition(Duration.seconds(2), backgroundPane);
    fade.setFromValue(0.5);
    fade.setToValue(0);
    fade.play();
  }

  private void loadVideo() {
    String source = getClass().getResource("/resource/a/uppercase/video.mp4").toString();
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
  }

  private void videoTransition() {
    AnchorPane parent = (AnchorPane) mediaView.getParent();
    double height = parent.getHeight() / 3;
    double diferenca = height / mediaView.getFitHeight();
    
    ScaleTransition scale = new ScaleTransition(Duration.seconds(2), mediaView);
  }

  @FXML
  private void playAction(ActionEvent event) {
    if (player.getStatus() == Status.PLAYING) {
      player.pause();
    }
    else {
      player.play();
    }
  }

  @FXML
  private void closeAction(ActionEvent event) {
    fadeOut();
    Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), (e) -> {
      StackPane parent = (StackPane) parentPane.getParent();
      parent.getChildren().remove(parentPane);
    }));
    timer.play();
  }

}
