package Objects.Traps;

import Game.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A ground trap that sits on the floor of the level.
 * Kills the player instantly on contact (handled by CollisionHandler).
 *
 * @author Patrik Říha
 */
public class GroundTrap extends Trap {
    private static final BufferedImage trapTexture = AssetLoader.load("traps/ground.png");


    /**
     * @param x world x position (left edge)
     * @param y world y position (top edge)
     * @param width trap width in pixels
     * @param height trap height in pixels
     */
    public GroundTrap(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Draws the appropriate texture, or a hand-drawn triangle fallback.
     */
    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (trapTexture != null) {
            g.drawImage(trapTexture, x - cameraX, y, width, height, null);
        } else {
            g.setColor(new Color(180, 30, 30));
            int[] xPoints = {x - cameraX, x - cameraX + width/2, x - cameraX + width};
            int[] yPoints = {y + height, y, y + height};
            g.fillPolygon(xPoints, yPoints, 3);
            g.setColor(new Color(150, 17, 17));
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }

    /** Traps don't move. */
    @Override
    public void update() {

    }

    /**
     * Returns a hitbox covering only the spike tips at the top,
     * excluding the flat base at the bottom.
     *
     * @return trimmed bounding rectangle
     */
    @Override
    public Rectangle getBounds() {
        int baseHeight = height / 3;
        return new Rectangle(x + 5, y, width - 10, height - baseHeight);
    }
}
