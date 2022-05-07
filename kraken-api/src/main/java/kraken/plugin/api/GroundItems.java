package kraken.plugin.api;

import java.util.LinkedList;
import java.util.List;

/**
 * A provider of ground items.
 */
public final class GroundItems {

    private GroundItems() {
    }

    /**
     * Retrieves all ground items.
     *
     * @return All ground items.
     */
    public static native GroundItem[] all();

    /**
     * Retrieves all ground items that match the provided filter.
     *
     * @return All ground items that match the provided filter.
     */
    public static GroundItem[] all(Filter<GroundItem> filter) {
        List<GroundItem> filtered = new LinkedList<>();
        for (GroundItem i : all()) {
            if (filter.accept(i)) {
                filtered.add(i);
            }
        }
        return filtered.toArray(new GroundItem[0]);
    }

    /**
     * Finds the closest ground item matching the provided filter.
     *
     * @param filter The filter ground items must pass through in order to be accepted.
     * @return The found ground item, or NULL if one was not found.
     */
    public static GroundItem closest(Filter<GroundItem> filter) {
        Player self = Players.self();
        if (self == null) {
            return null;
        }

        Vector3 center = self.getScenePosition();
        GroundItem closest = null;
        float closestDistance = 0.f;
        for (GroundItem i : all(filter)) {
            float distance = i.getScenePosition().distance(center);
            if (closest == null || distance < closestDistance) {
                closest = i;
                closestDistance = distance;
            }
        }

        return closest;
    }

    /**
     * Iterates over each ground item.
     *
     * @param cb The callback for invoke for each ground item.
     */
    public static void forEach(Action1<GroundItem> cb) {
        for (GroundItem i : all()) {
            cb.call(i);
        }
    }

    /**
     * Counts the number of ground items in the scene.
     *
     * @return The number of ground items in the scene.
     */
    public static int count() {
        return all().length;
    }

    /**
     * Counts the number of ground items that pass through the filter.
     *
     * @return The number of ground items that pass through the provided filter.
     */
    public static int count(Filter<GroundItem> filter) {
        return all(filter).length;
    }

}
