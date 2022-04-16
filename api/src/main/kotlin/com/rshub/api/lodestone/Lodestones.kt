package com.rshub.api.lodestone

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.definitions.CacheHelper
import com.rshub.api.input.KrakenInputHelper
import com.rshub.api.pathing.teleport.Teleport
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.tile
import kotlinx.coroutines.runBlocking
import kraken.plugin.api.Widgets

enum class Lodestones(
    val param1: Int,
    val param2: Int,
    val param3: Int,
    val key: Char,
    val dest: WorldTile,
    val varbit: Int,
    val isMembers: Boolean
) : Teleport {

    LUMBRIDGE(
        1,
        -1,
        71565330,
        'L',
        tile(3233, 3221),
        35,
        false
    ),
    KHARID(
        1,
        -1,
        71565323,
        'A',
        tile(3297, 3184),
        28,
        false
    ),
    DRAYNOR(
        1,
        -1,
        71565327,
        'D',
        tile(3105, 3289),
        32,
        false
    ),
    PORT_SARIM(
        1,
        -1,
        71565331,
        'P',
        tile(3011, 3215),
        36,
        false
    ),
    VARROCK(
        1,
        -1,
        71565334,
        'V',
        tile(3214, 3376),
        39,
        false
    ),
    FALADOR(
        1,
        -1,
        71565329,
        'F',
        tile(2967, 3403),
        34,
        false
    ),
    EDGEVILLE(
        1,
        -1,
        71565328,
        'E',
        tile(3067, 3505),
        33,
        false
    ),
    BURTHORPE(
        1,
        -1,
        71565325,
        'B',
        tile(2899, 3544),
        30,
        false
    ),
    TAVERLY(
        1,
        -1,
        71565333,
        'T',
        tile(2878, 3442),
        38,
        false
    ),
    CATHERBY(
        1,
        -1,
        71565326,
        'C',
        tile(2811, 3449),
        31,
        true
    ),
    SEERS(
        1,
        -1,
        71565332,
        'S',
        tile(2689, 3482),
        37,
        true
    ),
    CANIFIS(
        1,
        -1,
        71565338,
        ' ',
        tile(3517, 3515, 0),
        18523,
        true
    ),
    YANILLE(
        1,
        -1,
        71565337,
        'Y',
        tile(2529, 3094, 0),
        40,
        true
    ),
    OOGLOG(
        1,
        -1,
        71565342,
        'O',
        tile(2532, 2871, 0),
        18527,
        true
    ),
    ARDOUGNE(
        1,
        -1,
        71565324,
        ' ',
        tile(2634, 3348, 0),
        29,
        true
    ),
    WILDY(
        1,
        -1,
        71565344,
        'W',
        tile(3143, 3635),
        18529,
        true
    ),
    FREMENNIK_PROVINCE(
        1,
        -1,
        71565340,
        ' ',
        tile(2712, 3677),
        18525,
        true
    ),
    EAGLES_PEAK(
        1,
        -1,
        71565339,
        ' ',
        tile(2366, 3479),
        18524,
        true
    ),
    KARAMAJA(
        1,
        -1,
        71565341,
        'K',
        tile(2761, 3147),
        18526,
        true
    ),
    BANDIT_CAMP(
        1,
        -1,
        71565321,
        ' ',
        tile(3214, 2954),
        9482,
        true
    ) {
        override suspend fun isAvailable(): Boolean {
            return CacheHelper.getVarbitValue(varbit) >= 15
        }
    },
    LUNAR_ISLE(
        1,
        -1,
        71565322,
        ' ',
        tile(2085, 3914),
        9482,
        true
    ) {
        override suspend fun isAvailable(): Boolean {
            return CacheHelper.getVarbitValue(varbit) >= 100
        }
    },
    PRIFDDINAS(
        1,
        -1,
        71565346,
        ' ',
        tile(2208, 3360, 1),
        24967,
        true
    ),
    ANACHRONIA(
        1,
        -1,
        71565336,
        ' ',
        tile(5431, 2338),
        44270,
        true
    ),
    TIRANNWIN(
        1,
        -1,
        71565343,
        ' ',
        tile(2254, 3149),
        18528,
        true
    );

    open override suspend fun isAvailable(): Boolean {
        return CacheHelper.getVarbitValue(varbit) == 1
    }

    override suspend fun teleport(): Boolean {
        if (delayUntil(3000, 1000) {
                KrakenInputHelper.typeCharLiteral('T')
                Widgets.isOpen(LODESTONE_ID)
            }) {
            println("${this.name} - ${isAvailable()}")
            if (!isAvailable()) {
                println("Not unlocked!")
                return false
            }
            if (key != ' ') {
                KrakenInputHelper.typeCharLiteral(key)
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
            ActionHelper.menu(
                MenuAction.WIDGET,
                1,
                -1,
                96010258
            )
            if (delayUntil { Widgets.isOpen(LODESTONE_ID) }) {
                if (!isAvailable()) {
                    println("Not unlocked! 2")
                    return false
                }
                if (key != ' ') {
                    KrakenInputHelper.typeCharLiteral(key)
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
            }
        }
        return false
    }

    fun isUnlocked() = runBlocking { isAvailable() }

    companion object {
        val LODESTONES = values()
        const val LODESTONE_NETWORK_ID = 1465
        const val LODESTONE_ID = 1092
    }

}