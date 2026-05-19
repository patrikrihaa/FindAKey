package Game;

import Inputs.KeyHandler;
import Objects.*;
import Screens.GameScreen;

import javax.swing.*;
import javax.swing.Box;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Renderable {
    private ArrayList<Box> boxes;
    private ArrayList<Spike> spikes;
    private Door door;
    private Key key;
    private Player player;
    private Terrain terrain;

    private static final int screenWidth = 900;
    private static final int screenHeight = 500;
    private static final int mapWidth = 2400;
    private  int cameraX = 0;
    private boolean IsFinished = false;

    private boolean hintVisible;
    private int hintTimer;

    private final KeyHandler inputs;
    private final GameScreen screen;
    private Thread gameThread;

    public GamePanel(GameScreen screen) {
        this.screen = screen;
        setBackground(Color.BLACK);
        setFocusable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
        inputs = new KeyHandler();
        addKeyListener(inputs);
        initGame();
    }

    public void initGame() {
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {

    }
}