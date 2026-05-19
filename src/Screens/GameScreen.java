package Screens;

import Game.GameLoop;

import javax.swing.*;

/**
 * The game window. Creates a GameLoop panel, packs the frame,
 * then starts the game thread once the window is visible.
 */
public class GameScreen extends JFrame {

    /**
     * Sets up and displays the game window immediately.
     * The game thread is started after the window is shown so the panel
     * has its final size before the first frame is rendered.
     */
    public GameScreen() {
        super("Locked");

        GameLoop game = new GameLoop(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        add(game.getRenderer());
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        game.getRenderer().requestFocusInWindow();
        game.startGameThread();
    }
}