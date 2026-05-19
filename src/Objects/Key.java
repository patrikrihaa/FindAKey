package Objects;

import Game.AssetLoader;
import Game.Interactable;
import Objects.Player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The collectible key the player needs to unlock the door.
 * Disappears after being picked up and marks itself inactive so
 * CollisionHandler stops checking it.
 */
public class Key extends GameObject implements Interactable {
    private static final BufferedImage key = AssetLoader.load("key.png");

    private boolean isTaken;

    /**
     * Placed slightly above the given y so it sits just above the ground.
     *
     * @param x world x position
     * @param y world y position (adjusted by -2 internally)
     */
    public Key(int x, int y) {
        super(x, y - 2, 20, 30);
    }

    /**
     * Marks the key as taken and tells the player they now have it.
     */
    @Override
    public void interact(Player player) {
        isTaken = true;
        player.collectKey();
    }

    /** Returns false once picked up */
    @Override
    public boolean isActive() {
        return !isTaken;
    }

    /**
     * Draws the key texture, or a hand-drawn key shape as fallback.
     * Nothing is drawn at all if the key has been collected.
     */
    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (!isTaken) {
            if (key != null) {
                g.drawImage(key, x - cameraX, y, width, height, null);
            } else {
                g.setColor(new Color(255, 200, 0));
                g.fillOval(x - cameraX, y, 16, 16);
                g.setColor(new Color(200, 140, 0));
                g.setStroke(new BasicStroke(2f));
                g.drawOval(x - cameraX, y, 16, 16);
                g.setColor(new Color(255, 200, 0));
                g.fillRect(x - cameraX + 5, y + 14, 5, 14);
                g.fillRect(x - cameraX + 10, y + 17, 6, 4);
                g.setStroke(new BasicStroke(1f));
            }
        }

    }

    /** Key doesn't do anything on its own each frame. */
    @Override
    public void update() {

    }
}
