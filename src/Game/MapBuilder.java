package Game;

import Objects.Box;
import Objects.Key;
import Objects.Spike;

import java.util.ArrayList;
import java.util.Random;

public class MapBuilder {
    private int mapWidth;
    private int groundY;
    private int ceilingY;

    private ArrayList<Box> boxes;
    private ArrayList<Spike> spikes;
    private Key key;

    public MapBuilder(int mapWidth, int groundY, int ceilingY) {
        this.mapWidth = mapWidth;
        this.groundY = groundY;
        this.ceilingY = ceilingY;
    }

    void build()  throws GameException {
        Random rnd = new Random();
        int boxCount = 10 + rnd.nextInt(4);
        int spikeCount = 8 + rnd.nextInt(6);

        boxes = new ArrayList<>();
        spikes = new ArrayList<>();

        for (int i = 0; i < boxCount; i++) {
            int x, height, width;
            boolean freeSpace;
            int attempts = 0;
            do {
                x = 200 + rnd.nextInt(1800);
                height = 50 + rnd.nextInt(70);
                width = 50 + rnd.nextInt(70);
                freeSpace = true;
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

        for (int i = 0; i < spikeCount; i++) {
            int x, height, width;
            boolean onCeiling, freeSpace;
            int attempts = 0;
            do {
                x = 200 + rnd.nextInt(1800);
                height = 30 + rnd.nextInt(30);
                width = 30 + rnd.nextInt(30);
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

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public Key getKey() {
        return key;
    }
}