package com.rshub.api.pathing;

import com.rshub.api.pathing.walking.Traverse;
import com.rshub.api.pathing.web.Graph;
import com.rshub.definitions.maps.WorldTile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
     * @return Did the move exceed
     */

    public static boolean moveTo(int x, int y, int z) {
        return Traverse.move(new WorldTile(x, y, z));
    }

    /**
     * Walks to the given tile, transverses the RuneScape World.
     * @param x - Tile x
     * @param y - Tile y
     * @param z - Tile z
     * @return Did the transversal exceed
     */

    public static boolean walkTo(int x, int y, int z) {
        return Traverse.walk(new WorldTile(x, y, z));
    }

    public static Graph getGraph() {
        return GRAPH;
    }

    public static void saveWeb() {
        Path path = Paths.get(System.getProperty("user.home")).resolve("kraken-plugins");
        File file = path.toFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        WebWalkerSerializer.INSTANCE.save(path);
    }

    public static void loadWeb() {
        try {
            Path path = Paths.get(System.getProperty("user.home")).resolve("kraken-plugins");
            File file = path.toFile();
            if (!file.exists()) {
                file.mkdirs();
            }
            WebWalkerSerializer.INSTANCE.load(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void setGraph(Graph graph) {
        GRAPH = graph;
    }
}
