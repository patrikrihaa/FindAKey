package Screens;

import Game.GameLoop;

import javax.swing.*;

public class GameScreen extends JFrame {

    public GameScreen() {
        super("Locked");

        GameLoop game = new GameLoop(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        add(game);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        game.requestFocusInWindow();
        game.startGameThread();
    }
}