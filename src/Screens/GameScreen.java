package Screens;

import javax.swing.*;

public class GameScreen extends JFrame {

    public GameScreen(){
        super("Locked");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}