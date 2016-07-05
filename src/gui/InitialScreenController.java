/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Yan Kaic
 */
public class InitialScreenController implements Initializable {

  Image buttonDown;
  Image buttonUp;
  Image buttonLight;
  @FXML
  private ImageView imageButton;
  @FXML
  private StackPane superParent;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    buttonDown = new Image(getClass().getResource("/images/buttondown.png").toString());
    buttonUp = new Image(getClass().getResource("/images/button.png").toString());
    buttonLight = new Image(getClass().getResource("/images/buttonlight.png").toString());
    // TODO
  }  



  @FXML
  private void highImage(MouseEvent event) {
    imageButton.setImage(buttonUp);
  }

  @FXML
  private void lowImage(MouseEvent event) {
    imageButton.setImage(buttonLight);
  }

  @FXML
  private void pressedButton(MouseEvent event) {
    imageButton.setImage(buttonDown);
  }

  @FXML
  private void imageAction(MouseEvent event) {
    StackPane parent = (StackPane) superParent.getParent();
    parent.getChildren().remove(superParent);
  }
  
}
