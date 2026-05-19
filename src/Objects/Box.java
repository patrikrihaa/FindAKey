package Objects;

import Game.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Box extends GameObject {
    private static final BufferedImage box = AssetLoader.load("box.png");

    public Box(int x, int y) {
        super(x, y, 50, 60);
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (box != null) {
            g.drawImage(box, x - cameraX, y, width, height, null);
        } else {
            g.setColor(new Color(87, 31, 17));
            g.fillRect(x - cameraX, y, width, height);
        }
    }

    @Override
    public void update() {}

    private static final BufferedImage texture = AssetLoader.load("box.png");

    public Box(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (texture != null) {
            g.drawImage(texture, x - cameraX, y, width, height, null);
        } else {
            g.setColor(new Color(87, 31, 17));
            g.fillRect(x - cameraX, y, width, height);
        }
    }

    @Override
    public void update() {}

}
