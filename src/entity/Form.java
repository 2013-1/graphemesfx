/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.sun.javafx.geom.Rectangle;
import java.awt.Point;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;

/**
 *
 * @author Yan Kaic
 */
public class Form extends AnchorPane {

  private ImageView mask;
  private ImageView base;
  private Image acceptedImage;
  private Image recusedImage;
  private final Letter letter;
  private String name;

  public Form() {
    this.letter = new Letter("A");
    this.name = letter.getName();
    init();
  }

  public Form(Letter letter) {
    this.letter = letter;
    this.name = letter.getName();
    init();
//    setVisible(false);
  }

  private void init() {
    String source = letter.getPath();
    base = new ImageView(source + "form.png");
    mask = new ImageView();
    setWidth(base.getFitWidth());
    setHeight(base.getFitHeight());
    acceptedImage = new Image(source + "form_yes.png");
    recusedImage = new Image(source + "form_no.png");
    getChildren().add(base);
    getChildren().add(mask);
    
  }

  public void recuse() {
    mask.setImage(recusedImage);
  }

  public void accept() {
    mask.setImage(acceptedImage);
  }

  public void normalize() {
    mask.setImage(null);
  }

  public void fit(Letter letter) throws IllegalArgumentException {
    if (!check(letter)) {
      throw new IllegalArgumentException("this letter is not compatible.");
    }
    letter.setTranslateX(0);
    getChildren().add(2,letter);
    letter.setX(0);
    letter.setY(0);
    letter.setLayoutX(0);
    letter.setLayoutY(0);
  }

  public boolean check(Letter letter) {
    if (intersection(letter) > 0) {
      if (isCompatible(letter)) {
        accept();
        return true;
      }
      recuse();
      return false;
    }
    normalize();
    return false;
  }

  public boolean isCompatible(Letter letter) {
    return getName().equals(letter.getName());
  }

  public int intersection(Letter letter) {
    Rectangle letterArea = getRectangle(letter);
    Rectangle formArea = getRectangle(this);

    Rectangle intersection = letterArea.intersection(formArea);
    int signal = intersection.width < 0 && intersection.height < 0 ? -1 : 1;
    return intersection.width * intersection.height * signal;
  }

  private static Rectangle getRectangle(Node node) {
    Bounds bounds = node.localToScene(node.getBoundsInLocal());
    Rectangle rectangle = new Rectangle(
            (int) bounds.getMinX(),
            (int) bounds.getMinY(),
            (int) bounds.getWidth(),
            (int) bounds.getHeight()
    );
    return rectangle;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Bounds getRealBounds() {
    return base.localToScene(base.getBoundsInLocal());
  }

}
