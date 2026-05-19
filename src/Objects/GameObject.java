package Objects;

import java.awt.*;

/**
 * Base class for everything in the game world that has a position and size.
 * Subclasses handle their own drawing and update logic.
 */
public abstract class GameObject implements Renderable {
    protected int width;
    protected int height;
    protected int x;
    protected int y;

    /**
     * @param x world x position (left edge)
     * @param y world y position (top edge)
     * @param width object width in pixels
     * @param height object height in pixels
     */
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /** Draws the object on screen. cameraX is the current scroll offset. */
    public abstract void draw(Graphics2D g, int cameraX);

    /** Updates the object's state each frame. */
    public abstract void update();

    /** Returns the axis-aligned bounding box used for collision detection. */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
