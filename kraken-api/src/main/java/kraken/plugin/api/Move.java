package kraken.plugin.api;

import static kraken.plugin.api.Rng.i32;

/**
 * Provides ways of moving the local player.
 */
public final class Move {

    private Move() {
    }

    /**
     * Makes the local player walk to the provided tile.
     * <p>
     * The way in which the client walks is up to client, and depends on heuristics
     * configured by the end-user.
     *
     * @param tile The tile to walk to
     */
    public static native void to(Vector3i tile);

    /**
     * Traverses the world using a web.
     *
     * @param context The traversal context.
     */
    public static native void traverse(TraverseContext context);

    /**
     * Traverse the world using a web.
     *
     * @param tile The tile to traverse to.
     * @return The traversal context.
     */
    public static TraverseContext startTraverse(Vector3i tile) {
        TraverseContext context = new TraverseContext();
        context.setDestination(tile);
        traverse(context);
        return context;
    }

    /**
     * Traverse the world using a web. This method will not return
     * until game state is invalidated or the local player is within
     * the target destination.
     *
     * @param tile The tile to traverse to.
     * @param timeout The movement timeout.
     */
    public static boolean traverseBlocking(Vector3i tile, long timeout) {
        long end = System.currentTimeMillis() + timeout;

        Area3di dst = tile.expand(new Vector3i(8, 8, 0));
        TraverseContext ctx = startTraverse(tile);
        Time.waitUntil(() -> {
            Player p = Players.self();
            if (p == null) {
                return true;
            }

            return p.isMoving();
        });

        boolean finished = false;
        boolean failure = false;
        while (!finished && System.currentTimeMillis() < end) {
            Player self = Players.self();
            if (self == null) {
                finished = true;
                failure = true;
                continue;
            }

            // if (ctx.isFinished()) {
            //     finished = true;
            //     continue;
            // }

            if (dst.contains(self)) {
                finished = true;
                continue;
            }

            traverse(ctx);
            Time.waitUntil(() -> {
                Player p = Players.self();
                if (p == null) {
                    return true;
                }

                return p.isMoving();
            });

            Time.waitFor(i32(300, 600));
        }

        return !failure;
    }

    /**
     * Traverse the world using a web. This method will not return
     * until game state is invalidated or the local player is within
     * the target destination.
     *
     * @param tile The tile to traverse to.
     */
    public static boolean traverseBlocking(Vector3i tile) {
        return traverseBlocking(tile, 10000);
    }
}
