package com.rshub.api.pathing;

import com.rshub.api.pathing.strategy.FixedTileStrategy;
import com.rshub.api.pathing.web.Graph;
import com.rshub.definitions.maps.WorldTile;
import kraken.plugin.api.Move;
import kraken.plugin.api.Player;
import kraken.plugin.api.Players;

public final class WalkHelper {
    private WalkHelper() {
    }

    private static Graph GRAPH = new Graph();

    /**
     * Walks to a local tile, if that tile is reachable
     *
     * @param x - Tile x
     * @param y - Tile y
     * @param z - Tile z
     */

    public static boolean walk(int x, int y, int z) {
        Player player = Players.self();
        if (player == null) return false;
        WorldTile dest = new WorldTile(x, y, z);
        if(LocalPathing.isReachable(dest)) {
            Move.to(dest);
            return true;
        }
        return false;
    }

    public static Graph getGraph() {
        return GRAPH;
    }

    public static void saveWeb() {
        WebWalkerSerializer.INSTANCE.save();
    }

    public static void loadWeb() {
        WebWalkerSerializer.INSTANCE.load();
    }

    static void setGraph(Graph graph) {
        GRAPH = graph;
    }
}
