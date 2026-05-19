package Objects;

import Game.AssetLoader;
import Inputs.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    private static final BufferedImage player = AssetLoader.load("player.png");

    protected boolean hasKey;
    private boolean onGround;
    private int velocityY;
    private final KeyHandler inputs;

    private static final int speed = 5;
    private static final int jump = -13;
    private static final double gravity = 0.6;

    public Player(int x, int y, KeyHandler inputs) {
        super(x, y, 30, 40);
        this.inputs = inputs;
    }

    @Override
    public void draw(Graphics2D g, int cameraX) {
        if (player != null) {
            g.drawImage(player, x - cameraX, y, width, height, null);
        } else {
            g.setColor(new Color(80, 80, 80));
            g.fillRect(x - cameraX, y, width, height);
        }
    }

    @Override
    public void update() {
        if (inputs.isLeft()) {x -= speed;}
        if (inputs.isRight()) {x += speed;}
        if (inputs.isJump() && onGround) {velocityY = jump;}
        if (x < 0) {x = 0;}

        velocityY += gravity;
        y += velocityY;
        onGround = false;
    }

    public void land(int surfaceY) {
        y = surfaceY - height;
        velocityY = 0;
        onGround = true;
    }

    public void blockLeft(int wallX) {
        x = wallX - width;
    }

    public void blockRight(int wallX) {
        x = wallX;
    }

    public void hitCeiling(int ceilingY) {
        y = ceilingY;
        velocityY = 0;
    }

    public void collectKey() {
        hasKey = true;
    }

    public boolean hasKey() {
        return hasKey;
    }
}
