/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Form;
import entity.Letter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Yan Kaic
 */
public class SwipeViewController implements Initializable {

  @FXML
  private Pane letterPane;
  @FXML
  private ImageView letter;
  @FXML
  private AnchorPane draggerPane;
  @FXML
  private FlowPane formPane;
  @FXML
  private StackPane superParent;

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    Letter letter = new Letter();
    letter.setX(900);
    letterPane.getChildren().add(letter);
    draggerPane.setVisible(false);
    letterEvents(letter);

    TranslateTransition translate = new TranslateTransition(Duration.seconds(2), letter);
    translate.setToX(-900);
    translate.play();

    Form form = new Form(new Letter("D"));
    formPane.getChildren().add(form);

    form = new Form();
    formPane.getChildren().add(form);

    superParent.getChildren().addListener((ListChangeListener.Change<? extends Node> c) -> {
      if (superParent.getChildren().size() == 3) {
        nextLetter();
      }
    });

    // TODO
  }

  private void letterEvents(Letter letter) {
    letter.setOnMousePressed(MouseEvent -> {
      if (draggerPane.getChildren().isEmpty()) {
        letterPane.getChildren().remove(letter);
        draggerPane.getChildren().add(letter);
        letter.setLayoutX(letterPane.getLayoutX());
        letter.setLayoutY(letterPane.getLayoutY());
        draggerPane.setVisible(true);
      }
    });

    letter.setOnMouseReleased(MouseEvent -> {
      try {
        Form selectedForm = greaterIntersection(letter);
        selectedForm.fit(letter);
        showVideo();
      }
      catch (IllegalArgumentException ex) {

      }

    });

    letter.addEventHandler(MouseEvent.MOUSE_DRAGGED, Event -> {
      Form form = greaterIntersection(letter);
      form.check(letter);
    });
  }

  public void checkAll(Letter letter) {
    for (Node node : formPane.getChildren()) {
      Form form = (Form) node;
      form.check(letter);
    }
  }

  private Form greaterIntersection(Letter letter) {
    int max = -999999;
    Form greater = null;
    for (Node node : formPane.getChildren()) {
      Form form = (Form) node;
      form.normalize();

      if (form.intersection(letter) > max) {
        max = form.intersection(letter);
        greater = form;
      }
    }
    return greater;
  }

  private void showVideo() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/VideoAnimation.fxml"));
      StackPane showVideo = loader.load();
      superParent.getChildren().add(showVideo);
    }
    catch (IOException ex) {
      Logger.getLogger(SwipeViewController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  private void nextLetter() {

  }

}
