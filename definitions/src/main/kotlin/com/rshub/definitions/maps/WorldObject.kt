package com.rshub.definitions.maps

data class WorldObject(
    val objectId: Int,
    var objectX: Int,
    var objectY: Int,
    var objectPlane: Int,
    val objectRotation: Int,
    val objectType: ObjectType,
    val localTile: WorldTile
) {

    val slot: Int get() = objectType.slot
    val tile: WorldTile get() = WorldTile(objectX, objectY, objectPlane)

}