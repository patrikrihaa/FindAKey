package Game;

import Objects.*;
import Screens.DeathScreen;
import Screens.GameScreen;
import Screens.WinScreen;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.min;

public class CollisionHandler {
    private Player player;
    private ArrayList<Box> boxes;
    private ArrayList<Spike> spikes;
    private Key key;
    private Door door;
    private Terrain ground;
    private Terrain ceiling;
    private GameScreen gameScreen;
    private GameLoop gameLoop;

    public CollisionHandler(Player player, ArrayList<Box> boxes, ArrayList<Spike> spikes, Key key, Door door, Terrain ground, Terrain ceiling, GameScreen gameScreen, GameLoop gameLoop) {
        this.player = player;
        this.boxes = boxes;
        this.spikes = spikes;
        this.key = key;
        this.door = door;
        this.ground = ground;
        this.ceiling = ceiling;
        this.gameScreen = gameScreen;
        this.gameLoop = gameLoop;
    }

    public void checkGround() {
        if (player.getY() + player.getHeight() >= ground.getY()) {
            player.land(ground.getY());
        }
    }

    public void checkCeiling() {
        if (player.getY() <= ceiling.getY() + ceiling.getHeight()) {
            player.hitCeiling(ceiling.getY() + ceiling.getHeight());
        }
    }

    public void checkBoxes() {
        for (Box box : boxes) {
            Rectangle p = player.getBounds();
            Rectangle b = box.getBounds();
            if (!p.intersects(b)) {
                continue;
            }

            int dTop = (p.y + p.height) - b.y;
            int dBottom = (b.y + b.height) - p.y;
            int dLeft = (p.x + p.width) - b.x;
            int dRight = (b.x + b.width) - p.x;
            int min = min(min(dTop, dBottom), min(dLeft, dRight));

            if (min == dTop && player.getVelocityY() >= 0) {
                player.land(b.y);
            } else if (min == dBottom && player.getVelocityY() < 0) {
                player.hitCeiling(b.y + b.height);
            } else if (min == dLeft) {
                player.blockLeft(b.x);
            } else if (min == dRight) {
                player.blockRight(b.x + b.width);
            }
        }
    }

    public void checkSpikes() {
        for (Spike spike : spikes) {
            if (player.getBounds().intersects(spike.getBounds())) {
                gameLoop.setDead(true);
                gameScreen.dispose();
                new DeathScreen();
            }
        }
    }

    public void checkKey() {
        if (player.getBounds().intersects(key.getBounds())) {
            if (key.isActive()) {
                key.interact(player);
            }
        }
    }

    public void checkDoor() {
        if (!door.isOpen()) {
            Rectangle p = player.getBounds();
            Rectangle d = door.getBounds();
            if (p.intersects(d)) {
                int dLeft = (p.x + p.width) - d.x;
                int dRight = (d.x + d.width) - p.x;
                if (dLeft < dRight) {
                    player.blockLeft(d.x);
                } else {
                    player.blockRight(d.x + d.width);
                }
            }
        }
        int playerCenter = player.getX() + player.getWidth()/2;
        int doorCenter = door.getX() + door.getWidth()/2;

        if (door.isOpen() && Math.abs(playerCenter - doorCenter) < 20) {
            gameLoop.setFinished(true);
            gameScreen.dispose();
            new WinScreen();
        }
    }
}
