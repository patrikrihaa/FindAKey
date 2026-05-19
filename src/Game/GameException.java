package Game;

/**
 * Thrown when something goes wrong during map generation —
 * mainly when the builder can't find a free spot for an object after many attempts.
 * The game loop catches this and retries the whole map from scratch.
 *
 * @author Patrik Říha
 */
public class GameException  extends Exception {
    public GameException(String message) {
        super(message);
    }
}