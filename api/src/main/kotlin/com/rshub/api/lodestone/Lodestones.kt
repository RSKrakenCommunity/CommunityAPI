package com.rshub.api.lodestone

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.definitions.CacheHelper
import com.rshub.api.input.InputHelper.pressKey
import com.rshub.api.pathing.teleport.Teleport
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.tile
import kraken.plugin.api.Widgets

enum class Lodestones(
    val param1: Int,
    val param2: Int,
    val param3: Int,
    val key: Char,
    val dest: WorldTile,
    val varbit: Int
) : Teleport {

    LUMBRIDGE(
        1,
        -1,
        71565330,
        'L',
        tile(3233, 3221),
        35
    ),
    KHARID(
        1,
        -1,
        71565323,
        'A',
        tile(3297, 3184),
        28
    ),
    DRAYNOR(
        1,
        -1,
        71565327,
        'D',
        tile(3105, 3289),
        32
    ),
    PORT_SARIM(
        1,
        -1,
        71565331,
        'P',
        tile(3011, 3215),
        36
    ),
    VARROCK(
        1,
        -1,
        71565334,
        'V',
        tile(3214, 3376),
        39
    ),
    FALADOR(
        1,
        -1,
        71565329,
        'F',
        tile(2967, 3403),
        34
    ),
    EDGEVILLE(
        1,
        -1,
        71565328,
        'E',
        tile(3067, 3505),
        33
    ),
    BURTHORPE(
        1,
        -1,
        71565325,
        'B',
        tile(2899, 3544),
        30
    ),
    TAVERLY(
        1,
        -1,
        71565333,
        'T',
        tile(2878, 3442),
        38
    ),
    CATHERBY(
        1,
        -1,
        71565326,
        'C',
        tile(2811, 3449),
        31
    ),
    SEERS(
        1,
        -1,
        71565332,
        'S',
        tile(2689, 3482),
        37
    ),
    CANIFIS(
        1,
        -1,
        71565338,
        ' ',
        tile(3517, 3515, 0),
        18523
    ),
    YANILLE(
        1,
        -1,
        71565337,
        'Y',
        tile(2529, 3094, 0),
        40
    ),
    OOGLOG(
        1,
        -1,
        71565342,
        'O',
        tile(2532, 2871, 0),
        18527
    ),
    ARDOUGNE(
        1,
        -1,
        71565324,
        ' ',
        tile(2634, 3348, 0),
        29
    );

    override suspend fun isAvailable(): Boolean {
        return isUnlocked()
    }

    override suspend fun teleport(): Boolean {
        if (delayUntil(3000, 1000) {
                pressKey('T')
                Widgets.isOpen(LODESTONE_ID)
            }) {
            println("${this.name} - ${isUnlocked()}")
            if (!isUnlocked()) {
                println("Not unlocked!")
                return false
            }
            if (key != ' ') {
                pressKey(key)
            }
            if (delayUntil { !Widgets.isOpen(LODESTONE_ID) }) {
                return true
            }
            if (Widgets.isOpen(LODESTONE_ID)) {
                ActionHelper.menu(
                    MenuAction.WIDGET,
                    param1, param2, param3
                )
                return true
            }
        } else {
            if (!isUnlocked()) {
                println("Not unlocked! 1")
                return false
            }
            ActionHelper.menu(
                MenuAction.WIDGET,
                1,
                -1,
                96010258
            )
            if (delayUntil { Widgets.isOpen(LODESTONE_ID) }) {
                if (!isUnlocked()) {
                    println("Not unlocked! 2")
                    return false
                }
                pressKey(key)
                if (delayUntil { !Widgets.isOpen(LODESTONE_ID) }) {
                    return true
                }
                if (Widgets.isOpen(LODESTONE_ID)) {
                    ActionHelper.menu(
                        MenuAction.WIDGET,
                        param1, param2, param3
                    )
                    return true
                }
            }
        }
        return false
    }

    fun isUnlocked() = CacheHelper.getVarbitValue(varbit) == 1

    companion object {
        val LODESTONES = values()
        const val LODESTONE_NETWORK_ID = 1465
        const val LODESTONE_ID = 1092
    }

}