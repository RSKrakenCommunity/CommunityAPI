package com.rshub.api.pathing;

import com.rshub.api.pathing.web.Graph;
import kraken.plugin.api.Vector3i;

public final class WalkHelper {
    private WalkHelper() {}
    private static final Graph GRAPH = new Graph();

    public static void walkTo(int x, int y, int z) {
        Vector3i vec = new Vector3i(x, y, z);
        //TODO - Finish this method
    }

    public static Graph getGraph() {
        return GRAPH;
    }
}
