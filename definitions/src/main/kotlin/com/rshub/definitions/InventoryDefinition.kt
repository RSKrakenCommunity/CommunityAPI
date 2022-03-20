package com.rshub.definitions

class InventoryDefinition(override val id: Int) : Definition {

    var inventorySize: Int = 0

    var size: Int = 0
    val someIntArray1 = mutableListOf<Int>()
    val someIntArray2 = mutableListOf<Int>()

}