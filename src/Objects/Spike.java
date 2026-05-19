package Objects;

import Game.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A lethal spike — either pointing up from the ground or pointing down from the ceiling.
 * Touching one kills the player instantly (handled in CollisionHandler).
 */
public class Spike extends GameObject {
    private final boolean onCeiling;

    // Two separate textures for ground and ceiling variants
    private static final BufferedImage ground = AssetLoader.load("spike.png");
    private static final BufferedImage ceiling = AssetLoader.load("spike_ceiling.png");

    /**
     * @param x world x position
     * @param y world y position (top edge for ground spikes, top of the triangle for ceiling spikes)
     * @param width spike width
     * @param height spike height
     * @param onCeiling true if the spike hangs from the ceiling, false if it's on the ground
     */
    public Spike(int x, int y,int width, int height, boolean onCeiling) {
        super(x, y, width, height);
        this.onCeiling = onCeiling;
    }

    /**
     * Draws the appropriate texture, or a hand-drawn triangle fallback.
     * Ceiling spikes point downward; ground spikes point upward.
     */
    @Override
    public void draw(Graphics2D g, int cameraX) {

        if (onCeiling) {
            if (ceiling != null) {
                g.drawImage(ceiling, x- cameraX, y, width, height, null);
            } else {
                g.setColor(new Color(180, 30, 30));
                int[] xPoints = {x - cameraX, x - cameraX + width/2, x - cameraX + width};
                int[] yPoints = {y, y + height, y};
                g.fillPolygon(xPoints, yPoints, 3);
                g.setColor(new Color(150, 17, 17));
                g.drawPolygon(xPoints, yPoints, 3);
            }
        } else {
            if (ground != null) {
                    g.drawImage(ground, x - cameraX, y, width, height, null);
            } else {
                g.setColor(new Color(180, 30, 30));
                int[] xPoints = {x - cameraX, x - cameraX + width/2, x - cameraX + width};
                int[] yPoints = {y + height, y, y + height};
                g.fillPolygon(xPoints, yPoints, 3);
                g.setColor(new Color(150, 17, 17));
                g.drawPolygon(xPoints, yPoints, 3);
            }
        }
    }

    /** Spikes don't move. */
    @Override
    public void update() {}


    public boolean isOnCeiling() {
        return onCeiling;
    }
}
