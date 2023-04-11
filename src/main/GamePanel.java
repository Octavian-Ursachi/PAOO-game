package main;

import inputs.Keyboardinputs;
import inputs.Mouseinputs;
import javax.swing.*;
import java.awt.*;
import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HEIGHT;


public class GamePanel extends JPanel {

    private Mouseinputs mouseinputs;
    private Game game;

    public GamePanel(Game game){

        mouseinputs = new Mouseinputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new Keyboardinputs(this));
        addMouseListener(mouseinputs);
        addMouseMotionListener(mouseinputs);


    }

    private void setPanelSize() { // Seteaza marimea panoului

        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size); //Setata cu jframe.pack(); in GameWindow.java
        System.out.println("size : "+GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    public void updateGame(){

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        game.render(g);
    }

    public Game getGame(){
        return game;
    }

}
