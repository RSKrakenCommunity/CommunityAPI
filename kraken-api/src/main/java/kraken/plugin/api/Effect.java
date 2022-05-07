package kraken.plugin.api;

/**
 * An effect within the game world (glowing particles, glowing orbs, etc.)
 */
public final class Effect extends Entity {

    private int id;

    /**
     * Do not make instances of this.
     */
    private Effect() {
    }

    /**
     * Retrieves the id of the effect.
     *
     * @return The id of the effect.
     */
    public int getId() {
        return id;
    }

}
