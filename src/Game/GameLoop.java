package Game;

import Inputs.KeyHandler;
import Objects.*;
import Objects.Box;
import Screens.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameLoop extends JPanel implements Runnable {
    private ArrayList<Box> boxes;
    private ArrayList<Spike> spikes;
    private Door door;
    private Key key;
    private Player player;
    private Terrain ceiling;
    private Terrain ground;
    private CollisionHandler collisionHandler;

    private static final int groundY = 420;
    private static final int ceilingY = 80;
    private static final int screenWidth = 900;
    private static final int screenHeight = 500;
    private static final int mapWidth = 2400;
    private  int cameraX = 0;
    private boolean isFinished = false;

    private boolean isDead = false;

    private final KeyHandler inputs;
    private final GameScreen gameScreen;
    private Thread gameThread;

    public GameLoop(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        setBackground(Color.BLACK);
        setFocusable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
        requestFocusInWindow();
        inputs = new KeyHandler();
        addKeyListener(inputs);
        initGame();
    }

    public void initGame() {
        player = new Player(50, groundY - 40, inputs);
        door = new Door(2200, groundY - 120);
        ground = new Terrain(0, groundY, mapWidth, 80, false);
        ceiling = new Terrain(0, 0, mapWidth, ceilingY, true);

        MapBuilder mapBuilder = new MapBuilder(mapWidth, groundY, ceilingY);
        try {
            mapBuilder.build();
        } catch (GameException e) {
            initGame();
            return;
        }

        boxes  = mapBuilder.getBoxes();
        spikes = mapBuilder.getSpikes();
        key    = mapBuilder.getKey();

        collisionHandler = new CollisionHandler(player, boxes, spikes, key, door, ground, ceiling, gameScreen, this);
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
    }

    private void update() {
        if (isDead || isFinished) return;

        player.update();

        if (inputs.isInteract() && Math.abs(player.getX() - door.getX()) < 50) {
            door.interact(player);
        }
        collisionHandler.checkCeiling();
        collisionHandler.checkGround();
        collisionHandler.checkDoor();
        collisionHandler.checkKey();
        collisionHandler.checkBoxes();
        collisionHandler.checkSpikes();


        cameraX = Math.clamp(player.getX() - screenWidth / 2, 0, mapWidth - screenWidth);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double scale = Math.min((double) getWidth() / screenWidth, (double) getHeight() / screenHeight);
        int offsetX  = (int) ((getWidth()  - screenWidth  * scale) / 2);
        int offsetY  = (int) ((getHeight() - screenHeight * scale) / 2);
        g2.translate(offsetX, offsetY);
        g2.scale(scale, scale);
        g2.setColor(new Color(30, 25, 35));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        ground.draw(g2, cameraX);
        ceiling.draw(g2, cameraX);

        for (Spike spike : spikes) {
            spike.draw(g2, cameraX);
        }
        for (Box box : boxes) {
            box.draw(g2, cameraX);
        }

        key.draw(g2, cameraX);
        door.draw(g2, cameraX);
        player.draw(g2, cameraX);

        drawHUD(g2);
    }

    private void drawHUD(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 130));
        g.fillRoundRect(8, 8, 215, 32, 4, 4);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        if (player.hasKey()) {
            g.setColor(new Color(255, 210, 0));
            g.drawString("Key: collected ✓", 18, 29);
        } else {
            g.setColor(Color.WHITE);
            g.drawString("Key: not found", 18, 29);
        }

        if (player.hasKey() && !door.isOpen() && Math.abs(player.getX() - door.getX()) < 130) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRoundRect(screenWidth/2 - 110, screenHeight - 52, 220, 34, 10, 10);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("[ E ] Unlock the door", screenWidth/2 - 88, screenHeight - 30);
        }
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

}