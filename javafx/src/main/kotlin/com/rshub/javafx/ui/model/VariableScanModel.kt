package com.rshub.javafx.ui.model

import com.rshub.api.definitions.CacheHelper
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import kraken.plugin.api.Client
import tornadofx.ViewModel

class VariableScanModel : ViewModel() {

    val scanValue = bind { SimpleIntegerProperty(this, "scan_value", 0) }
    val isValueUnknown = bind { SimpleBooleanProperty(this, "is_value_unknown", false) }

    val results = bind { SimpleListProperty<VariableModel>(this, "results", FXCollections.observableArrayList()) }

    fun scan(): List<VariableModel> {
        val value = scanValue.get()
        val currentResults = mutableListOf<VariableModel>()
        if(results.isNotEmpty()) {
            if(isValueUnknown.get()) {
                for (result in results) {
                    val newValue = Client.getConVarById(result.variableId.get())
                    if(newValue == null) continue
                    if(newValue.valueInt != result.value.get()) {
                        currentResults.add(result)
                    }
                }
                results.clear()
                results.setAll(currentResults)
            } else {
                for (result in results) {
                    val newValue = Client.getConVarById(result.variableId.get())
                    if(newValue == null) continue
                    if(newValue.valueInt == scanValue.get()) {
                        currentResults.add(result)
                    }
                }
                results.clear()
                results.setAll(currentResults)
            }
        } else if(isValueUnknown.get()) {
            val rset = mutableListOf<VariableModel>()
            repeat(65535) {
                val convar = Client.getConVarById(it)
                if(convar != null) {
                    rset.add(VariableModel(convar.id, "", convar.valueInt))
                }
            }
            results.setAll(rset)
            return rset
        } else {
            val rset = CacheHelper.varbits { CacheHelper.getVarbitValue(it.id) == value || Client.getConVarById(it.index)?.valueInt == value }
                .map { VariableModel(it.index, "", Client.getConVarById(it.index)?.valueInt ?: -1, false) }
            results.setAll(rset)
            return rset
        }
        return currentResults
    }

}