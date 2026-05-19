package Game;

import Objects.*;
import Objects.Player.Player;
import Screens.DeathScreen;
import Screens.GameScreen;
import Screens.WinScreen;

import java.awt.*;
import java.util.List;

import static java.lang.Math.min;

/**
 * Handles all collision detection between the player and the rest of the world.
 * Checks are called every frame from the game loop in a fixed order.
 */
public class CollisionHandler {
    private Player player;
    private List<Box> boxes;
    private List<Spike> spikes;
    private Key key;
    private Door door;
    private Terrain ground;
    private Terrain ceiling;
    private GameScreen gameScreen;
    private GameLoop gameLoop;

    public CollisionHandler(Player player, List<Box> boxes, List<Spike> spikes, Key key, Door door, Terrain ground, Terrain ceiling, GameScreen gameScreen, GameLoop gameLoop) {
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

    /**
     * Snaps the player to the ground if they've fallen into it.
     */
    public void checkGround() {
        if (player.getY() + player.getHeight() >= ground.getY()) {
            player.land(ground.getY());
        }
    }

    /**
     * Pushes the player back down if they've bumped into the ceiling.
     */
    public void checkCeiling() {
        if (player.getY() <= ceiling.getY() + ceiling.getHeight()) {
            player.hitCeiling(ceiling.getY() + ceiling.getHeight());
        }
    }

    /**
     * Checks each box for collision with the player.
     * Resolves from the side with the smallest overlap.
     * Landing on top counts as ground, hitting from below as ceiling, sides block movement.
     */
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

    /**
     * Kills the player immediately if they touch any spike.
     * Disposes the game window and opens the death screen.
     */
    public void checkSpikes() {
        for (Spike spike : spikes) {
            if (player.getBounds().intersects(spike.getBounds())) {
                gameLoop.setDead(true);
                gameScreen.dispose();
                new DeathScreen();
                return;
            }
        }
    }

    /**
     * Picks up the key when the player walks over it (only if it hasn't been taken yet).
     */
    public void checkKey() {
        if (player.getBounds().intersects(key.getBounds())) {
            if (key.isActive()) {
                key.interact(player);
            }
        }
    }

    /**
     * Blocks the player from walking through a locked door.
     * Once the door is open and the player stands roughly centered in front of it,
     * the win state is triggered and the win screen opens.
     */
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
