package Objects;

import Game.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A solid box the player can stand on
 * Collision resolution is handled entirely by CollisionHandler
 */
public class Box extends GameObject {
    private static final BufferedImage box = AssetLoader.load("box.png");

    /**
     * @param x world x (left edge)
     * @param y world y (top edge)
     * @param width box width
     * @param height box height
     */
    public Box(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Draws the box texture, or a plain brown rectangle if the texture didn't load.
     */
    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (box != null) {
            g.drawImage(box, x - cameraX, y, width, height, null);
        } else {
            g.setColor(new Color(97, 58, 33));
            g.fillRect(x - cameraX, y, width, height);
            g.setColor(new Color(87, 45, 22));
            g.drawRect(x - cameraX, y, width, height);
        }
    }

    /** Boxes don't move, so nothing happens here. */
    @Override
    public void update() {}
}
