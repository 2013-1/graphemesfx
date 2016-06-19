/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Yan Kaic
 */
public class SwipeViewController implements Initializable {

  @FXML
  private ImageView oi;
  

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ImageView letter = new ImageView();
    oi.setLayoutX(900);
    TranslateTransition transition = new TranslateTransition(new Duration(1000), oi);
    transition.setToX(-900);
    transition.play();
    
    // TODO
  }  
  
}
