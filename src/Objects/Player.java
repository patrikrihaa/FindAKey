package Objects;

import Game.AssetLoader;
import Inputs.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The player. Moves, jumps, and falls.
 * Collision is handled externally by CollisionHandler.
 */
public class Player extends GameObject {
    private static final BufferedImage player = AssetLoader.load("player.png");

    protected boolean hasKey;
    private boolean onGround;
    private double velocityY;
    private final KeyHandler inputs;

    // Movement constants
    private static final int speed = 5;
    private static final int jump = -13;
    private static final double gravity = 0.6;

    /**
     * @param x starting world x position
     * @param y starting world y position
     * @param inputs keyboard state, read every frame in update()
     */
    public Player(int x, int y, KeyHandler inputs) {
        super(x, y, 35, 42);
        this.inputs = inputs;
    }

    /**
     * Draws the player texture, or a simple placeholder figure if it didn't load.
     */
    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (player != null) {
            g.drawImage(player, x - cameraX, y, width, height, null);
        } else {
            g.setColor(new Color(80, 80, 80));
            g.fillRect(x - cameraX, y, width, height);
            g.setColor(Color.DARK_GRAY);
            g.drawRect(x - cameraX, y, width, height);
            g.setColor(Color.WHITE);
            g.fillOval(x - cameraX + 6,  y + 10, 7, 7);
            g.fillOval(x - cameraX + 17, y + 10, 7, 7);
            g.setColor(Color.BLACK);
            g.fillOval(x - cameraX + 8,  y + 12, 4, 4);
            g.fillOval(x - cameraX + 19, y + 12, 4, 4);
        }
    }

    /**
     * Reads input, moves horizontally, applies gravity, and increments vertical position.
     * onGround is reset to false here every frame — CollisionHandler sets it back to true,
     * if the player is actually on a the ground.
     */
    @Override
    public void update() {
        if (inputs.isLeft()) {
            x -= speed;
        }
        if (inputs.isRight()) {
            x += speed;
        }
        // Jump only if standing on something
        if (inputs.isJump() && onGround) {
            velocityY = jump;
        }

        // Keep player inside map bounds
        if (x < 0) {
            x = 0;
        }
        if (x > 2350) {
            x = 2350;
        }

        velocityY += gravity;
        y += velocityY;
        onGround = false;
    }

    /**
     * Snaps the player onto a surface and zeroes vertical velocity.
     * Also marks onGround so they can jump again.
     *
     * @param surfaceY world y of the surface they're landing on
     */
    public void land(int surfaceY) {
        y = surfaceY - height;
        velocityY = 0;
        onGround = true;
    }

    /**
     * Stops the player from passing through a wall on their left side.
     *
     * @param wallX world x of the wall's right edge
     */
    public void blockLeft(int wallX) {
        x = wallX - width;
    }

    /**
     * Stops the player from passing through a wall on their right side.
     *
     * @param wallX world x of the wall's left edge
     */
    public void blockRight(int wallX) {
        x = wallX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    /**
     * Bounces the player off the ceiling and kills upward momentum.
     *
     * @param ceilingY world y of the ceiling's bottom edge
     */
    public void hitCeiling(int ceilingY) {
        y = ceilingY;
        velocityY = 0;
    }

    /** Called by Key.interact() when the player picks up the key. */
    public void collectKey() {
        hasKey = true;
    }

    public boolean hasKey() {
        return hasKey;
    }
}
