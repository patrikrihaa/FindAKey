package Objects;

import Objects.Player.Player;

/**
 * Anything in the world the player can interact with (boxes, door).
 * The game loop calls interact() when the player presses E near an object,
 * and checks isActive() first to skip objects that are already used up.
 */
public interface Interactable {

    /**
     * Triggered when the player interacts with the object.
     * What happens depends on the implementation — picking up a key, opening a door, etc.
     *
     * @param player  the player doing the interacting
     */
    void interact(Player player);

    /**
     * Returns false once the object has been used and shouldn't respond anymore.
     * A key that's been picked up, for example, returns false here.
     */
    boolean isActive();
}
