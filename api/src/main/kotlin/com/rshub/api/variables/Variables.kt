// Generated by kraken community, do not modify!
package com.rshub.api.variables

import com.rshub.api.variables.impl.VariableBit
import kotlin.Any
import kotlin.Int
import kotlin.reflect.KProperty

public enum class Variables(
  variable: Variable,
) : Variable by variable {
  BANK_INVENTORY_TAB(VariableBit(45319)),
  BANK_TAB(VariableBit(45141)),
  PRESETS_OPEN(VariableBit(39433)),
  SELECTED_PRESET(VariableBit(22179)),
  PRESET_SUM_SELECTED(VariableBit(26177)),
  PRESET_INVENTORY_SELECTED(VariableBit(22158)),
  PRESET_EQUIPMENT_SELECTED(VariableBit(22159)),
  TRANSFER_X(VariableBit(2567)),
  TRANSFER_AMOUNT(VariableBit(45189)),
  PLACEHOLDERS_ENABLED(VariableBit(45190)),
  ACTION_BAR_NUMBER(VariableBit(1893)),
  ACTION_BAR_LOCKED(VariableBit(1892)),
  PRAYER_AMOUNT(VariableBit(16736)),
  ADRENALINE_AMOUNT(VariableBit(1902)),
  HEALTH_AMOUNT(VariableBit(1668)),
  MAX_HEALTH_AMOUNT(VariableBit(24595)),
  SUMMONING_POINTS(VariableBit(41524)),
  ;

  public operator fun getValue(ref: Any?, prop: KProperty<*>): Int = value
}