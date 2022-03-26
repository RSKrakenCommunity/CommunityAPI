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
package com.rshub.api.pathing.strategy;

import com.rshub.api.definitions.CacheHelper;
import com.rshub.api.entities.items.WorldItem;
import com.rshub.api.entities.spirits.WorldSpirit;
import com.rshub.api.entities.spirits.npc.WorldNpc;
import com.rshub.api.entities.spirits.player.WorldPlayer;
import com.rshub.api.pathing.RouteStrategy;
import kraken.plugin.api.GroundItem;

public class EntityStrategy extends RouteStrategy {

    private final int x;
    private final int y;
    private final int size;
    private final int accessBlockFlag;

    public EntityStrategy(WorldPlayer entity) {
        this(entity, 1, 0);
    }

    public EntityStrategy(WorldNpc npc) {
        this(npc, CacheHelper.getNpc(npc.getId()).getSize(), 0);
    }

    public EntityStrategy(WorldItem item) {
        this.x = item.getGlobalPosition().getX();
        this.y = item.getGlobalPosition().getY();
        this.size = 1;
        this.accessBlockFlag = 0;
    }

    public EntityStrategy(WorldSpirit entity, int size, int accessBlockFlag) {
        this(entity.getGlobalPosition().getX(), entity.getGlobalPosition().getY(), size, accessBlockFlag);
    }

    public EntityStrategy(int x, int y, int size, int accessBlockFlag) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.accessBlockFlag = accessBlockFlag;
    }

    @Override
    public boolean canExit(int currentX, int currentY, int sizeXY, int[][] clip, int clipBaseX, int clipBaseY) {
        return RouteStrategy.checkFilledRectangularInteract(clip, currentX - clipBaseX, currentY - clipBaseY, sizeXY, sizeXY, x - clipBaseX, y - clipBaseY, size, size, accessBlockFlag);
    }

    @Override
    public int getApproxDestinationX() {
        return x;
    }

    @Override
    public int getApproxDestinationY() {
        return y;
    }

    @Override
    public int getApproxDestinationSizeX() {
        return size;
    }

    @Override
    public int getApproxDestinationSizeY() {
        return size;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof EntityStrategy))
            return false;
        EntityStrategy strategy = (EntityStrategy) other;
        return x == strategy.x && y == strategy.y && size == strategy.size && accessBlockFlag == strategy.accessBlockFlag;
    }

}
