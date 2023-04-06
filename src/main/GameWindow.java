package main;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow{
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel){

        jframe = new JFrame();


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel); // aici am adaugat GamePanel-ul la Frame
        //jframe.setLocationRelativeTo(null);
        jframe.setResizable(false); // nu se poate schimba marimea ferestrei
        jframe.pack(); // cauzeaza ca aceasta fereastra sa fie dimensionata ca sa se potriveasca cu marimea preferata
        jframe.setVisible(true);

    }


}
