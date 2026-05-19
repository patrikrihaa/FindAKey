package Objects;

import java.awt.*;

/**
 * Every visible game object implements this so the game loop
 * can call draw() on anything without caring what it is.
 *
 * @author Patrik Říha
 */
public interface Renderable {

    /**
     * Draws the object onto the given graphics context.
     *
     * @param g        the Graphics2D context to draw on
     * @param cameraX  current horizontal scroll offset
     */
    void draw(Graphics2D g, int cameraX);
}
