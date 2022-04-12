package com.rshub.api.banking

import com.rshub.definitions.maps.WorldTile
import kotlinx.serialization.Serializable

@Serializable
data class BankLocation(val name: String, val tile: WorldTile)