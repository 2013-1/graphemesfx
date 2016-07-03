/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphemefx;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Yan Kaic
 */
public class GraphemeFX extends Application {

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SwipeView.fxml"));
      StackPane root = loader.load();

      Scene scene = new Scene(root);

      primaryStage.setTitle("Graphemes");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();
    }
    catch (IOException ex) {
      Logger.getLogger(GraphemeFX.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
