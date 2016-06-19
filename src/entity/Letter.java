/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Yan Kaic
 */
public class Letter extends ImageView {

  private Point oldLocation;
  private boolean lowercase;
  private String name;

  public Letter() {
    this("A");
  }

  public Letter(String name) {
    super();
    this.name = name;
    init();
  }

  private void init() {

    String letter = getName().toLowerCase();

    lowercase = letter.equals(getName());
    String url = (lowercase) ? getLowerCasePath() : getUpperCasePath();
    url += "letter.png";
    Image image = new Image(url);

    setImage(image);
    setFitWidth(image.getWidth());
    setFitHeight(image.getHeight());

    setOnMouseDragged((javafx.scene.input.MouseEvent event) -> {
      setX(event.getX() - getFitWidth() / 2);
      setY(event.getY() - getFitHeight() / 2);
    });

  }

  public String getLowerCasePath() {
    char bar = '/';
    return getClass().getResource(bar + "resource" + bar
            + getName().trim().toLowerCase() + bar
            + "lowercase" + bar).toString();
  }

  public String getUpperCasePath() {
    char bar = '/';
    String path = bar + "resource" + bar
            + getName().trim().toLowerCase() + bar
            + "uppercase" + bar;
    URL resource = getClass().getResource(path);
    return resource.toString();
  }

  public boolean isLowerCaseLetter() {
    return lowercase;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
