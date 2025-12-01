package org.example;

/**An enum containing the possible players.
 * Can be TOP or BOTTOM.
 */
public enum Player{
    TOP, BOTTOM;

    /**Returns the other player. */
    public Player other() {
        if(this == TOP) {
            return BOTTOM;
        }
        else {
            return TOP;
        }
    }
}
