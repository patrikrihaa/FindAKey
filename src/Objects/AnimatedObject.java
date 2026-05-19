package Objects;

import java.awt.image.BufferedImage;

/**
 * Extends GameObject with frame-by-frame sprite animation.
 * Subclasses provide the frames for the current animation state
 * via getCurrentFrames() — the update logic here handles timing and advancing.
 */
public abstract class AnimatedObject extends GameObject {

    /** Counts game ticks — resets every time a new frame is shown. */
    protected int animationTimer = 0;

    /** Index of the frame currently being displayed. */
    protected int animationFrame = 0;

    /**
     * @param x world x position (left edge)
     * @param y world y position (top edge)
     * @param width object width in pixels
     * @param height object height in pixels
     */
    public AnimatedObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Advances the animation by one game tick.
     * Call this from update() in every subclass.
     */
    protected void updateAnimation() {
        animationTimer++;
        if (animationTimer >= 8) {
            animationTimer = 0;
            animationFrame++;
            if (animationFrame >= getCurrentFrames().length) {
                animationFrame = 0;
            }
        }
    }

    /**
     * Returns the frames for the current animation state.
     * Subclasses switch between idle, run, jump arrays depending on player state.
     *
     * @return array of frames to cycle through
     */
    protected abstract BufferedImage[] getCurrentFrames();
}