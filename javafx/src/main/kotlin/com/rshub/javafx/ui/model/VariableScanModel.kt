package com.rshub.javafx.ui.model

import com.rshub.api.definitions.CacheHelper
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import kraken.plugin.api.Client
import kraken.plugin.api.Debug
import tornadofx.ViewModel

class VariableScanModel : ViewModel() {

    val scanValue = bind { SimpleIntegerProperty(this, "scan_value", 0) }
    val isValueUnknown = bind { SimpleBooleanProperty(this, "is_value_unknown", false) }

    val results = bind { SimpleListProperty<VariableModel>(this, "results", FXCollections.observableArrayList()) }

    fun scan() : List<VariableModel> {
        return if(isValueUnknown.get()) {
            scanForUnknown()
        } else scanForInput()
    }
    fun scanForUnknown() : List<VariableModel> {
        if(results.isNotEmpty()) {
            val newResults = mutableListOf<VariableModel>()
            for (result in results) {
                val value = result.value.get()
                val convar = Client.getConVarById(result.variableId.get())
                if(convar.valueInt != value) {
                    newResults.add(result)
                }
            }
            results.setAll(newResults)
        } else {
            val currentResults = mutableListOf<VariableModel>()
            repeat(80000) {
                val convar = Client.getConVarById(it)
                if(convar != null) {
                    currentResults.add(VariableModel(convar.id, "", convar.valueInt))
                }
            }
            results.addAll(currentResults)
        }
        return results
    }

    fun scanForInput(): List<VariableModel> {
        val value = scanValue.get()
        val currentResults = mutableListOf<VariableModel>()
        repeat(80000) {
            val convar = Client.getConVarById(it)
            if (convar != null) {
                currentResults.add(VariableModel(convar.id, "", (0 + convar.valueInt)))
            }
        }
        return currentResults.filter { Client.getConVarById(it.variableId.get()).valueInt == value } + currentResults.asSequence()
            .map { CacheHelper.findVarbitsFor(it.variableId.get()) }.flatten()
            .filter { CacheHelper.getVarbitValue(it.id) == value }
            .map { Client.getConVarById(it.index) }
            .map { VariableModel(it.id, "", it.valueInt) }.toList()
    }

}