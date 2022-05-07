package kraken.plugin.api;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A provider of players.
 */
public final class Players {

    private Players() {
    }

    /**
     * Retrieves the local player.
     *
     * @return The local player, or NULL if there is no local player.
     */
    public static native Player self();

    /**
     * Retrieves all players.
     *
     * @return All players.
     */
    public static native Player[] all();

    /**
     * Retrieves all players that match the provided filter.
     *
     * @return All players that match the provided filter.
     */
    public static Player[] all(Filter<Player> filter) {
        List<Player> filtered = new LinkedList<>();
        for (Player p : all()) {
            if (filter.accept(p)) {
                filtered.add(p);
            }
        }
        return filtered.toArray(new Player[0]);
    }

    /**
     * Counts the number of available players that pass the provided filter.
     *
     * @param filter The filter to use for counting.
     * @return The number of players that passed the provided filter.
     */
    public static int count(Filter<Player> filter) {
        return all(filter).length;
    }

    /**
     * Finds the best player matching the provided filter.
     *
     * @param comparator The comparator to use for determining priority.
     * @return The found player, or NULL if one was not found.
     */
    public static Player best(Comparator<Player> comparator) {
        List<Player> filtered = Arrays.asList(all());
        filtered.sort(comparator);
        if (filtered.isEmpty()) {
            return null;
        }

        return filtered.get(0);
    }

    /**
     * Finds the best player matching the provided filter.
     *
     * @param filter The filter players must pass through in order to be accepted.
     * @param comparator The comparator to use for determining priority.
     * @return The found player, or NULL if one was not found.
     */
    public static Player best(Filter<Player> filter, Comparator<Player> comparator) {
        List<Player> filtered = Arrays.asList(all(filter));
        filtered.sort(comparator);

        return filtered.isEmpty() ? null : filtered.get(0);
    }

    /**
     * Finds the closest player matching the provided filter.
     *
     * @param filter The filter players must pass through in order to be accepted.
     * @return The found player, or NULL if one was not found.
     */
    public static Player closest(Filter<Player> filter) {
        Player self = Players.self();
        if (self == null) {
            return null;
        }

        Vector3 center = self.getScenePosition();
        Player closest = null;
        float closestDistance = 0.f;
        for (Player p : all(filter)) {
            float distance = p.getScenePosition().distance(center);
            if (closest == null || distance < closestDistance) {
                closest = p;
                closestDistance = distance;
            }
        }

        return closest;
    }

    /**
     * Iterates over each player.
     *
     * @param cb The callback for invoke for each player.
     */
    public static void forEach(Action1<Player> cb) {
        for (Player p : all()) {
            cb.call(p);
        }
    }

    /**
     * Finds a player by their server side index.
     *
     * @param index The server index to search for.
     * @return The found player, or NULL if one was not found.
     */
    public static Player byServerIndex(int index) {
        return closest((p) -> p.getServerIndex() == index);
    }
}
