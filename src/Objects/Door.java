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
        super(x, y, 94, 120);
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
                g.setColor(new Color(120, 72, 22));
                g.fillRect(x - cameraX, y, width, height);
                g.setColor(new Color(80, 44, 8));
                g.setStroke(new BasicStroke(2f));
                g.drawRect(x - cameraX, y, width, height);
                g.setColor(new Color(255, 200, 0));
                g.fillOval(x - cameraX + 5, y + height / 2 - 8, 11, 11);
            }
        } else {
            if (door_opened != null) {
                g.drawImage(door_opened, x - cameraX, y, width, height, null);
            } else {
                g.setColor(new Color(60, 180, 80, 90));
                g.fillRect(x - cameraX, y, width, height);
                g.setColor(new Color(60, 200, 80));
                g.setStroke(new BasicStroke(3f));
                g.drawRect(x - cameraX, y, width, height);
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
