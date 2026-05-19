package Objects;

import Game.AssetLoader;
import Game.Interactable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Door extends GameObject implements Interactable {
    private static final BufferedImage door = AssetLoader.load("door.png");
    private static final BufferedImage door_opened = AssetLoader.load("door_opened.png");

    private boolean isOpen;

    public Door(int x, int y) {
        super(x, y, 60, 80);
    }

    @Override
    public void interact(Player player) {
        if (player.hasKey()) {
            isOpen = true;
        }
    }

    @Override
    public boolean isActive() {
        return !isOpen;
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (!isOpen) {
            if (door != null) {
                g.drawImage(door, x - cameraX, y, width, height, null);
            } else {
                g.setColor(new Color(255, 215, 0));
                g.fillOval(x - cameraX, y + height, width, height);
                g.fillRect(x - cameraX, y, width, height);
            }
        } else {
            if (door_opened != null) {
                g.drawImage(door_opened, x - cameraX, y, width, height, null);
            } else {
                g.setColor(new Color(60, 170, 25));
                g.fillRoundRect(x - cameraX, y, width, height, 10, 10);
            }
        }
    }

    @Override
    public void update() {
    }

    public boolean isOpen(){
        return isOpen;
    }
}
