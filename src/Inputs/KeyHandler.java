package Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Tracks the current state of the movement and interact keys.
 * Each flag stays true for as long as the key is held down.
 *
 * @author Patrik Říha
 */
public class KeyHandler implements KeyListener {
    private boolean left;
    private boolean right;
    private boolean jump;
    private boolean interact;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /** Sets the matching flag to true when a key is pressed. */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A: left = true; break;
            case KeyEvent.VK_D : right = true; break;
            case KeyEvent.VK_SPACE: jump = true; break;
            case KeyEvent.VK_E: interact = true; break;
        }
    }

    /** Clears the matching flag when the key is released. */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A: left = false; break;
            case KeyEvent.VK_D : right = false; break;
            case KeyEvent.VK_SPACE: jump = false; break;
            case KeyEvent.VK_E: interact = false; break;
        }
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isJump() {
        return jump;
    }

    public boolean isInteract() {
        return interact;
    }
}