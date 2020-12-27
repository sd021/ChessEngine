package main.java.com.sd.events;

import main.java.com.sd.view.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler implements ActionListener, Runnable {
    Timer timer=new Timer(10, this);
    GameView gameView;

    public EventHandler(GameView gameView) {
        this.gameView = gameView;
        timer.start();
    }

    @Override
    public void run() {
        while (true) {
            gameView.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == timer) {
              //  gameView.updateBoard();
        }
    }
}