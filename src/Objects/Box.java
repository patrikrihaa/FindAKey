package Objects;

import Game.AssetLoader;
import Objects.Player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * A solid box the player can stand on.
 * If the box contains the key, pressing E nearby will give it to the player.
 * Collision resolution is handled entirely by CollisionHandler.
 */
public class Box extends GameObject implements Interactable {
    private static final BufferedImage boxTexture  = AssetLoader.load("box/box.png");
    private static final BufferedImage bboxTexture = AssetLoader.load("box/box_blood.png");
    private static final BufferedImage dboxTexture = AssetLoader.load("box/box_damaged.png");

    private final BufferedImage texture;
    private boolean hasKey;
    private boolean keyTaken;
    private boolean broken;
    private boolean searched = false;

    /**
     * @param x world x (left edge)
     * @param y world y (top edge)
     * @param width box width
     * @param height box height
     * @param hasKey true if this box contains the hidden key
     */
    public Box(int x, int y, int width, int height, boolean hasKey) {
        super(x, y, width, height);
        this.hasKey = hasKey;
        this.texture = hasKey ? dboxTexture : pickTexture();
    }

    /**
     * Picks one of the non-key box textures at random when the box is created.
     *
     * @return a randomly selected box texture, or null if all failed to load
     */
    private BufferedImage pickTexture() {
        BufferedImage[] options = {boxTexture, bboxTexture, dboxTexture};
        List<BufferedImage> available = new ArrayList<>();
        for (BufferedImage img : options) {
            if (img != null) available.add(img);
        }
        if (available.isEmpty()) return null;
        return available.get(new Random().nextInt(available.size()));
    }

    /**
     * Gives the key to the player if this box contains it, and if it hasn't been taken yet.
     *
     * @param player the player interacting with the box
     */
    @Override
    public void interact(Player player) {
        if (hasKey && !keyTaken) {
            keyTaken = true;
            broken = true;
            player.collectKey();
        } else if (texture == dboxTexture && !searched) {
            searched = true;
        }
    }

    /**
     * Returns false once the key has been taken from this box.
     *
     * @return true if the box still has the key
     */
    @Override
    public boolean isActive() {
        return (hasKey && !keyTaken) || (texture == dboxTexture && !searched);
    }

    /**
     * Draws the box texture, or a plain brown rectangle if the texture didn't load.
     */
    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (broken) return;
        BufferedImage toDraw = hasKey ? dboxTexture : texture;
        if (toDraw != null) {
            g.drawImage(toDraw, x - cameraX, y, width, height, null);
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

    /**
     * Returns a trimmed hitbox with a small margin to avoid catching on edges.
     * Returns an empty rectangle if the box has been broken open.
     *
     * @return axis-aligned bounding box for collision detection
     */
    @Override
    public Rectangle getBounds() {
        if (broken) return new Rectangle(0, 0, 0, 0);
        int margin = 4;
        return new Rectangle(x + margin, y + margin, width - margin * 2, height - margin * 2);
    }

    /** @return true if this damaged crate has already been searched */
    public boolean isSearched() {
        return searched;
    }

    /**
     * Marks this box as containing the hidden key.
     *
     * @param hasKey true if this box should contain the key
     */
    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }
}