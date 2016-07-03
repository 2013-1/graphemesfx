/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Collections;

/**
 *
 * @author shanks
 */
public class GameControll {

  private Controller controllerLetters;//objeto que controla o estado do jogo

  public GameControll() {
    controllerLetters = new Controller();  
  }//fim contrutor

  public void initGame() {
    //carregando ou recarregando a lista de letras do jogo
    controllerLetters.loadLetters();//carrega as letras

    if (controllerLetters.isLettersEmpty()) {
      controllerLetters.reloadLetters();
    }//fim if  
    
  }//fim initGame

  /**
   * Método que retorna a próxima letra do jogo
   * @return letter Letter
   */
  public Letter nextLetter() {
    Letter letter = new Letter(controllerLetters.getLetter());
    return letter;
  }//fim nextLetter

  public Form[] nextForms(int size) {
    Form forms[] = new Form[size];
    String names[] = controllerLetters.getLasts(size);
    for (int i = 0; i < size; i++) {
      forms[i] = new Form(new Letter(names[i]));
    }
    return forms;
  }
}
