package ui;

import entities.Player;
import gameStates.Playing;
import main.Game;

import java.awt.*;

public class StarMenu {

    Playing playing;

    public StarMenu(Playing playing) {
        this.playing = playing;
    }

    public void draw(Graphics g) {

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(Game.GAME_WIDTH / 3, 0, Game.GAME_WIDTH / 3, Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("YOU WON", Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2);

    }
}
