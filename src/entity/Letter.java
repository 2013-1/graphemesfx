/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
    try {
      String letter = getName().toLowerCase();
      
      lowercase = letter.equals(getName());
      URL url = new URL(((lowercase) ? getLowerCasePath() : getUpperCasePath()) 
              + "letter.png");
      Image image = new Image(url.toString());
      
      setImage(image);
      ImageIcon icon = new ImageIcon(url);
      setFitWidth(image.getWidth());
      setFitHeight(image.getHeight());
      
      
      addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
          Point mouse = MouseInfo.getPointerInfo().getLocation();
//          Point tela = getLocationOnScreen();
//
//          tela.x -= getLocation().x;
//          tela.y -= getLocation().y;
//
//          mouse.x -= tela.x + getWidth() / 2;
//          mouse.y -= tela.y + getHeight() / 2;
//          setLocation(mouse);
        }
      });
    }
    catch (MalformedURLException ex) {
      Logger.getLogger(Letter.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void setOldLocation(Point location) {
    this.oldLocation = location;
  }

  public Point getOldLocation() {
    return oldLocation;
  }

  public String getLowerCasePath() {
    char bar = '/';
    return getClass().getResource(bar + "letters" + bar
            + getName().trim().toLowerCase() + bar
            + "lowercase" + bar).toString();
  }

  public String getUpperCasePath() {
    char bar = '/';
    String path = bar + "letters" + bar
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
