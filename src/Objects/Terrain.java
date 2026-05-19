package Objects;


import java.awt.*;

/**
 * An invisible boundary strip that spans the full map width.
 * Used purely for collision — CollisionHandler reads its position
 * to snap the player to the ground or ceiling.
 *
 * @author Patrik Říha
 */
public class Terrain extends GameObject {

    /**
     * @param x world x start (always 0 for full-width terrain)
     * @param y world y position
     * @param width total width of the terrain strip (should match mapWidth)
     * @param height thickness of the strip
     */
    public Terrain(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Terrain is invisible — boundaries are enforced by CollisionHandler only.
     */
    @Override
    public void draw(Graphics2D g, int cameraX) {}

    /** Terrain doesn't move or change state. */
    @Override
    public void update() {
    }
}
