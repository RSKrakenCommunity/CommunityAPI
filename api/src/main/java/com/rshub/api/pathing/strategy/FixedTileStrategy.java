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

import com.rshub.api.pathing.RouteStrategy;
import com.rshub.definitions.maps.WorldTile;

public class FixedTileStrategy extends RouteStrategy {

	private final WorldTile target;

	public FixedTileStrategy(WorldTile target) {
		this.target = target;
	}

	@Override
	public boolean canExit(int currentX, int currentY, int sizeXY, int[][] clip, int clipBaseX, int clipBaseY) {
		return currentX == target.getX() && currentY == target.getY();
	}

	@Override
	public int getApproxDestinationX() {
		return target.getX();
	}

	@Override
	public int getApproxDestinationY() {
		return target.getY();
	}

	@Override
	public int getApproxDestinationSizeX() {
		return 1;
	}

	@Override
	public int getApproxDestinationSizeY() {
		return 1;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof FixedTileStrategy)) {
			return false;
		}
		FixedTileStrategy strategy = (FixedTileStrategy) other;
		return target.getX() == strategy.target.getX() && target.getY() == strategy.target.getY();
	}
}
