/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Letter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
//    ImageView letter = new ImageView();
//    oi.setLayoutX(900);
//    TranslateTransition transition = new TranslateTransition(new Duration(1000), oi);
//    transition.setToX(-900);
//    transition.play();
    Letter letter = new Letter();
    letter.setX(900);
    letterPane.getChildren().add(letter);
    draggerPane.setVisible(false);
    letterEvents(letter);
    
    TranslateTransition translate = new TranslateTransition(new Duration(2000), letter);
    translate.setToX(-900);
    translate.play();
    

    // TODO
  }
  
  private void letterEvents(Letter letter){
    letter.setOnMousePressed((MouseEvent event) -> {
      if (draggerPane.getChildren().isEmpty()) {
        letterPane.getChildren().remove(letter);
        draggerPane.getChildren().add(letter);
        draggerPane.setVisible(true);
        letter.setX(letter.getX() + letterPane.getLayoutX());
        letter.setY(letter.getY() + letterPane.getLayoutY());
      }
    });
  }

}
