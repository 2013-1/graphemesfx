/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Letter;
import graphemefx.GraphemeFX;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.security.util.Length;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import javafx.animation.Animation;

/**
 * FXML Controller class
 *
 * @author Iago
 */
public class LowerCaseAnimationController implements Initializable {

    @FXML
    private StackPane parentPane;
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private ImageView playImage;
    @FXML
    private ImageView muteImage;
    @FXML
    private HBox buttonPane;
    @FXML
    private StackPane imagesPane;

    //variáveis de estado da animação
    private boolean isPlay, isMute;

    //letra que está sendo animada
    private Letter letter;

    //componente de audio
    private AudioClip audio;

    private String audioSource;

    //componente de translação
    private TranslateTransition translate;

    //componente de escala
    private ScaleTransition scale;

    //imagens da animação
    private ImageView images[];

    private Timeline timerAudio;

    private Timeline timerTranslacaoH;

    private Timeline timerClose;

    private Timeline timerReplay;

    int indexImage = 0;

    private int indexImage2;

    private boolean isClosing;
    
    private boolean isRepeatOnlyAudio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO               
    }//fim initialize    

    /**
     * Método que inicia e pausa a animação da letra
     *
     * @param event
     */
    @FXML
    private void playAction(ActionEvent event) {
        String path = getClass().getResource(GraphemeFX.BAR + "icons" + GraphemeFX.BAR).toString();
        if (isPlay) {
            path += "play.png";
            playImage.setImage(new Image(path));
            isPlay = false;
            //pausando a animação
            if (timerAudio != null) {//pausando o audio
                timerAudio.pause();
            }//fim if
            if (timerClose != null) {//pausando o close automático
                timerClose.pause();
            }//fim if
            if (timerTranslacaoH != null) {//pausando a translação dos centros
                timerTranslacaoH.pause();
            }//fim if
            if (translate != null) {//pausando a translação inicial e/ou do centro
                translate.pause();
            }//fim if
            if (scale != null) {//pausando a escala
                scale.pause();
            }//fim if
            if (timerReplay != null) {//pausando o timer para o replay
                timerReplay.pause();
            }//fim if
        } else {
            path += "pause.png";
            playImage.setImage(new Image(path));
            isPlay = true;
            //retomando a animação
            if (timerAudio != null && timerAudio.getStatus() == Timeline.Status.PAUSED) {//pausando o audio
                timerAudio.play();
            }//fim if
            if (timerClose != null && timerClose.getStatus() == Timeline.Status.PAUSED) {//pausando o close automático
                timerClose.play();
            }//fim if
            if (timerTranslacaoH != null && timerTranslacaoH.getStatus() == Timeline.Status.PAUSED) {//pausando a translação dos centros
                timerTranslacaoH.play();
            }//fim if
            if (translate != null && translate.getStatus() == Animation.Status.PAUSED) {//pausando a translação inicial e/ou do centro
                translate.play();
            }//fim if
            if (scale != null && scale.getStatus() == Animation.Status.PAUSED) {//pausando a escala
                scale.play();
            }//fim if
            if (timerReplay != null && timerReplay.getStatus() == Timeline.Status.PAUSED) {
                timerReplay.play();
            }//fim if
        }//fim if-else
        isClosing = false;
    }//fim playAction

    /**
     * Método que encerra a animação e fecha a janela de animação, retornando
     * para o SwipeView
     *
     * @param event
     */
    @FXML
    private void closeAction(ActionEvent event) {
        fadeOut(backgroundPane, 1);
        fadeOut(buttonPane, 1);
        fadeOut(imagesPane, 1);
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2),
                (e) -> {
                    StackPane parent = (StackPane) parentPane.getParent();
                    parent.getChildren().remove(parentPane);
                })
        );
        timer.play();
    }//fim closeAction

    /**
     * Método que reinicia a animação da letra
     *
     * @param event
     */
    @FXML
    private void replayAction(ActionEvent event) {
        String path = getClass().getResource(GraphemeFX.BAR + "icons" + GraphemeFX.BAR).toString() + "pause.png";
        isPlay = true;
        isClosing = false;
        playImage.setImage(new Image(path));
        String pathAudio = letter.getLowerCasePath() + "examples" + GraphemeFX.BAR;
        if (isRepeatOnlyAudio) {
            if (timerAudio != null ){
                if (audio != null) {
                    audio.stop();
                }//fim if
                if(translate!=null && translate.getStatus() == Animation.Status.RUNNING){
                    translate.stop();
                }//fim if
                if(timerTranslacaoH!=null && timerTranslacaoH.getStatus() == Timeline.Status.RUNNING){
                    timerTranslacaoH.stop();
                }//fim if
                
                isRepeatOnlyAudio = true;
                timerAudio.stop();
                playAudioAnimation(pathAudio);
            }//fim if
        } else if (timerAudio != null && timerAudio.getStatus() == Timeline.Status.RUNNING) {
            if (timerTranslacaoH != null && timerTranslacaoH.getStatus() == Timeline.Status.RUNNING) {
                timerTranslacaoH.stop();
            }//fim if
            if (timerReplay != null && timerReplay.getStatus() == Timeline.Status.RUNNING) {
                timerReplay.stop();
            }//fim if
            if (audio != null) {
                audio.stop();
            }//fim if
            if(translate!=null && translate.getStatus() == Animation.Status.RUNNING){
                translate.stop();
            }//fim if
            isRepeatOnlyAudio = false;
            timerAudio.stop();
            for(ImageView img: images){
                img.setTranslateX(0);
            }
            playAudioAnimation(letter.getLowerCasePath() + "examples" + GraphemeFX.BAR);
        }//fim if//fim if-else  
    }//fim replayAction

    /**
     * Método que desabilita e habilita o audio da animação
     *
     * @param event
     */
    @FXML
    private void muteAction(ActionEvent event) {
        String path = getClass().getResource(GraphemeFX.BAR + "icons" + GraphemeFX.BAR).toString();
        isClosing = false;
        if (isMute) {
            path += "audio.png";
            muteImage.setImage(new Image(path));
            isMute = false;
        } else {
            path += "mute.png";
            muteImage.setImage(new Image(path));
            isMute = true;
        }//fim if-else
    }//fim muteAction

    /**
     * Método que modifica a letra que estará sendo animada
     *
     * @param letter
     */
    public void setLetter(Letter letter) {
        this.letter = letter;
        init();
    }//fim setLetter

    /**
     * Método que realiza o efeito de fadeIn em um node
     *
     * @param node elemento que recebe o efeito
     * @param n float : taxa do efeito
     * @param seconds int: tempo de animação
     */
    private void fadeIn(Node node, float n, int seconds) {
        FadeTransition fade = new FadeTransition(Duration.seconds(seconds), node);
        fade.setFromValue(0f);
        fade.setToValue(n);
        fade.play();
    }//fim fadeIn

    /**
     * Método que realiza o efeito de fadeOut no background
     *
     * @param node elemento que recebe o efeito
     * @param seconds int: tempo de animação
     */
    private void fadeOut(Node node, int seconds) {
        FadeTransition fade = new FadeTransition(Duration.seconds(seconds), node);
        fade.setFromValue(node.getOpacity());
        fade.setToValue(0);
        fade.play();
    }//fim fadeOut

    private void initAnimation() {
        //quantidade de exemplos
        int maxImages = 3;// 

        //vetor com os exemplos
        images = new ImageView[maxImages];

        //caminho para as imagens dos exemplos
        String path = letter.getLowerCasePath() + "examples" + GraphemeFX.BAR;

        //inicializando as imagens da animação
        for (int i = 0; i < images.length; i++) {
            images[i] = new ImageView(new Image(path + (i + 1) + GraphemeFX.BAR + "image.png"));
            images[i].setFitHeight(1);
            images[i].setFitWidth(1);
            imagesPane.getChildren().add(images[i]);
        }//fim for

        //tamanho da tela
        double viewWidth = 900;
        double viewHeight = 600;
        double widthImage = images[0].getImage().getWidth(),
                heightImage = images[0].getImage().getHeight(),
                deltaX = imagesPane.getLayoutX() - viewWidth / 2,
                deltaY = (viewHeight - heightImage) / 2;

        //redimensionando as imagens
        scale = new ScaleTransition(new Duration(3000), imagesPane);
        scale.setFromX(1);
        scale.setFromY(1);
        scale.setToX(widthImage);
        scale.setToY(heightImage);

        translate = new TranslateTransition(new Duration(3000), imagesPane);
        translate.setToX(-deltaX);
        translate.setToY(deltaY);

        scale.play();
        translate.play();

        playAudioAnimation(path);

        isClosing = true;
        timerClose = new Timeline(new KeyFrame(new Duration(15000),
                ae
                -> {
            if (isClosing) {
                closeAction(ae);
            }//fim if                      
        })//fim keyframe
        );//fim timeline
        timerClose.play();
     
    } //fim initAnimation

    private void playAudioAnimation(String path) {
        indexImage = images.length;
        timerAudio = new Timeline(new KeyFrame(new Duration(3000),
                ae -> {
                    if (!isMute) {
                        audioSource = path + (indexImage) + GraphemeFX.BAR + "audio.aiff";
                        audio = new AudioClip(audioSource);
                        audio.play();
                    } //fim if                    
                    --indexImage;
                    if(!isRepeatOnlyAudio){
                        timerTranslacaoH = new Timeline(new KeyFrame(new Duration(1500),
                                ae2
                                -> {
                            double delta = ((indexImage) % 3 == 2) ? -1 //caso o indice da imagem mod 3 seja igual a 1 (esquerda)
                                    : ((indexImage) % 3 == 1) ? 1 //caso o indice da imagem mod 3 seja igual a 2 (direita)
                                            : 0; //caso o indice da imagem mod 3 seja igual a 0 (centro)
                            translate = new TranslateTransition(new Duration(1800), images[indexImage]);
                            translate.setToX(delta);
                            translate.play();
                        })//fim KeyFrame
                        );//fim timer
                        timerTranslacaoH.play();
                    }
                })//fim keyframe
        );//fim timeline
        timerAudio.setCycleCount(3);
        timerAudio.play();
        
        timerReplay = new Timeline(new KeyFrame(new Duration(9000),
            ae -> {
                isRepeatOnlyAudio = true;
            })//fim keyframe
        );//fim timerRepeat
        timerReplay.play();
    }//fim playAudioAnimation

    public void init() {
        isPlay = true;
        isMute = isRepeatOnlyAudio = false;
        buttonPane.setVisible(false);
        fadeIn(backgroundPane, 0.7f, 2);

        Timeline timerButtonPane = new Timeline(new KeyFrame(new Duration(1000),
                (ae) -> {
                    buttonPane.setOpacity(0f);
                    buttonPane.setVisible(true);
                    fadeIn(buttonPane, 1f, 1);
                })//fim KeyFrame
        );//fim timerButtonPane
        timerButtonPane.play();
        initAnimation();
    }//fim  init

}//fim class
