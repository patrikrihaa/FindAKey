package Objects;

import Game.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Terrain extends GameObject {
    private static final BufferedImage ground = AssetLoader.load("ground.png");
    private static final BufferedImage ceiling = AssetLoader.load("ceiling.png");

    private boolean isCeiling;

    public Terrain(int x, int y, int width, int height, boolean isCeiling) {
        super(x, y, width, height);
        this.isCeiling = isCeiling;
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (isCeiling) {
            if (ceiling != null) {
                g.setClip(x - cameraX, y, width, height);
                for (int i = x - cameraX; i < x - cameraX + width; i += ceiling.getWidth()) {
                    g.drawImage(ceiling, i, y, null);
                }
                g.setClip(null);
            } else {
                g.setColor(new Color(50, 30, 10));
                g.drawRect(x - cameraX, y, width, height);
                g.setColor(new Color(80, 50, 20));
                g.fillRect(x - cameraX, y, width, height);
            }
        }  else {
            if (ground != null) {
                for (int i = x - cameraX; i < x - cameraX + width; i += ground.getWidth()) {
                    g.drawImage(ground, i, y, null);
                }
            } else {
                g.setColor(new Color(80, 50, 20));
                g.fillRect(x - cameraX, y, width, height);
                g.setColor(new Color(50, 30, 10));
                g.drawRect(x - cameraX, y, width, height);
            }
        }
    }

    @Override
    public void update() {
    }
}
