package Objects;

import Game.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Box extends GameObject {
    private static final BufferedImage box = AssetLoader.load("box.png");

    public Box(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (box != null) {
            g.drawImage(box, x - cameraX, y, width, height, null);
        } else {
            g.setColor(new Color(97, 58, 33));
            g.fillRect(x - cameraX, y, width, height);
            g.setColor(new Color(87, 45, 22));
            g.drawRect(x - cameraX, y, width, height);
        }
    }

    @Override
    public void update() {}
}
