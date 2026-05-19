package Game;

import Objects.Box;
import Objects.Traps.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Procedurally generates the obstacle layout for a single run.
 * Places boxes, traps, and the key at random positions while making sure
 * nothing overlaps. If it can't find a valid spot after 500 tries, it throws
 * GameException and the caller restarts the whole thing.
 *
 * @author Patrik Říha
 */
public class MapBuilder {
    private final int mapWidth;
    private final int groundY;
    private final int ceilingY;

    private List<Box> boxes;
    private List<Trap> traps;

    /**
     * @param mapWidth  total horizontal size of the map in pixels
     * @param groundY   y-coordinate of the ground surface
     * @param ceilingY  y-coordinate of the ceiling bottom edge
     */
    public MapBuilder(int mapWidth, int groundY, int ceilingY) {
        this.mapWidth = mapWidth;
        this.groundY = groundY;
        this.ceilingY = ceilingY;
    }

    /**
     * Generates boxes, traps with random positions and sizes.
     * Each object is checked against all already-placed objects to prevent overlap.
     * The first ~200px near the player spawn are always left clear.
     *
     * @throws GameException if any object can't be placed after 500 attempts
     */
    public void build() throws GameException {
        Random rnd = new Random();
        int boxCount = 8 + rnd.nextInt(5);
        int trapCount = 10 + rnd.nextInt(5);

        boxes = new ArrayList<>();
        traps = new ArrayList<>();

        // Place boxes on the ground at random x positions
        for (int i = 0; i < boxCount; i++) {
            int x, height, width;
            int attempts = 0;
            do {
                x = 200 + rnd.nextInt(1800);
                height = 50 + rnd.nextInt(70);
                width = 50 + rnd.nextInt(70);
                attempts++;
            } while (!isFreeSpot(x, width, 10) && attempts < 500);
            if (attempts >= 500) {
                throw new GameException("Could not place object after 500 attempts");
            }
            boxes.add(new Box(x, groundY - height, width, height, false));
        }

        List<Integer> validKeyBoxes = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).getX() > 600) validKeyBoxes.add(i);
        }
        int keyBoxIndex = validKeyBoxes.isEmpty()
                ? rnd.nextInt(boxCount)
                : validKeyBoxes.get(rnd.nextInt(validKeyBoxes.size()));
        boxes.get(keyBoxIndex).setHasKey(true);

        // Place traps — either GroundTrap or CeilingTrap
        for (int i = 0; i < trapCount; i++) {
            int x, height, width;
            boolean onCeiling;
            int attempts = 0;
            do {
                onCeiling = rnd.nextBoolean();
                x = 200 + rnd.nextInt(1800);

                // CeilingTrap has fixed size, GroundTrap is random
                width = onCeiling ? CeilingTrap.trapWidth  : 40 + rnd.nextInt(30);
                height = onCeiling ? CeilingTrap.trapHeight : 40 + rnd.nextInt(30);
                attempts++;
            } while (!isFreeSpot(x, width, 10) && attempts < 500);
            if (attempts >= 500) {
                throw new GameException("Could not place object after 500 attempts");
            }

            if (onCeiling) {
                traps.add(new CeilingTrap(x, ceilingY + 29));
            } else {
                traps.add(new GroundTrap(x, groundY - height, width, height));
            }
        }
        validateMap();
    }

    /**
     * Checks whether a given x position is free of all existing boxes and traps.
     * Used during map generation to prevent objects from overlapping.
     *
     * @param x candidate x position (left edge of the object being placed)
     * @param width width of the object being placed
     * @param margin minimum gap in pixels required between objects
     * @return true if the spot is clear, false if it overlaps with an existing object
     */
    private boolean isFreeSpot(int x, int width, int margin) {
        for (Box box : boxes) {
            int boxLeft  = box.getX() - width - margin;
            int boxRight = box.getX() + box.getWidth() + margin;
            if (x >= boxLeft && x <= boxRight) return false;
        }
        for (Trap trap : traps) {
            int trapLeft  = trap.getX() - width - margin;
            int trapRight = trap.getX() + trap.getWidth() + margin;
            if (x >= trapLeft && x <= trapRight) return false;
        }
        return true;
    }

    /**
     * Checks that no two consecutive ground traps are packed so closely
     * together that the player cannot jump over them.
     *
     * Ground traps are sorted by x position — if the total span from the left
     * edge of the first to the right edge of the second is under 180 px, the pair is
     * flagged as impassable and a GameException is thrown.
     *
     * @throws GameException if an impassable trap pair is detected
     */
    private void validateMap() throws GameException {
        List<Trap> groundTraps = new ArrayList<>();
        for (Trap t : traps) {
            if (t instanceof GroundTrap) groundTraps.add(t);
        }
        groundTraps.sort(Comparator.comparingInt(Trap::getX));

        for (int i = 0; i <= groundTraps.size() - 2; i++) {
            Trap first  = groundTraps.get(i);
            Trap second = groundTraps.get(i + 1);
            int span = (second.getX() + second.getWidth()) - first.getX();
            if (span < 180) {
                throw new GameException("Two traps too close to jump over at x=" + first.getX());
            }
        }
    }

    /** @return the list of placed boxes */
    public List<Box> getBoxes() {
        return boxes;
    }

    /** @return the list of placed traps */
    public List<Trap> getTraps() {
        return traps;
    }

    /** @return total horizontal size of the map */
    public int getMapWidth() {
        return mapWidth;
    }

    /** @return y-coordinate of the ground surface */
    public int getGroundY() {
        return groundY;
    }
}