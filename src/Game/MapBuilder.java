package Game;

import Objects.Box;
import Objects.Key;
import Objects.Spike;

import java.util.ArrayList;
import java.util.Random;

/**
 * Procedurally generates the obstacle layout for a single run.
 * Places boxes, spikes, and the key at random positions while making sure
 * nothing overlaps. If it can't find a valid spot after 500 tries, it throws
 * GameException and the caller restarts the whole thing.
 */
public class MapBuilder {
    private int mapWidth;
    private int groundY;
    private int ceilingY;

    private ArrayList<Box> boxes;
    private ArrayList<Spike> spikes;
    private Key key;

    /**
     * @param mapWidth   total horizontal size of the map in pixels
     * @param groundY    y-coordinate of the ground surface
     * @param ceilingY   y-coordinate of the ceiling bottom edge
     */
    public MapBuilder(int mapWidth, int groundY, int ceilingY) {
        this.mapWidth = mapWidth;
        this.groundY = groundY;
        this.ceilingY = ceilingY;
    }

    /**
     * Generates boxes, spikes, and a key with random positions and sizes.
     * Each object is checked against all already-placed objects to prevent overlap.
     * The first ~200px near the player spawn are always left clear.
     *
     * @throws GameException  if any object can't be placed after 500 attempts
     */
    void build()  throws GameException {
        Random rnd = new Random();
        int boxCount = 8 + rnd.nextInt(5);
        int spikeCount = 10 + rnd.nextInt(5);

        boxes = new ArrayList<>();
        spikes = new ArrayList<>();

        // Place boxes on the ground at random x positions
        for (int i = 0; i < boxCount; i++) {
            int x, height, width;
            boolean freeSpace;
            int attempts = 0;
            do {
                x = 200 + rnd.nextInt(1800);
                height = 50 + rnd.nextInt(70);
                width = 50 + rnd.nextInt(70);
                freeSpace = true;

                // Reject if too close to an existing box (10px gap minimum)
                for (Box existing : boxes) {
                    int boxLeft  = existing.getX() - width - 10;
                    int boxRight = existing.getX() + existing.getWidth() + 10;
                    if (x >= boxLeft && x <= boxRight) {
                        freeSpace = false;
                        break;
                    }
                }
                attempts++;
            } while (!freeSpace && attempts < 500);
            if (attempts >= 500) {
                throw new GameException("Could not place object after 500 attempts");
            }
            boxes.add(new Box(x, groundY - height, width, height));
        }

        // Place spikes — either on the ground or hanging from the ceiling
        for (int i = 0; i < spikeCount; i++) {
            int x, height, width;
            boolean onCeiling, freeSpace;
            int attempts = 0;
            do {
                x = 200 + rnd.nextInt(1800);
                height = 40 + rnd.nextInt(30);
                width = 40 + rnd.nextInt(30);
                onCeiling = rnd.nextBoolean();
                freeSpace = true;
                for (Box box : boxes) {
                    int boxLeft  = box.getX() - width - 10;
                    int boxRight = box.getX() + box.getWidth() + 10;
                    if (x >= boxLeft && x <= boxRight) {
                        freeSpace = false;
                        break;
                    }
                }
                if (freeSpace) {
                    for (Spike spike : spikes) {
                        int spikeLeft  = spike.getX() - width - 10;
                        int spikeRight = spike.getX() + spike.getWidth() + 10;
                        if (x >= spikeLeft && x <= spikeRight) {
                            freeSpace = false;
                            break;
                        }
                    }
                }
                attempts++;
            } while (!freeSpace && attempts < 500);
            if (attempts >= 500) {
                throw new GameException("Could not place object after 500 attempts");
            }
            int y = onCeiling ? ceilingY : groundY - height;
            spikes.add(new Spike(x, y, width, height, onCeiling));
        }

        // Place the key somewhere in the second half of the map, away from obstacles
        int keyX;
        boolean freeSpace;
        int attempts = 0;
        do {
            keyX = 800 + rnd.nextInt(1200);
            freeSpace = true;
            for (Box box : boxes) {
                int boxLeft  = box.getX() - 20;
                int boxRight = box.getX() + box.getWidth() + 20;
                if (keyX >= boxLeft && keyX <= boxRight) {
                    freeSpace = false;
                    break;
                }
            }
            if (freeSpace) {
                for (Spike spike : spikes) {
                    int spikeLeft  = spike.getX() - 20;
                    int spikeRight = spike.getX() + spike.getWidth() + 20;
                    if (keyX >= spikeLeft && keyX <= spikeRight) {
                        freeSpace = false;
                        break;
                    }
                }
            }
            attempts++;
        } while (!freeSpace && attempts < 500);
        if (attempts >= 500) {
            throw new GameException("Could not place object after 500 attempts");
        }
        key = new Key(keyX, groundY - 30);
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getGroundY() {
        return groundY;
    }

    public void setGroundY(int groundY) {
        this.groundY = groundY;
    }

    /** Returns the list of placed boxes */
    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    /** Returns the list of placed spikes */
    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    /** Returns the placed key */
    public Key getKey() {
        return key;
    }
}