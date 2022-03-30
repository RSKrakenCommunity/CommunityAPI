package com.rshub.api.pathing;

import com.rshub.api.pathing.web.Graph;
import kraken.plugin.api.Vector3i;

public final class WalkHelper {
    private WalkHelper() {}
    private static Graph GRAPH = new Graph();

    public static void walkTo(int x, int y, int z) {
        Vector3i vec = new Vector3i(x, y, z);
        //TODO - Finish this method
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
