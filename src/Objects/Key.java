package Objects;

import Game.AssetLoader;
import Game.Interactable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Key extends GameObject implements Interactable {
    private static final BufferedImage key = AssetLoader.load("key.png");

    private boolean isTaken;

    public Key(int x, int y) {
        super(x, y, 20, 30);
    }

    @Override
    public void interact(Player player) {
        isTaken = true;
    }

    @Override
    public boolean isActive() {
        return !isTaken;
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (!isTaken) {
            if (key != null) {
                g.drawImage(key, x - cameraX, y, width, height, null);
            } else {
                g.setColor(new Color(255, 200, 0));
                g.fillOval(x - cameraX, y, 16, 16);
                g.setColor(new Color(200, 140, 0));
                g.setStroke(new BasicStroke(2f));
                g.drawOval(x - cameraX, y, 16, 16);
                g.setColor(new Color(255, 200, 0));
                g.fillRect(x - cameraX + 5, y + 14, 5, 14);
                g.fillRect(x - cameraX + 10, y + 17, 6, 4);
                g.setStroke(new BasicStroke(1f));
            }
        }

    }

    @Override
    public void update() {

    }
}
