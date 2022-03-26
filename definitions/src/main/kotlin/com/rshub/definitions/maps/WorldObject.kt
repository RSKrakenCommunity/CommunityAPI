package com.rshub.definitions.maps

data class WorldObject(
    val objectId: Int,
    var objectX: Int,
    var objectY: Int,
    var objectPlane: Int,
    val objectRotation: Int = 0,
    val objectType: ObjectType = ObjectType.SCENERY_INTERACT,
    val localTile: WorldTile = WorldTile(objectX, objectY, objectPlane)
) {

    val slot: Int get() = objectType.slot
    val tile: WorldTile get() = WorldTile(objectX, objectY, objectPlane)
}