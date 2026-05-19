package Game;

import Inputs.KeyHandler;
import Objects.*;
import Objects.Player.Player;
import Objects.Traps.Trap;
import Screens.GameScreen;

import java.util.List;

/**
 * Core of the game — runs the update loop on a background thread
 * and owns all the game objects for the current run.
 * Rendering is handled separately by GameRenderer.
 *
 * Implements Runnable so the game thread can call run() directly.
 */
public class GameLoop implements Runnable {

    private List<Box> boxes;
    private List<Trap> traps;
    private Door door;
    private Player player;
    private Terrain ceiling;
    private Terrain ground;
    private CollisionHandler collisionHandler;
    private GameRenderer renderer;

    // Fixed layout constants — the logical game area is always 900×500
    private static final int groundY = 420;
    private static final int ceilingY = 80;
    private static final int screenWidth = 900;
    private static final int mapWidth = 2400;

    private volatile int cameraX = 0;
    private volatile boolean isFinished = false;
    private volatile boolean isDead = false;

    private final KeyHandler inputs;
    private final GameScreen gameScreen;
    private Thread gameThread;

    /** Current message shown after searching an empty crate. Null when no message is active. */
    private String searchMessage = null;

    /** Counts down from 60 to 0 — when it hits 0, searchMessage is cleared. */
    private int searchMessageTimer = 0;

    /**
     * Sets up input, builds the initial map, and creates the renderer.
     *
     * @param gameScreen the parent window, needed so we can close it on death/win
     */
    public GameLoop(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        inputs = new KeyHandler();
        initGame();
    }

    /**
     * Creates all game objects and generates a new random map.
     * If the map builder fails (can't place something after 500 tries), it just tries again.
     */
    public void initGame() {
        player  = new Player(50, groundY - 40, inputs);
        door    = new Door(2200, groundY - 145);
        ground  = new Terrain(0, groundY, mapWidth, 80);
        ceiling = new Terrain(0, 0, mapWidth, ceilingY);

        MapBuilder mapBuilder = new MapBuilder(mapWidth, groundY, ceilingY);
        try {
            mapBuilder.build();
        } catch (GameException e) {
            initGame();
            return;
        }

        boxes = mapBuilder.getBoxes();
        traps = mapBuilder.getTraps();

        renderer = new GameRenderer(player, door, boxes, traps);
        renderer.addKeyListener(inputs);
        renderer.setFocusable(true);

        collisionHandler = new CollisionHandler(player, boxes, traps, door, ground, ceiling, gameScreen, this);
    }

    /**
     * Main loop — runs at roughly 60 fps (16 ms sleep per frame).
     * Calls update() then repaints the renderer every iteration.
     */
    @Override
    public void run() {
        while (gameThread != null) {
            update();
            renderer.setState(cameraX, searchMessage);
            renderer.repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Advances the game state by one frame.
     * Skipped entirely if the player is dead or has finished the game.
     */
    private void update() {
        if (isDead || isFinished) return;

        player.update();

        if (inputs.isInteract() && Math.abs(player.getX() - door.getX()) < 80) {
            door.interact(player);
        }

        collisionHandler.checkCeiling();
        collisionHandler.checkGround();
        collisionHandler.checkDoor();
        collisionHandler.checkBoxes();
        collisionHandler.checkTraps();

        if (inputs.isInteract()) {
            for (Box box : boxes) {
                if (box.isActive() && Math.abs(player.getX() - box.getX()) < 60) {
                    boolean hadKey = player.hasKey();
                    box.interact(player);
                    if (!player.hasKey() && !hadKey) {
                        searchMessage = "Nothing here...";
                        searchMessageTimer = 60;
                    }
                }
            }
        }

        if (searchMessageTimer > 0) {
            searchMessageTimer--;
            if (searchMessageTimer == 0) {
                searchMessage = null;
            }
        }

        cameraX = Math.clamp(player.getX() - screenWidth / 2, 0, mapWidth - screenWidth);
    }

    /**
     * Starts the game thread. Call this after the window is visible.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Returns the renderer panel so GameScreen can add it to the window.
     *
     * @return the GameRenderer instance for this game session
     */
    public GameRenderer getRenderer() {
        return renderer;
    }

    /**
     * Marks the player as dead, stopping the game loop.
     *
     * @param dead true to stop updates and freeze the game
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * Marks the game as finished, stopping the game loop.
     *
     * @param finished true to stop updates and freeze the game
     */
    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}