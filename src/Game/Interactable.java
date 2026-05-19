package Game;

import Objects.Player;

public interface Interactable {

    void interact(Player player);

    boolean isActive();
}
