// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.rshub.api.map;

import com.rshub.api.definitions.CacheHelper;
import com.rshub.definitions.maps.WorldObject;
import com.rshub.definitions.maps.WorldTile;
import com.rshub.definitions.maps.ObjectType;
import com.rshub.definitions.objects.ObjectDefinition;

import java.util.*;

public class Region {

    public static final int OBJECTS = 0, UNDERWATER = 1, NPCS = 2, TILES = 3, WATER_TILES = 4;
    private static Map<Integer, Region> REGIONS = new HashMap<>();

    private final int regionId;
    private ClipMap clipMap;
    private ClipMap clipMapProj;

    public WorldObject[][][][] objects;
    public List<WorldObject> objectList;
    public int[][][] overlayIds;
    public int[][][] underlayIds;
    public byte[][][] overlayPathShapes;
    public byte[][][] overlayRotations;
    public byte[][][] tileFlags;
    private boolean loaded = false;

    public Region(int regionId, boolean load) {
        this.regionId = regionId;
        if (load)
            load();
    }

    public Region(int regionId) {
        this(regionId, true);
    }

    public void load() {
        int regionX = regionId >> 8;
        int regionY = regionId & 0xff;
        loaded = true;
    }

    public void spawnObject(WorldObject obj, int plane, int localX, int localY) {
        if (objects == null)
            objects = new WorldObject[4][64][64][4];
        if (objectList == null)
            objectList = new ArrayList<>();
        objectList.add(obj);
        objects[plane][localX][localY][obj.getSlot()] = obj;
        clip(obj, localX, localY);
    }

    public void clip(WorldObject object, int x, int y) {
        if (object.getObjectId() == -1)
            return;
        if (clipMap == null)
            clipMap = new ClipMap(regionId, false);
        if (clipMapProj == null)
            clipMapProj = new ClipMap(regionId, true);
        int plane = object.getObjectPlane();
        ObjectType type = object.getObjectType();
        int rotation = object.getObjectRotation();
        if (x < 0 || y < 0 || x >= clipMap.getMasks()[plane].length || y >= clipMap.getMasks()[plane][x].length)
            return;
        ObjectDefinition defs = CacheHelper.getObject(object.getObjectId());

        if (defs.getClipType() == 0)
            return;

        switch (type) {
            case WALL_STRAIGHT:
            case WALL_DIAGONAL_CORNER:
            case WALL_WHOLE_CORNER:
            case WALL_STRAIGHT_CORNER:
                clipMap.addWall(plane, x, y, type, rotation, defs.getBlocks(), !defs.getIgnoreAltClip());
                if (defs.getBlocks())
                    clipMapProj.addWall(plane, x, y, type, rotation, defs.getBlocks(), !defs.getIgnoreAltClip());
                break;
            case WALL_INTERACT:
            case SCENERY_INTERACT:
            case GROUND_INTERACT:
            case STRAIGHT_SLOPE_ROOF:
            case DIAGONAL_SLOPE_ROOF:
            case DIAGONAL_SLOPE_CONNECT_ROOF:
            case STRAIGHT_SLOPE_CORNER_CONNECT_ROOF:
            case STRAIGHT_SLOPE_CORNER_ROOF:
            case STRAIGHT_FLAT_ROOF:
            case STRAIGHT_BOTTOM_EDGE_ROOF:
            case DIAGONAL_BOTTOM_EDGE_CONNECT_ROOF:
            case STRAIGHT_BOTTOM_EDGE_CONNECT_ROOF:
            case STRAIGHT_BOTTOM_EDGE_CONNECT_CORNER_ROOF:
                int sizeX;
                int sizeY;
                if (rotation != 1 && rotation != 3) {
                    sizeX = defs.getSizeX();
                    sizeY = defs.getSizeY();
                } else {
                    sizeX = defs.getSizeY();
                    sizeY = defs.getSizeX();
                }
                clipMap.addObject(plane, x, y, sizeX, sizeY, defs.getBlocks(), !defs.getIgnoreAltClip());
                if (defs.getClipType() != 0)
                    clipMapProj.addObject(plane, x, y, sizeX, sizeY, defs.getBlocks(), !defs.getIgnoreAltClip());
                break;
            case GROUND_DECORATION:
                if (defs.getClipType() == 1)
                    clipMap.addBlockWalkAndProj(plane, x, y);
                break;
            default:
                break;
        }
    }

    public static Region get(int regionId) {
        return get(regionId, true);
    }

    public static Region get(int regionId, boolean load) {
        Region region = REGIONS.get(regionId);
        if (region == null) {
            region = new Region(regionId, load);
            REGIONS.put(regionId, region);
        }
        if (load && !region.loaded)
            region.load();
        return region;
    }

    public ClipMap getClipMap() {
        if (clipMap == null)
            clipMap = new ClipMap(regionId, false);
        return clipMap;
    }

    public ClipMap getClipMapProj() {
        if (clipMapProj == null)
            clipMapProj = new ClipMap(regionId, true);
        return clipMapProj;
    }

    public List<WorldObject> getObjectList() {
        return objectList;
    }

    public static boolean validateObjCoords(WorldObject object) {
        Region region = Region.get(object.getTile().getRegionId());
        List<WorldObject> realObjects = region.getObjectList();
        if (realObjects == null || realObjects.size() <= 0)
            return false;
        Map<Integer, WorldObject> distanceMap = new TreeMap<>();
        for (WorldObject real : realObjects) {
            if (object.getObjectPlane() != real.getObjectPlane() || real.getObjectId() != object.getObjectId())
                continue;
            int distance = object.getTile().distance(real.getTile());
            if (distance != -1)
                distanceMap.put(distance, real);
        }
        if (distanceMap.isEmpty())
            return false;
        List<Integer> sortedKeys = new ArrayList<>(distanceMap.keySet());
        Collections.sort(sortedKeys);
        WorldObject closest = distanceMap.get(sortedKeys.get(0));
        ObjectDefinition def = CacheHelper.getObject(object.getObjectId());
        if (object.getTile().distance(closest.getTile()) <= Math.max(def.getSizeX(), def.getSizeY())) {
            object.setObjectX(closest.getObjectX());
            object.setObjectY(closest.getObjectY());
            object.setObjectPlane(closest.getObjectPlane());
            return true;
        }
        return false;
    }

    public static int getClip(WorldTile tile) {
        return Region.get(tile.getRegionId()).getClipMap().getMasks()[tile.getZ()][tile.getXInRegion()][tile.getYInRegion()];
    }
}
