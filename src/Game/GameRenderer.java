package Game;

import Objects.Box;
import Objects.Door;
import Objects.Player.Player;
import Objects.Traps.Trap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Handles all rendering for the game — background, objects, player, and HUD.
 * Extends JPanel so it can be dropped directly into the GameScreen window.
 *
 * @author Patrik Říha
 */
public class GameRenderer extends JPanel {
    private static final BufferedImage backgroundTexture = AssetLoader.load("backgrounds/background.png");

    private static final int screenWidth = 900;
    private static final int screenHeight = 500;
    private static final int mapWidth = 2400;

    private final Player player;
    private final Door door;
    private final List<Box> boxes;
    private final List<Trap> traps;

    /** Shared game state read each frame from GameLoop. */
    private int cameraX;
    private String searchMessage;

    /**
     * @param player the player to draw
     * @param door the door to draw
     * @param boxes all boxes in the current map
     * @param traps all traps in the current map
     */
    public GameRenderer(Player player, Door door, List<Box> boxes, List<Trap> traps) {
        this.player = player;
        this.door = door;
        this.boxes = boxes;
        this.traps = traps;

        setBackground(Color.BLACK);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
    }

    /**
     * Updates the camera offset and current search message before each frame is drawn.
     * Called by GameLoop every tick before repaint().
     *
     * @param cameraX current horizontal scroll offset
     * @param searchMessage message to show when searching a crate, or null if none
     */
    public void setState(int cameraX, String searchMessage) {
        this.cameraX = cameraX;
        this.searchMessage = searchMessage;
    }

    /**
     * Draws everything: background, traps, boxes, door, player, then HUD on top.
     * The whole scene is scaled and centered to keep the 900×500 aspect ratio
     * regardless of window size.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double scale  = Math.min((double) getWidth() / screenWidth, (double) getHeight() / screenHeight);
        int offsetX = (int) ((getWidth() - screenWidth * scale) / 2);
        int offsetY = (int) ((getHeight() - screenHeight * scale) / 2);
        g2.translate(offsetX, offsetY);
        g2.scale(scale, scale);

        // Background — tiled across the full map width
        for (int i = 0; i < mapWidth; i += screenWidth) {
            g2.drawImage(backgroundTexture, i - cameraX, 40, screenWidth, screenHeight, null);
        }

        for (Trap trap : traps) {
            trap.draw(g2, cameraX);
        }
        for (Box box : boxes) {
            box.draw(g2, cameraX);
        }

        door.draw(g2, cameraX);
        player.draw(g2, cameraX);

        drawHUD(g2);
    }

    /**
     * Draws the key status indicator in the top-left corner
     * and context-sensitive prompts near the door and searchable crates.
     *
     * @param g graphics context, already scaled and translated by paintComponent
     */
    private void drawHUD(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 130));
        g.fillRoundRect(8, 8, 215, 32, 4, 4);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        if (player.hasKey()) {
            g.setColor(new Color(255, 210, 0));
            g.drawString("Key: collected ✓", 18, 29);
        } else {
            g.setColor(Color.WHITE);
            g.drawString("Key: not found", 18, 29);
        }

        if (player.hasKey() && !door.isOpen() && Math.abs(player.getX() - door.getX()) < 130) {
            drawPrompt(g, "[ E ] Unlock the door", 220);
        }

        if (!player.hasKey()) {
            for (Box box : boxes) {
                if (box.isActive() && Math.abs(player.getX() - box.getX()) < 80) {
                    drawPrompt(g, "[ E ] Search the crate", 220);
                    break;
                }
            }
        }

        if (searchMessage != null) {
            drawPrompt(g, searchMessage, 180);
        }
    }

    /**
     * Draws a centered prompt bar at the bottom of the screen.
     *
     * @param g graphics context
     * @param text text to display inside the prompt
     * @param width width of the prompt background bar
     */
    private void drawPrompt(Graphics2D g, String text, int width) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(screenWidth / 2 - width / 2, screenHeight - 52, width, 34, 10, 10);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(text, screenWidth / 2 - width / 2 + 22, screenHeight - 30);
    }
}