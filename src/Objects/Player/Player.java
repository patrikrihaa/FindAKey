package Objects.Player;

import Game.AssetLoader;
import Inputs.KeyHandler;
import Objects.AnimatedObject;
import Objects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The player. Moves, jumps, and falls.
 * Collision is handled externally by CollisionHandler.
 */
public class Player extends AnimatedObject {
    private static final BufferedImage[] idleFrames = AssetLoader.loadSpriteSheet("player/idle.png", 5);
    private static final BufferedImage[] runFrames = AssetLoader.loadSpriteSheet("player/run.png", 8);
    private static final BufferedImage[] jumpFrames = AssetLoader.loadSpriteSheet("player/jump.png", 6);

    private PlayerState currentState = PlayerState.IDLE;
    private boolean facingLeft = false;

    private boolean hasKey;
    private boolean onGround;
    private double velocityY;
    private final KeyHandler inputs;

    // Movement constants
    private static final int speed = 5;
    private static final int jump = -13;
    private static final double gravity = 0.6;

    private final int doorX = 2350;

    /**
     * @param x starting world x position
     * @param y starting world y position
     * @param inputs keyboard state, read every frame in update()
     */
    public Player(int x, int y, KeyHandler inputs) {
        super(x, y, 45, 55);
        this.inputs = inputs;
    }

    /**
     * Returns the animation frames for the current player state.
     * Switches between idle, run, and jump arrays.
     *
     * @return array of frames matching the current state
     */
    @Override
    public BufferedImage[] getCurrentFrames() {
        switch (currentState) {
            case IDLE: return idleFrames;
            case RUNNING: return runFrames;
            case JUMPING: return jumpFrames;
            default: return idleFrames;
        }
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {
        BufferedImage[] frames = getCurrentFrames();
        if (animationFrame >= frames.length) {
            animationFrame = 0;
        }
        if (frames != null && frames.length > 0) {
            if (facingLeft) {
                g.drawImage(frames[animationFrame], x - cameraX + width, y, -width, height, null);
            } else {
                g.drawImage(frames[animationFrame], x - cameraX, y, width, height, null);
            }
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
     * if the player is actually on the ground.
     */
    @Override
    public void update() {
        if (inputs.isLeft()) {
            facingLeft = true;
            x -= speed;
            currentState = PlayerState.RUNNING;
        } else if (inputs.isRight()) {
            facingLeft = false;
            x += speed;
            currentState = PlayerState.RUNNING;
        } else {
            currentState = PlayerState.IDLE;
        }

        if (inputs.isJump() && onGround) {
            velocityY = jump;
            onGround = false;
        }

        if (!onGround) {
            currentState = PlayerState.JUMPING;
        }

        // Keep player inside map bounds
        if (x < 0) {
            x = 0;
        }

        if (x > doorX) {
            x = doorX;
        }

        velocityY += gravity;
        y += velocityY;

        updateAnimation();
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

    /** Called by Box.interact() when the player interacts with a box. */
    public void collectKey() {
        hasKey = true;
    }

    public boolean hasKey() {
        return hasKey;
    }
}
