package com.rshub.definitions.maps

data class MapObject(
    val objectId: Int,
    val objectX: Int,
    val objectY: Int,
    val objectPlane: Int,
    val objectRotation: Int,
    val objectType: ObjectType
)