package ui;


import gameStates.Playing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopWatch {

    private Playing playing;
    private Timer timer;
    private float elapsedTime;

    public StopWatch(Playing playing) {

        this.playing = playing;

        elapsedTime = 0.00f;
        timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime += 0.01f;
            }
        });
    }

    public void startTimer() {
        if(playing.getPlayer().hasMoved && elapsedTime == 0)
            timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void resetAll() {
        elapsedTime = 0;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }


}
