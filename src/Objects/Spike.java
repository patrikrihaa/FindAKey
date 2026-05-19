package Objects;

import Game.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spike extends GameObject {
    private boolean onCeiling;

    private static final BufferedImage ground = AssetLoader.load("spike.png");
    private static final BufferedImage ceiling = AssetLoader.load("spike_ceiling.png");

    public Spike(int x, int y, int width, int height, boolean onCeiling) {
        super(x, y, width, height);
        this.onCeiling = onCeiling;
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {

        if (onCeiling) {
            if (ceiling != null) {
                g.drawImage(ceiling, x- cameraX, y, width, height, null);
            } else {
                g.setColor(new Color(255, 0, 0));
                int[] xPoints = {x - cameraX, x - cameraX + width/2, x - cameraX + width};
                int[] yPoints = {y, y + height, y};
                g.fillPolygon(xPoints, yPoints, 3);
            }
        } else {
            if (ground != null) {
                    g.drawImage(ground, x - cameraX, y, width, height, null);
            } else {
                g.setColor(new Color(255, 0, 0));
                int[] xPoints = {x - cameraX, x - cameraX + width/2, x - cameraX + width};
                int[] yPoints = {y + height, y, y + height};
                g.fillPolygon(xPoints, yPoints, 3);
            }
        }
    }

    @Override
    public void update() {}

}
