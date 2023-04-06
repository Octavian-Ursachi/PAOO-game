package main;

import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class Menu {

    public Rectangle playButton = new Rectangle(GAME_WIDTH/2 - 80,150,100,50);
    public Rectangle editorButton = new Rectangle(GAME_WIDTH/2 - 80,250,100,50);
    public Rectangle quitButton = new Rectangle(GAME_WIDTH/2 - 80,350,100,50);



    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font fnt0 = new Font("arial",Font.BOLD,50);
        g.setFont(fnt0);
        g.setColor(Color.black);
        g.drawString("SWIFT ARMOR",GAME_WIDTH/3,GAME_HEIGHT/6);


        Font fnt1 = new Font("arial", Font.BOLD,30);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x+19,playButton.y+30);
        g2d.draw(playButton);
        g.drawString("Edit", editorButton.x+19,editorButton.y+30);
        g2d.draw(editorButton);
        g.drawString("Quit", quitButton.x+19,quitButton.y+30);
        g2d.draw(quitButton);
        g2d.draw(editorButton);
        g2d.draw(quitButton);


    }

}
