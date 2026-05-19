package Objects.Traps;

import Game.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A ceiling trap that hangs from the top of the level on a chain.
 * Kills the player instantly on contact (handled by CollisionHandler).
 */
public class CeilingTrap extends Trap{
    private static final BufferedImage trapTexture = AssetLoader.load("traps/ceiling.png");

    public static final int trapWidth = 45;
    public static final int trapHeight = 50;


    /**
     * @param x world x position (left edge)
     * @param y world y position (top edge, including chain)
     */
    public CeilingTrap(int x, int y) {
        super(x, y, trapWidth, trapHeight);
    }

    /**
     * Draws the appropriate texture, or a hand-drawn triangle fallback.
     */
    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (trapTexture != null) {
            g.drawImage(trapTexture, x - cameraX, y, trapWidth, trapHeight, null);
        } else {
            g.setColor(new Color(180, 30, 30));
            int[] xPoints = {x - cameraX, x - cameraX + width/2, x - cameraX + width};
            int[] yPoints = {y, y + height, y};
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
     * Returns a hitbox covering only the spiked ball at the bottom,
     * excluding the chain portion at the top.
     *
     * @return trimmed bounding rectangle
     */
    @Override
    public Rectangle getBounds() {
        int chainHeight = trapHeight / 3;
        return new Rectangle(x + 5, y + chainHeight, trapWidth - 10, trapHeight - chainHeight);
    }

}
