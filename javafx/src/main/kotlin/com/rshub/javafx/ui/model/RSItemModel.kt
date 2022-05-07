package com.rshub.javafx.ui.model

import com.rshub.definitions.ItemDefinition
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class RSItemModel(def: ItemDefinition, amount: Int) : ViewModel() {

    val name = bind { SimpleStringProperty(this, "name", def.name) }
    val itemId = bind { SimpleIntegerProperty(this, "item_id", def.id) }
    val amount = bind { SimpleIntegerProperty(this, "amount", amount) }
    val shopPrice = bind { SimpleIntegerProperty(this, "shop_price", def.price) }
    val geBuyLimit = bind { SimpleIntegerProperty(this, "ge_buy_limit", def.geBuyLimit) }

    fun toForm(): Form = Form().apply {
        fieldset("Item Identification") {
            field("Item ID") {
                label(itemId)
            }
            field("Name") {
                label(name)
            }
        }
        fieldset("Item Value Information") {
            field("Shop Price") {
                label(shopPrice)
            }
            field("Grand Exchange Buy Limit") {
                label(geBuyLimit)
            }
        }
    }

}