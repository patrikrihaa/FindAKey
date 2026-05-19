package Objects;

import Game.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The ground or ceiling — a tiled, scrolling strip that spans the full map width.
 * The same class handles both; which one it is depends on the isCeiling flag.
 */
public class Terrain extends GameObject {
    private static final BufferedImage ground = AssetLoader.load("ground.png");
    private static final BufferedImage ceiling = AssetLoader.load("ceiling.png");

    private boolean isCeiling;

    /**
     * @param x world x start (always 0 for full-width terrain)
     * @param y world y position
     * @param width total width of the terrain strip (should match mapWidth)
     * @param height thickness of the strip
     * @param isCeiling true if this is the top boundary, false for the floor
     */
    public Terrain(int x, int y, int width, int height, boolean isCeiling) {
        super(x, y, width, height);
        this.isCeiling = isCeiling;
    }

    /**
     * repeats the texture to fill the full strip width.
     * The ceiling uses a clip region to prevent tiles from bleeding outside the terrain area.
     * Falls back to a plain rectangle if textures aren't available.
     */
    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (isCeiling) {
            if (ceiling != null) {
                g.setClip(x - cameraX, y, width, height);
                for (int i = x - cameraX; i < x - cameraX + width; i += ceiling.getWidth()) {
                    g.drawImage(ceiling, i, y, null);
                }
                g.setClip(null);
            } else {
                g.setColor(new Color(50, 30, 10));
                g.drawRect(x - cameraX, y, width, height);
                g.setColor(new Color(80, 50, 20));
                g.fillRect(x - cameraX, y, width, height);
            }
        }  else {
            if (ground != null) {
                for (int i = x - cameraX; i < x - cameraX + width; i += ground.getWidth()) {
                    g.drawImage(ground, i, y, null);
                }
            } else {
                g.setColor(new Color(80, 50, 20));
                g.fillRect(x - cameraX, y, width, height);
                g.setColor(new Color(50, 30, 10));
                g.drawRect(x - cameraX, y, width, height);
            }
        }
    }

    /** Terrain doesn't move or change state. */
    @Override
    public void update() {
    }
}
