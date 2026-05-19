package Objects.Traps;

import Objects.GameObject;

import java.awt.*;

/**
 * Base class for all lethal traps in the level.
 * Subclasses define their own shape, texture, and hitbox.
 * Collision is handled externally by CollisionHandler.
 *
 * @author Patrik Říha
 */
public abstract class Trap extends GameObject {

    /**
     * @param x world x position (left edge)
     * @param y world y position (top edge)
     * @param width trap width in pixels
     * @param height trap height in pixels
     */
    public Trap(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Draws the trap on screen.
     *
     * @param g       graphics context
     * @param cameraX current horizontal scroll offset
     */
    @Override
    public abstract void draw(Graphics2D g, int cameraX);

    /** Traps are static — nothing to update each frame. */
    @Override
    public abstract void update();
}

